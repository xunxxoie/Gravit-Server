package gravit.code.admin.dto.response;

import gravit.code.admin.domain.staging.LabelStatus;
import gravit.code.admin.domain.staging.StagingLabel;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "스테이징 라벨 목록 항목")
public record StagingLabelListItemResponse(

        String label,

        long unitId,

        String description,

        LabelStatus status,

        LocalDateTime createdAt
) {
    public static StagingLabelListItemResponse from(StagingLabel label) {
        return new StagingLabelListItemResponse(
                label.getLabel(),
                label.getUnitId(),
                label.getDescription(),
                label.getStatus(),
                label.getCreatedAt()
        );
    }
}
