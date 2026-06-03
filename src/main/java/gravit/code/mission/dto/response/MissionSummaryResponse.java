package gravit.code.mission.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import gravit.code.mission.domain.MissionType;
import io.swagger.v3.oas.annotations.media.Schema;

public record MissionSummaryResponse(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        MissionType missionType,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @JsonProperty("isCompleted")
        boolean isCompleted
) {
}
