package gravit.code.userLeague.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record MyLeagueRankWithProfileResponse(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        long leagueId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        String leagueName,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        int rank,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        long userId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        int lp,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        int maxLp,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        String nickname,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        int profileImgNumber,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        int xp,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        int level
) {
}
