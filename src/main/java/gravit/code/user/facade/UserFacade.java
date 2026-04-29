package gravit.code.user.facade;

import gravit.code.chapter.domain.Chapter;
import gravit.code.chapter.service.ChapterQueryService;
import gravit.code.dailyLearningRecord.dto.response.WeeklyLearningRecord;
import gravit.code.dailyLearningRecord.service.DailyLearningRecordService;
import gravit.code.global.annotation.Facade;
import gravit.code.league.dto.response.LeagueDetail;
import gravit.code.learning.domain.Learning;
import gravit.code.learning.dto.response.LearningDetail;
import gravit.code.learning.service.LearningProgressRateService;
import gravit.code.learning.service.LearningService;
import gravit.code.mission.dto.response.MissionDetail;
import gravit.code.mission.service.MissionService;
import gravit.code.unit.dto.response.RecommendedUnit;
import gravit.code.unit.dto.response.UnitProgressSummary;
import gravit.code.unit.service.UnitQueryService;
import gravit.code.user.domain.User;
import gravit.code.user.dto.response.MainPageResponse;
import gravit.code.user.dto.response.UserLevelDetail;
import gravit.code.user.service.UserService;
import gravit.code.userLeague.service.UserLeagueService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Facade
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final UserLeagueService userLeagueService;
    private final UnitQueryService unitQueryService;
    private final LearningService learningService;
    private final ChapterQueryService chapterQueryService;
    private final LearningProgressRateService learningProgressRateService;
    private final MissionService missionService;
    private final DailyLearningRecordService dailyLearningRecordService;

    @Transactional(readOnly = true)
    public MainPageResponse getMainPage(long userId) {
        User user = userService.getUser(userId);
        Learning learning = learningService.getLearning(userId);

        UserLevelDetail userLevelDetail = user.getLevel().getUserLevelDetail();
        LeagueDetail leagueDetail = userLeagueService.getUserLeagueDetail(userId);
        LearningDetail learningDetail = getLearningDetail(userId, learning);
        List<RecommendedUnit> recommendedUnits = unitQueryService.getRecommendedUnits(userId);
        WeeklyLearningRecord weeklyLearningRecord = dailyLearningRecordService.getWeeklyLearningRecord(userId);
        MissionDetail missionDetail = missionService.getMissionDetail(userId);

        return MainPageResponse.of(
                user.getProfileImgNumber(),
                user.getNickname(),
                userLevelDetail,
                leagueDetail,
                learningDetail,
                recommendedUnits,
                weeklyLearningRecord,
                missionDetail
        );
    }

    private LearningDetail getLearningDetail(
            long userId,
            Learning learning
    ) {
        long chapterId = learning.getRecentSolvedChapterId();

        List<UnitProgressSummary> units = unitQueryService.getAllUnitProgressSummariesInChapter(chapterId, userId);

        Chapter recentSolvedChapter = chapterQueryService.getChapter(chapterId);

        double chapterProgressRate = learningProgressRateService.getChapterProgress(chapterId, userId);

        return LearningDetail.of(
                learning.getConsecutiveSolvedDays(),
                recentSolvedChapter.getId(),
                recentSolvedChapter.getTitle(),
                chapterProgressRate,
                units
        );
    }
}
