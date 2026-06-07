package gravit.code.admin.dto.request;

import gravit.code.user.domain.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "유저 상태 변경 요청")
public record UserStatusUpdateRequest(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "SUSPENDED")
        @NotNull(message = "상태 값은 필수입니다.")
        UserStatus status
) {
}
