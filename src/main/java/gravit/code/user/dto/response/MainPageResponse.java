package gravit.code.user.dto.response;

import gravit.code.dailyLearningRecord.dto.response.WeeklyLearningRecord;
import gravit.code.league.dto.response.LeagueDetail;
import gravit.code.learning.dto.response.LearningDetail;
import gravit.code.mission.dto.response.MissionDetail;
import gravit.code.unit.dto.response.RecommendedUnit;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record MainPageResponse(
        int profileImgNumber,
        String nickname,
        UserLevelDetail userLevelDetail,
        LeagueDetail leagueDetail,
        LearningDetail learningDetail,
        List<RecommendedUnit> recommendedUnits,
        WeeklyLearningRecord weeklyLearningRecord,
        MissionDetail missionDetail
) {
    public static MainPageResponse of(
            int profileImgNumber,
            String nickname,
            UserLevelDetail userLevelDetail,
            LeagueDetail leagueDetail,
            LearningDetail learningDetail,
            List<RecommendedUnit> recommendedUnits,
            WeeklyLearningRecord weeklyLearningRecord,
            MissionDetail missionDetail
    ) {
        return MainPageResponse.builder()
                .profileImgNumber(profileImgNumber)
                .nickname(nickname)
                .userLevelDetail(userLevelDetail)
                .leagueDetail(leagueDetail)
                .learningDetail(learningDetail)
                .recommendedUnits(recommendedUnits)
                .weeklyLearningRecord(weeklyLearningRecord)
                .missionDetail(missionDetail)
                .build();
    }
}