package gravit.code.learning.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record MyPageSummaryResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        LearningSummaryResponse learningSummary,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        LearningHistoryResponse learningHistory,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        List<Integer> years
) {
    public static MyPageSummaryResponse of(
            LearningSummaryResponse learningSummary,
            LearningHistoryResponse learningHistory,
            List<Integer> years
    ) {
        return MyPageSummaryResponse.builder()
                .learningSummary(learningSummary)
                .learningHistory(learningHistory)
                .years(years)
                .build();
    }
}
