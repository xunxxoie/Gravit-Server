package gravit.code.unit.dto.response;

import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record UnitProgressSummary(
        long unitId,
        String title,
        boolean isCompleted
) {
    public static UnitProgressSummary of(
            long unitId,
            String title,
            boolean isCompleted
    ){
        return UnitProgressSummary.builder()
                .unitId(unitId)
                .title(title)
                .isCompleted(isCompleted)
                .build();
    }
}
