package gravit.code.admin.dto.response;

import gravit.code.lesson.domain.Lesson;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "레슨 상세")
public record LessonDetailResponse(

        long lessonId,

        long unitId,

        String title,

        long problemCount
) {
    public static LessonDetailResponse of(
            Lesson lesson,
            long problemCount
    ) {
        return new LessonDetailResponse(lesson.getId(), lesson.getUnitId(), lesson.getTitle(), problemCount);
    }
}
