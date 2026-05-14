package gravit.code.learning.dto.internal;

public record WeakLessonStatDto(
        long lessonId,
        String unitTitle,
        String chapterTitle,
        long wrongAnswerCount,
        long totalProblemCount
) {
}
