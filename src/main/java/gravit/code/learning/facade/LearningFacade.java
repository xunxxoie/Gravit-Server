package gravit.code.learning.facade;

import gravit.code.chapter.dto.response.TopChapterResponse;
import gravit.code.dailyLearningRecord.dto.response.DailySolvedCountResponse;
import gravit.code.dailyLearningRecord.dto.response.WeeklyLearningReportResponse;
import gravit.code.dailyLearningRecord.service.DailyLearningRecordService;
import gravit.code.global.annotation.Facade;
import gravit.code.learning.dto.response.LearningHistoryResponse;
import gravit.code.learning.dto.response.LearningSummaryResponse;
import gravit.code.learning.dto.response.MyPageLearningResponse;
import gravit.code.learning.dto.response.WeakConceptResponse;
import gravit.code.learning.service.LearningProgressRateService;
import gravit.code.lesson.service.LessonQueryService;
import gravit.code.lesson.service.LessonSubmissionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Facade
@RequiredArgsConstructor
public class LearningFacade {

    private final LearningProgressRateService learningProgressRateService;
    private final LessonSubmissionQueryService lessonSubmissionQueryService;
    private final LessonQueryService lessonQueryService;
    private final DailyLearningRecordService dailyLearningRecordService;

    @Transactional(readOnly = true)
    public MyPageLearningResponse getMyPageLearning(long userId) {
        LearningSummaryResponse learningSummary = getLearningSummary(userId);
        LearningHistoryResponse learningHistory = getLearningHistory(userId);
        WeeklyLearningReportResponse weeklyReport = getWeeklyLearningReport(userId);
        List<TopChapterResponse> topChapters = getTopChapters(userId);
        List<WeakConceptResponse> weakConcepts = getWeakConcepts(userId);

        return MyPageLearningResponse.of(
                learningSummary,
                learningHistory,
                weeklyReport,
                topChapters,
                weakConcepts
        );
    }

    private LearningSummaryResponse getLearningSummary(long userId) {
        int rankPercentile = learningProgressRateService.getLearningRankPercentile(userId);
        int completedLessonCount = lessonSubmissionQueryService.getCompletedLessonCount(userId);
        int totalLessonCount = lessonQueryService.getTotalLessonCount();
        double totalLearningHours = lessonSubmissionQueryService.getTotalLearningHours(userId);
        int averageAccuracy = lessonSubmissionQueryService.getAverageAccuracy(userId);

        return LearningSummaryResponse.of(
                rankPercentile,
                completedLessonCount,
                totalLessonCount,
                totalLearningHours,
                averageAccuracy
        );
    }

    private LearningHistoryResponse getLearningHistory(long userId) {
        List<DailySolvedCountResponse> dailySolvedCountResponses = dailyLearningRecordService.getDailySolvedCounts(userId, LocalDate.now().getYear());
        int peakLearningHour = lessonSubmissionQueryService.getPeakLearningHour(userId);

        return LearningHistoryResponse.of(
                dailySolvedCountResponses,
                peakLearningHour
        );
    }

    private WeeklyLearningReportResponse getWeeklyLearningReport(long userId) {
        return dailyLearningRecordService.getWeeklyLearningReport(userId);
    }

    private List<TopChapterResponse> getTopChapters(long userId) {
        return lessonSubmissionQueryService.getTopChapters(userId);
    }

    private List<WeakConceptResponse> getWeakConcepts(long userId) {
        return lessonSubmissionQueryService.getWeakConcepts(userId);
    }
}
