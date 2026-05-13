package gravit.code.learning.dto.internal;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record WeakLessonStatDto(
        long lessonId,
        String unitTitle,
        String chapterTitle,
        int accuracy,
        long totalProblemCount
) {
    public static WeakLessonStatDto of(
            long lessonId,
            String unitTitle,
            String chapterTitle,
            int accuracy,
            long totalProblemCount
    ) {
        return WeakLessonStatDto.builder()
                .lessonId(lessonId)
                .unitTitle(unitTitle)
                .chapterTitle(chapterTitle)
                .accuracy(accuracy)
                .totalProblemCount(totalProblemCount)
                .build();
    }
}
