package gravit.code.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UserLevelDetailResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        int level,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        int currentXp,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        int maxXp,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        double levelRate
) {
    public static UserLevelDetailResponse of(
            int level,
            int currentXp,
            int maxXp,
            double levelRate
    ) {
        return UserLevelDetailResponse.builder()
                .level(level)
                .currentXp(currentXp)
                .maxXp(maxXp)
                .levelRate(levelRate)
                .build();
    }
}
