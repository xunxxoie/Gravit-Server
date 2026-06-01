package gravit.code.league.dto.response;

import gravit.code.league.dto.internal.CurrentSeasonDto;
import gravit.code.league.dto.internal.LastSeasonPopupDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record LeagueHomeResponse(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        boolean containsPopup,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        CurrentSeasonDto currentSeason,
        LastSeasonPopupDto lastSeasonPopupDto
) {
    public static LeagueHomeResponse normal(
            CurrentSeasonDto currentSeasonDto
    ) {
        return new LeagueHomeResponse(false, currentSeasonDto, null);
    }

    public static LeagueHomeResponse withPopup(
            CurrentSeasonDto currentSeasonDto,
            LastSeasonPopupDto lastSeasonPopupDto
    ) {
        return new LeagueHomeResponse(true, currentSeasonDto, lastSeasonPopupDto);
    }
}
