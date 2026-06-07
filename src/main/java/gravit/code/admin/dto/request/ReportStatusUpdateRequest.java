package gravit.code.admin.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "신고 처리 상태 변경 요청 (명시적 set)")
public record ReportStatusUpdateRequest(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
        @JsonProperty("isResolved")
        @NotNull(message = "isResolved 값은 필수입니다.")
        Boolean isResolved
) {
}
