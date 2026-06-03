package gravit.code.notification.support;

import gravit.code.notification.dto.internal.InactivityMilestone;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class NotificationMessageProvider {

    private static final String CONSECUTIVE_WARNING_MESSAGE = "오늘 학습을 하지 않으면 %d일 연속학습이 끊겨요!";

    private static final String NEW_CONTENT_MESSAGE = "새 레슨이 업데이트됐어요! 오늘 학습에 도전해보세요 🔥";

    private static final List<String> DAILY_INCOMPLETE_MESSAGES = List.of(
            "오늘 아직 학습을 안 했어요! 10분만 투자해보세요 📚",
            "오늘 학습을 시작해보세요! 작은 습관이 큰 변화를 만들어요 🌱",
            "지금 이 시간에도 누군가는 CS를 공부하고 있어요 👀"
    );

    private static final List<InactivityMilestone> INACTIVITY_MILESTONES = List.of(
            new InactivityMilestone(7, "일주일 비웠더니 실력도 쉬는 중... 다시 깨워볼까요?"),
            new InactivityMilestone(14, "2주.. 슬슬 돌아오실 때가 되었는데요? 👀"),
            new InactivityMilestone(30, "한 달간 안 까먹었어요. 당신은.. 좀 까먹었을지도?"),
            new InactivityMilestone(60, "두 달째 그래빗이 우주에서 당신의 신호를 기다리고 있어요 🛜"),
            new InactivityMilestone(90, "저를 잊으셨나요?")
    );

    public String consecutiveWarning(int consecutiveDays) {
        return CONSECUTIVE_WARNING_MESSAGE.formatted(consecutiveDays);
    }

    public String randomDailyIncomplete() {
        int index = ThreadLocalRandom.current().nextInt(DAILY_INCOMPLETE_MESSAGES.size());
        return DAILY_INCOMPLETE_MESSAGES.get(index);
    }

    public List<InactivityMilestone> inactivityMilestones() {
        return INACTIVITY_MILESTONES;
    }

    public String inactivity(int inactiveDays) {
        return INACTIVITY_MILESTONES.stream()
                .filter(milestone -> milestone.days() == inactiveDays)
                .map(InactivityMilestone::message)
                .findFirst()
                .orElseGet(() -> "%d일째 그래빗이 당신을 기다리고 있어요 🛜".formatted(inactiveDays));
    }

    public String noticePublished(String noticeTitle) {
        return "[공지] " + noticeTitle;
    }

    public String newContent() {
        return NEW_CONTENT_MESSAGE;
    }
}
