package gravit.code.mission.dto.response;

import gravit.code.mission.domain.Mission;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record MissionDetail(
        String missionType,
        String missionDescription,
        int awardXp,
        double progressRate,
        boolean isCompleted
) {
    public static MissionDetail from(Mission mission) {
        return MissionDetail.builder()
                .missionType(mission.getMissionType().name())
                .missionDescription(mission.getMissionType().getDescription())
                .awardXp(mission.getMissionType().getAwardXp())
                .progressRate(mission.getProgressRate())
                .isCompleted(mission.isCompleted())
                .build();
    }
}