package gravit.code.notification.service;

import gravit.code.notification.domain.Notification;
import gravit.code.notification.domain.NotificationType;
import gravit.code.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void notify(
            long userId,
            NotificationType type,
            String message,
            Long targetId
    ) {
        notificationRepository.save(Notification.create(userId, type, message, targetId));
    }

    @Transactional
    public void notify(
            long userId,
            NotificationType type,
            String message
    ) {
        notify(userId, type, message, null);
    }
}
