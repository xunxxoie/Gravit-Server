package gravit.code.auth.dto.response;

import gravit.code.auth.domain.AccessToken;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ReissueResponse(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        String accessToken
) {
    public static ReissueResponse from(AccessToken accessToken) {
        return new ReissueResponse(accessToken.token());
    }
}
