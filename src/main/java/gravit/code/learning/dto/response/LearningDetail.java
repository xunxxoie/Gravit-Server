package gravit.code.learning.dto.response;

import gravit.code.unit.dto.response.UnitProgressSummary;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record LearningDetail(
        int consecutiveSolvedDays,
        long recentSolvedChapterId,
        String recentSolvedChapterTitle,
        double recentSolvedChapterProgressRate,
        List<UnitProgressSummary> units
) {
    public static LearningDetail of(
            int consecutiveSolvedDays,
            long recentSolvedChapterId,
            String recentSolvedChapterTitle,
            double recentSolvedChapterProgressRate,
            List<UnitProgressSummary> units
    ){
        return LearningDetail.builder()
                .consecutiveSolvedDays(consecutiveSolvedDays)
                .recentSolvedChapterId(recentSolvedChapterId)
                .recentSolvedChapterTitle(recentSolvedChapterTitle)
                .recentSolvedChapterProgressRate(recentSolvedChapterProgressRate)
                .units(units)
                .build();
    }
}
