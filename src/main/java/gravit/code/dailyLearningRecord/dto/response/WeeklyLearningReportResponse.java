package gravit.code.dailyLearningRecord.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Builder(access = AccessLevel.PRIVATE)
public record WeeklyLearningReportResponse(
        int MONDAY,
        int TUESDAY,
        int WEDNESDAY,
        int THURSDAY,
        int FRIDAY,
        int SATURDAY,
        int SUNDAY,
        int weekOverWeekDelta,
        List<Integer> recentWeeklyCounts
) {
    public static WeeklyLearningReportResponse of(
            Map<DayOfWeek, Integer> thisWeekCountsByDay,
            int weekOverWeekDelta,
            List<Integer> recentWeeklyCounts
    ) {
        return WeeklyLearningReportResponse.builder()
                .MONDAY(thisWeekCountsByDay.getOrDefault(DayOfWeek.MONDAY, 0))
                .TUESDAY(thisWeekCountsByDay.getOrDefault(DayOfWeek.TUESDAY, 0))
                .WEDNESDAY(thisWeekCountsByDay.getOrDefault(DayOfWeek.WEDNESDAY, 0))
                .THURSDAY(thisWeekCountsByDay.getOrDefault(DayOfWeek.THURSDAY, 0))
                .FRIDAY(thisWeekCountsByDay.getOrDefault(DayOfWeek.FRIDAY, 0))
                .SATURDAY(thisWeekCountsByDay.getOrDefault(DayOfWeek.SATURDAY, 0))
                .SUNDAY(thisWeekCountsByDay.getOrDefault(DayOfWeek.SUNDAY, 0))
                .weekOverWeekDelta(weekOverWeekDelta)
                .recentWeeklyCounts(recentWeeklyCounts)
                .build();
    }
}
