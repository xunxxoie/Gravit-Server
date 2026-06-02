package gravit.code.notification.facade;

import gravit.code.fcm.dto.internal.PushMessage;
import gravit.code.fcm.service.FcmService;
import gravit.code.fcm.service.FcmTokenQueryService;
import gravit.code.global.annotation.Facade;
import gravit.code.learning.dto.internal.ConsecutiveAtRiskUser;
import gravit.code.learning.service.LearningQueryService;
import gravit.code.notification.domain.NotificationType;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Facade
@RequiredArgsConstructor
public class NotificationFacade {

    private static final String CONSECUTIVE_WARNING_MESSAGE = "오늘 학습을 하지 않으면 %d일 연속학습이 끊겨요!";

    private static final List<String> DAILY_INCOMPLETE_MESSAGES = List.of(
            "오늘 아직 학습을 안 했어요! 10분만 투자해보세요 📚",
            "오늘 학습을 시작해보세요! 작은 습관이 큰 변화를 만들어요 🌱",
            "지금 이 시간에도 누군가는 CS를 공부하고 있어요 👀"
    );

    private final LearningQueryService learningQueryService;
    private final FcmTokenQueryService fcmTokenQueryService;
    private final FcmService fcmService;

    public void sendConsecutiveLearningWarnings() {

        List<ConsecutiveAtRiskUser> targets = learningQueryService.getConsecutiveAtRiskUsers();

        if (targets.isEmpty()) {
            return;
        }

        List<Long> targetUserIds = targets.stream()
                .map(ConsecutiveAtRiskUser::userId)
                .toList();

        Map<Long, List<String>> tokensByUserId = fcmTokenQueryService.getTokensByUserIds(targetUserIds);

        Map<String, String> data = buildActionData(NotificationType.STREAK_WARNING);

        List<PushMessage> messages = targets.stream()
                .filter(target -> tokensByUserId.containsKey(target.userId()))
                .map(target -> PushMessage.of(
                        tokensByUserId.get(target.userId()),
                        CONSECUTIVE_WARNING_MESSAGE.formatted(target.consecutiveSolvedDays()),
                        null,
                        data
                ))
                .toList();

        fcmService.sendNotifications(messages);
    }

    public void sendDailyIncompleteReminders() {

        List<Long> targetUserIds = learningQueryService.getDailyIncompleteUserIds();

        if (targetUserIds.isEmpty()) {
            return;
        }

        Map<Long, List<String>> tokensByUserId = fcmTokenQueryService.getTokensByUserIds(targetUserIds);

        Map<String, String> data = buildActionData(NotificationType.DAILY_INCOMPLETE);

        List<PushMessage> messages = targetUserIds.stream()
                .filter(tokensByUserId::containsKey)
                .map(userId -> PushMessage.of(
                        tokensByUserId.get(userId),
                        randomDailyIncompleteMessage(),
                        null,
                        data
                ))
                .toList();

        fcmService.sendNotifications(messages);
    }

    private String randomDailyIncompleteMessage() {
        int index = ThreadLocalRandom.current().nextInt(DAILY_INCOMPLETE_MESSAGES.size());
        return DAILY_INCOMPLETE_MESSAGES.get(index);
    }

    private Map<String, String> buildActionData(NotificationType type) {
        return Map.of(
                "type", type.name(),
                "actionType", type.getActionType().name()
        );
    }
}
