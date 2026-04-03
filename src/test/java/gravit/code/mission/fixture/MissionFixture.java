package gravit.code.mission.fixture;

import gravit.code.mission.domain.Mission;
import gravit.code.mission.domain.MissionType;
import org.springframework.test.util.ReflectionTestUtils;

public class MissionFixture {

    public static Mission 기본_미션(long userId) {
        Mission mission = Mission.create(MissionType.COMPLETE_LESSON_ONE, userId);
        ReflectionTestUtils.setField(mission, "id", 1L);
        return mission;
    }

    public static Mission 저장된_미션(
            long id,
            MissionType missionType,
            long userId
    ) {
        Mission mission = Mission.create(missionType, userId);
        ReflectionTestUtils.setField(mission, "id", id);
        return mission;
    }

    public static Mission 진행중_미션(
            long id,
            MissionType missionType,
            double progressRate,
            long userId
    ) {
        Mission mission = Mission.create(missionType, userId);
        ReflectionTestUtils.setField(mission, "id", id);
        ReflectionTestUtils.setField(mission, "progressRate", progressRate);
        return mission;
    }

    public static Mission 완료된_미션(
            long id,
            MissionType missionType,
            long userId
    ) {
        Mission mission = Mission.create(missionType, userId);
        ReflectionTestUtils.setField(mission, "id", id);
        ReflectionTestUtils.setField(mission, "progressRate", 100.0);
        ReflectionTestUtils.setField(mission, "isCompleted", true);
        return mission;
    }
}
