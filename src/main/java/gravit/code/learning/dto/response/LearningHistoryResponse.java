package gravit.code.learning.dto.response;

import gravit.code.dailyLearningRecord.dto.response.DailySolvedCountResponse;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record LearningHistoryResponse(
        List<DailySolvedCountResponse> dailySolvedCounts,
        int peakLearningHour
) {
    public static LearningHistoryResponse of(
            List<DailySolvedCountResponse> dailySolvedCounts,
            int peakLearningHour
    ) {
        return LearningHistoryResponse.builder()
                .dailySolvedCounts(dailySolvedCounts)
                .peakLearningHour(peakLearningHour)
                .build();
    }
}
