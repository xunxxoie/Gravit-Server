package gravit.code.learning.dto.response;

import gravit.code.chapter.dto.response.TopChapterResponse;
import gravit.code.dailyLearningRecord.dto.response.WeeklyLearningReportResponse;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record MyPageLearningResponse(
        WeeklyLearningReportResponse weeklyReport,
        List<TopChapterResponse> topChapters,
        List<WeakConceptResponse> weakConcepts
) {
    public static MyPageLearningResponse of(
            WeeklyLearningReportResponse weeklyReport,
            List<TopChapterResponse> topChapters,
            List<WeakConceptResponse> weakConcepts
    ) {
        return MyPageLearningResponse.builder()
                .weeklyReport(weeklyReport)
                .topChapters(topChapters)
                .weakConcepts(weakConcepts)
                .build();
    }
}
