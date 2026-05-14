package gravit.code.dailyLearningRecord.listener;

import gravit.code.dailyLearningRecord.service.DailyLearningRecordService;
import gravit.code.global.event.LessonCompletedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DailyLearningRecordListenerUnitTest {

    @InjectMocks
    private DailyLearningRecordListener dailyLearningRecordListener;

    @Mock
    private DailyLearningRecordService dailyLearningRecordService;

    @Nested
    @DisplayName("레슨 완료 이벤트를 처리할 때")
    class HandleDailyLearningRecord {

        @Test
        void 이벤트_수신_시_서비스의_일일_학습_기록_처리_메서드를_호출한다() {
            // given
            long userId = 1L;
            LessonCompletedEvent event = new LessonCompletedEvent(userId, 10L, 100L, 20, 80, 120, 0, 1);

            // when
            dailyLearningRecordListener.handleDailyLearningRecord(event);

            // then
            verify(dailyLearningRecordService).handleDailyLearningRecord(userId);
        }

        @Test
        void 서비스에서_예외가_발생해도_외부로_전파되지_않는다() {
            // given
            long userId = 1L;
            LessonCompletedEvent event = new LessonCompletedEvent(userId, 10L, 100L, 20, 80, 120, 0, 1);
            doThrow(new RuntimeException("DB error"))
                    .when(dailyLearningRecordService).handleDailyLearningRecord(userId);

            // when & then
            assertThatCode(() -> dailyLearningRecordListener.handleDailyLearningRecord(event))
                    .doesNotThrowAnyException();
            verify(dailyLearningRecordService).handleDailyLearningRecord(userId);
        }
    }
}
