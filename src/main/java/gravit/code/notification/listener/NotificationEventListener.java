package gravit.code.notification.listener;

import gravit.code.global.event.NoticeCreatedEvent;
import gravit.code.notification.domain.NotificationType;
import gravit.code.notification.service.NotificationService;
import gravit.code.notification.support.NotificationMessageProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Log4j2
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;
    private final NotificationMessageProvider messageProvider;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleNoticeCreated(NoticeCreatedEvent event) {
        try {
            String message = messageProvider.noticePublished(event.title());
            notificationService.notifyAllUsers(NotificationType.NOTICE, message, event.noticeId());
        } catch (Exception e) {
            log.error("공지 알림 적재 실패 - noticeId: {}", event.noticeId(), e);
        }
    }
}
