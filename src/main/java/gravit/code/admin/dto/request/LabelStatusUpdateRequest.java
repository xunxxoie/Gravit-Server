package gravit.code.admin.dto.request;

import gravit.code.admin.domain.staging.LabelStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "스테이징 라벨 상태 변경 요청 (COMPLETED = promote)")
public record LabelStatusUpdateRequest(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "COMPLETED")
        @NotNull(message = "상태 값은 필수입니다.")
        LabelStatus status
) {
}
