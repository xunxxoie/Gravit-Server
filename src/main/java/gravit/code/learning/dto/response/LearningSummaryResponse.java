package gravit.code.learning.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record LearningSummaryResponse(
        int topPercent,
        int completedLessonCount,
        int totalLessonCount,
        double totalLearningHours,
        int averageAccuracy
) {
    public static LearningSummaryResponse of(
            int topPercent,
            int completedLessonCount,
            int totalLessonCount,
            double totalLearningHours,
            int averageAccuracy
    ) {
        return LearningSummaryResponse.builder()
                .topPercent(topPercent)
                .completedLessonCount(completedLessonCount)
                .totalLessonCount(totalLessonCount)
                .totalLearningHours(totalLearningHours)
                .averageAccuracy(averageAccuracy)
                .build();
    }
}
