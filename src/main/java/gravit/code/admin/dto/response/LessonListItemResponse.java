package gravit.code.admin.dto.response;

import gravit.code.lesson.domain.Lesson;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "레슨 목록 항목")
public record LessonListItemResponse(

        long lessonId,

        String title
) {
    public static LessonListItemResponse from(Lesson lesson) {
        return new LessonListItemResponse(lesson.getId(), lesson.getTitle());
    }
}
