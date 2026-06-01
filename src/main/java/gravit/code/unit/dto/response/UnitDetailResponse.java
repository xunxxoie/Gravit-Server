package gravit.code.unit.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UnitDetailResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        UnitSummaryResponse unitSummaryResponse,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        double progressRate
) {
    public static UnitDetailResponse create(
            UnitSummaryResponse unitSummaryResponse,
            double progressRate
    ) {
        return UnitDetailResponse.builder()
                .unitSummaryResponse(unitSummaryResponse)
                .progressRate(progressRate)
                .build();
    }
}
