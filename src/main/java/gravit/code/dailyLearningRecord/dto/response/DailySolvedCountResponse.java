package gravit.code.dailyLearningRecord.dto.response;

import java.time.LocalDate;

public record DailySolvedCountResponse(
        LocalDate date,
        int solvedLessonCount
) {
}
