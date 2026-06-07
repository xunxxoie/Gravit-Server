package gravit.code.admin.dto.request;

import gravit.code.user.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "유저 권한 변경 요청")
public record UserRoleUpdateRequest(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "ADMIN")
        @NotNull(message = "권한 값은 필수입니다.")
        Role role
) {
}
