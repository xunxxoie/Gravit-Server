package gravit.code.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import gravit.code.auth.domain.AccessToken;
import gravit.code.auth.domain.RefreshToken;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record LoginResponse(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        String accessToken,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        String refreshToken,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @JsonProperty("isOnboarded")
        boolean isOnboarded
) {
    public static LoginResponse of(
            AccessToken accessToken,
            RefreshToken refreshToken,
            boolean isOnboarded
    ) {
        return new LoginResponse(accessToken.token(), refreshToken.token(), isOnboarded);
    }
}