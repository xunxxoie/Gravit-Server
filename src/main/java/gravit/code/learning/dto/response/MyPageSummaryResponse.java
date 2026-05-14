package gravit.code.learning.dto.response;

import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record MyPageSummaryResponse(
        LearningSummaryResponse learningSummary,
        LearningHistoryResponse learningHistory,
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
