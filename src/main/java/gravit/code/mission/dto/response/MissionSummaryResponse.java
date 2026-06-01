package gravit.code.mission.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import gravit.code.mission.domain.MissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record MissionSummaryResponse(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        MissionType missionType,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @JsonProperty("isCompleted")
        boolean isCompleted
) {
}
