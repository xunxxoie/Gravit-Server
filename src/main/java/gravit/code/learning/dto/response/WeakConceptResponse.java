package gravit.code.learning.dto.response;

import gravit.code.learning.dto.internal.WeakLessonStatDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record WeakConceptResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        int rank,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String unitTitle,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String chapterTitle,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        int wrongAnswerCount,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
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
