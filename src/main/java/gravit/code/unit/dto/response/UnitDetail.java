package gravit.code.unit.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UnitDetail(
        UnitSummary unitSummaries,
        double progressRate
) {
    public static UnitDetail create(
            UnitSummary unitSummaries,
            double progressRate
    ) {
        return UnitDetail.builder()
                .unitSummaries(unitSummaries)
                .progressRate(progressRate)
                .build();
    }
}
