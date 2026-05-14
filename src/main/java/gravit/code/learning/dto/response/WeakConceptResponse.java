package gravit.code.learning.dto.response;

import gravit.code.learning.dto.internal.WeakLessonStatDto;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record WeakConceptResponse(
        int rank,
        String unitTitle,
        String chapterTitle,
        int wrongAnswerCount,
        int wrongAnswerRate
) {
    public static WeakConceptResponse of(
            int rank,
            WeakLessonStatDto stat
    ) {
        int wrongAnswerCount = Math.toIntExact(stat.wrongAnswerCount());
        int wrongAnswerRate = stat.totalProblemCount() == 0
                ? 0
                : Math.toIntExact(stat.wrongAnswerCount() * 100 / stat.totalProblemCount());

        return WeakConceptResponse.builder()
                .rank(rank)
                .unitTitle(stat.unitTitle())
                .chapterTitle(stat.chapterTitle())
                .wrongAnswerCount(wrongAnswerCount)
                .wrongAnswerRate(wrongAnswerRate)
                .build();
    }
}
