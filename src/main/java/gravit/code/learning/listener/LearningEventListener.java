package gravit.code.learning.listener;

import gravit.code.global.event.OnboardingCompletedEvent;
import gravit.code.learning.service.LearningCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Log4j2
@Component
@RequiredArgsConstructor
public class LearningEventListener {

    private final LearningCommandService learningCommandService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void createLearning(OnboardingCompletedEvent event){
        try{
            learningCommandService.createLearning(event.userId());
        }catch (Exception e){
            log.error("Exception occurred while creating Learning", e);
        }
    }
}
