package gravit.code.dailyLearningRecord.service;

import gravit.code.dailyLearningRecord.domain.DailyLearningRecord;
import gravit.code.dailyLearningRecord.dto.response.WeeklyLearningRecordResponse;
import gravit.code.dailyLearningRecord.repository.DailyLearningRecordRepository;
import gravit.code.support.TCSpringBootTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@TCSpringBootTest
@Sql(scripts = "classpath:sql/truncate_all.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class DailyLearningRecordServiceIntegrationTest {

    private static final ZoneId KST = ZoneId.of("Asia/Seoul");

    @Autowired
    private DailyLearningRecordService dailyLearningRecordService;

    @Autowired
    private DailyLearningRecordRepository dailyLearningRecordRepository;

    @Nested
    @DisplayName("주간 학습 기록을 조회할 때")
    class GetWeeklyLearningRecordResponse {

        @Test
        void 모든_요일에_학습_기록이_있으면_모두_true를_반환한다() {
            // given
            long userId = 1L;
            LocalDate monday = LocalDate.now(KST).with(DayOfWeek.MONDAY);
            for (int i = 0; i < 7; i++) {
                dailyLearningRecordRepository.save(DailyLearningRecord.create(userId, monday.plusDays(i)));
            }

            // when
            WeeklyLearningRecordResponse result = dailyLearningRecordService.getWeeklyLearningRecord(userId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(result.MONDAY()).isTrue();
                softly.assertThat(result.TUESDAY()).isTrue();
                softly.assertThat(result.WEDNESDAY()).isTrue();
                softly.assertThat(result.THURSDAY()).isTrue();
                softly.assertThat(result.FRIDAY()).isTrue();
                softly.assertThat(result.SATURDAY()).isTrue();
                softly.assertThat(result.SUNDAY()).isTrue();
            });
        }

        @Test
        void 월요일과_수요일만_학습했다면_해당_요일만_true를_반환한다() {
            // given
            long userId = 1L;
            LocalDate monday = LocalDate.now(KST).with(DayOfWeek.MONDAY);
            dailyLearningRecordRepository.save(DailyLearningRecord.create(userId, monday));
            dailyLearningRecordRepository.save(DailyLearningRecord.create(userId, monday.plusDays(2)));

            // when
            WeeklyLearningRecordResponse result = dailyLearningRecordService.getWeeklyLearningRecord(userId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(result.MONDAY()).isTrue();
                softly.assertThat(result.TUESDAY()).isFalse();
                softly.assertThat(result.WEDNESDAY()).isTrue();
                softly.assertThat(result.THURSDAY()).isFalse();
                softly.assertThat(result.FRIDAY()).isFalse();
                softly.assertThat(result.SATURDAY()).isFalse();
                softly.assertThat(result.SUNDAY()).isFalse();
            });
        }

        @Test
        void 학습_기록이_없으면_모두_false를_반환한다() {
            // given
            long userId = 1L;

            // when
            WeeklyLearningRecordResponse result = dailyLearningRecordService.getWeeklyLearningRecord(userId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(result.MONDAY()).isFalse();
                softly.assertThat(result.TUESDAY()).isFalse();
                softly.assertThat(result.WEDNESDAY()).isFalse();
                softly.assertThat(result.THURSDAY()).isFalse();
                softly.assertThat(result.FRIDAY()).isFalse();
                softly.assertThat(result.SATURDAY()).isFalse();
                softly.assertThat(result.SUNDAY()).isFalse();
            });
        }

        @Test
        void 다른_주차의_학습_기록은_무시된다() {
            // given
            long userId = 1L;
            LocalDate lastMonday = LocalDate.now(KST).with(DayOfWeek.MONDAY).minusWeeks(1);
            LocalDate nextMonday = LocalDate.now(KST).with(DayOfWeek.MONDAY).plusWeeks(1);
            dailyLearningRecordRepository.save(DailyLearningRecord.create(userId, lastMonday));
            dailyLearningRecordRepository.save(DailyLearningRecord.create(userId, nextMonday));

            // when
            WeeklyLearningRecordResponse result = dailyLearningRecordService.getWeeklyLearningRecord(userId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(result.MONDAY()).isFalse();
                softly.assertThat(result.TUESDAY()).isFalse();
                softly.assertThat(result.WEDNESDAY()).isFalse();
                softly.assertThat(result.THURSDAY()).isFalse();
                softly.assertThat(result.FRIDAY()).isFalse();
                softly.assertThat(result.SATURDAY()).isFalse();
                softly.assertThat(result.SUNDAY()).isFalse();
            });
        }

        @Test
        void 다른_사용자의_학습_기록은_무시된다() {
            // given
            long userId = 1L;
            long otherUserId = 2L;
            LocalDate monday = LocalDate.now(KST).with(DayOfWeek.MONDAY);
            dailyLearningRecordRepository.save(DailyLearningRecord.create(otherUserId, monday));

            // when
            WeeklyLearningRecordResponse result = dailyLearningRecordService.getWeeklyLearningRecord(userId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(result.MONDAY()).isFalse();
                softly.assertThat(result.TUESDAY()).isFalse();
                softly.assertThat(result.WEDNESDAY()).isFalse();
                softly.assertThat(result.THURSDAY()).isFalse();
                softly.assertThat(result.FRIDAY()).isFalse();
                softly.assertThat(result.SATURDAY()).isFalse();
                softly.assertThat(result.SUNDAY()).isFalse();
            });
        }
    }

    @Nested
    @DisplayName("일일 학습 기록을 처리할 때")
    class HandleDailyLearningRecord {

        @Test
        void 오늘_날짜_레코드가_없으면_새로_생성하고_카운트가_1이_된다() {
            // given
            long userId = 1L;
            LocalDate today = LocalDate.now(KST);

            // when
            dailyLearningRecordService.handleDailyLearningRecord(userId);

            // then
            Optional<DailyLearningRecord> result = dailyLearningRecordRepository.findByUserIdAndSolvedDate(userId, today);
            assertSoftly(softly -> {
                softly.assertThat(result).isPresent();
                softly.assertThat(result.get().getUserId()).isEqualTo(userId);
                softly.assertThat(result.get().getSolvedDate()).isEqualTo(today);
                softly.assertThat(result.get().getSolvedLessonCount()).isEqualTo(1);
            });
        }

        @Test
        void 오늘_날짜_레코드가_존재하면_카운트가_1_증가한다() {
            // given
            long userId = 1L;
            LocalDate today = LocalDate.now(KST);
            DailyLearningRecord existing = DailyLearningRecord.create(userId, today);
            existing.increaseSolvedLessonCount();
            dailyLearningRecordRepository.save(existing);

            // when
            dailyLearningRecordService.handleDailyLearningRecord(userId);

            // then
            DailyLearningRecord result = dailyLearningRecordRepository.findByUserIdAndSolvedDate(userId, today).orElseThrow();
            assertSoftly(softly -> {
                softly.assertThat(result.getId()).isEqualTo(existing.getId());
                softly.assertThat(result.getSolvedLessonCount()).isEqualTo(2);
                softly.assertThat(result.getSolvedDate()).isEqualTo(today);
            });
        }

        @Test
        void 같은_유저의_다른_날짜_레코드가_있어도_정상_처리된다() {
            // given
            long userId = 1L;
            LocalDate today = LocalDate.now(KST);
            LocalDate yesterday = today.minusDays(1);
            LocalDate twoDaysAgo = today.minusDays(2);

            DailyLearningRecord pastRecord1 = DailyLearningRecord.create(userId, yesterday);
            pastRecord1.increaseSolvedLessonCount();
            DailyLearningRecord pastRecord2 = DailyLearningRecord.create(userId, twoDaysAgo);
            pastRecord2.increaseSolvedLessonCount();
            dailyLearningRecordRepository.save(pastRecord1);
            dailyLearningRecordRepository.save(pastRecord2);

            // when & then
            assertThatCode(() -> dailyLearningRecordService.handleDailyLearningRecord(userId))
                    .doesNotThrowAnyException();

            DailyLearningRecord todayRecord = dailyLearningRecordRepository.findByUserIdAndSolvedDate(userId, today).orElseThrow();
            DailyLearningRecord yesterdayRecord = dailyLearningRecordRepository.findByUserIdAndSolvedDate(userId, yesterday).orElseThrow();
            assertSoftly(softly -> {
                softly.assertThat(todayRecord.getSolvedLessonCount()).isEqualTo(1);
                softly.assertThat(yesterdayRecord.getSolvedLessonCount()).isEqualTo(1);
            });
        }

        @Test
        void 같은_메서드를_두_번_호출하면_카운트가_2가_된다() {
            // given
            long userId = 1L;
            LocalDate today = LocalDate.now(KST);

            // when
            dailyLearningRecordService.handleDailyLearningRecord(userId);
            dailyLearningRecordService.handleDailyLearningRecord(userId);

            // then
            DailyLearningRecord result = dailyLearningRecordRepository.findByUserIdAndSolvedDate(userId, today).orElseThrow();
            assertSoftly(softly -> {
                softly.assertThat(result.getSolvedLessonCount()).isEqualTo(2);
                softly.assertThat(result.getUserId()).isEqualTo(userId);
                softly.assertThat(result.getSolvedDate()).isEqualTo(today);
            });
        }
    }
}
