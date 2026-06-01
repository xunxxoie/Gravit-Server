package gravit.code.learning.dto.response;

import gravit.code.dailyLearningRecord.dto.response.DailySolvedCountResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record LearningHistoryResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        List<DailySolvedCountResponse> dailySolvedCounts,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
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
