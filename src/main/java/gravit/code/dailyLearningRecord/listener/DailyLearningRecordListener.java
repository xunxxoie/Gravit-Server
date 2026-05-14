package gravit.code.dailyLearningRecord.listener;

import gravit.code.dailyLearningRecord.service.DailyLearningRecordService;
import gravit.code.global.event.LessonCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Async("dailyLearningRecordAsync")
@Log4j2
@Component
@RequiredArgsConstructor
public class DailyLearningRecordListener {

    private final DailyLearningRecordService dailyLearningRecordService;

    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void handleDailyLearningRecord(LessonCompletedEvent event) {
        try{
            dailyLearningRecordService.handleDailyLearningRecord(event.userId());
        } catch (Exception e) {
            log.error("Exception occurred while handling Daily Learning Record event", e);
        }
    }
}
