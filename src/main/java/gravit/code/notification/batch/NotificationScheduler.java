package gravit.code.notification.batch;

import gravit.code.notification.facade.NotificationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final NotificationFacade notificationFacade;

    @Scheduled(cron = "0 0 21 * * *", zone = "Asia/Seoul")
    public void sendConsecutiveLearningWarnings(){
        notificationFacade.sendConsecutiveLearningWarnings();
    }

    @Scheduled(cron = "0 0 21 * * *", zone = "Asia/Seoul")
    public void sendDailyIncompleteReminders(){
        notificationFacade.sendDailyIncompleteReminders();
    }

    @Scheduled(cron = "0 0 21 * * *", zone = "Asia/Seoul")
    public void sendInactivityReminders(){
        notificationFacade.sendInactivityReminders();
    }
}
