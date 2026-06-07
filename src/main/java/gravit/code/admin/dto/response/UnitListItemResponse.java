package gravit.code.admin.dto.response;

import gravit.code.unit.domain.Unit;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "유닛 목록 항목")
public record UnitListItemResponse(

        long unitId,

        String title,

        String description
) {
    public static UnitListItemResponse from(Unit unit) {
        return new UnitListItemResponse(unit.getId(), unit.getTitle(), unit.getDescription());
    }
}
