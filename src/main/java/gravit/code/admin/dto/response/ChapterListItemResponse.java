package gravit.code.admin.dto.response;

import gravit.code.chapter.domain.Chapter;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "챕터 목록 항목")
public record ChapterListItemResponse(

        long chapterId,

        String title,

        String description
) {
    public static ChapterListItemResponse from(Chapter chapter) {
        return new ChapterListItemResponse(chapter.getId(), chapter.getTitle(), chapter.getDescription());
    }
}
