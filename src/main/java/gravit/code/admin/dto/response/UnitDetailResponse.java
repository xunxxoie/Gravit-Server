package gravit.code.admin.dto.response;

import gravit.code.unit.domain.Unit;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "유닛 상세")
public record UnitDetailResponse(

        long unitId,

        long chapterId,

        String title,

        String description,

        long lessonCount
) {
    public static UnitDetailResponse of(
            Unit unit,
            long lessonCount
    ) {
        return new UnitDetailResponse(unit.getId(), unit.getChapterId(), unit.getTitle(), unit.getDescription(), lessonCount);
    }
}
