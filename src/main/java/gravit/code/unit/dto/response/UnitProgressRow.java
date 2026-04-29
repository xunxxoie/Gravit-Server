package gravit.code.unit.dto.response;

public record UnitProgressRow(
    long unitId,
    String title,
    long totalLessons,
    long solvedLessons
) {
    public UnitProgressSummary toSummary(){
        boolean completed = totalLessons > 0 && totalLessons == solvedLessons;

        return UnitProgressSummary.of(unitId, title, completed);
    }
}
