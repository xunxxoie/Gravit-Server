package gravit.code.mission.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import gravit.code.mission.domain.Mission;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record MissionDetailResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String missionType,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String missionDescription,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        int awardXp,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        double progressRate,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @JsonProperty("isCompleted")
        boolean isCompleted
) {
    public static MissionDetailResponse from(Mission mission) {
        return MissionDetailResponse.builder()
                .missionType(mission.getMissionType().name())
                .missionDescription(mission.getMissionType().getDescription())
                .awardXp(mission.getMissionType().getAwardXp())
                .progressRate(mission.getProgressRate())
                .isCompleted(mission.isCompleted())
                .build();
    }
}