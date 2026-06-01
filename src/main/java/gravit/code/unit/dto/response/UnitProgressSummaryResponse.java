package gravit.code.unit.dto.response;

import gravit.code.unit.domain.UnitProgressStatus;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record UnitProgressSummaryResponse(
        long unitId,
        String title,
        UnitProgressStatus status
) {
    public static UnitProgressSummaryResponse of(
            long unitId,
            String title,
            UnitProgressStatus status
    ){
        return UnitProgressSummaryResponse.builder()
                .unitId(unitId)
                .title(title)
                .status(status)
                .build();
    }
}
