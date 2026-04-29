package gravit.code.unit.dto.response;

public record RecommendedUnit(
        long unitId,
        String unitTitle,
        long chapterId,
        String chapterTitle
) {
}