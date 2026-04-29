package gravit.code.dailyLearningRecord.dto.response;

public record WeeklyLearningRecord(
        boolean MONDAY,
        boolean TUESDAY,
        boolean WEDNESDAY,
        boolean THURSDAY,
        boolean FRIDAY,
        boolean SATURDAY,
        boolean SUNDAY
) {
}
