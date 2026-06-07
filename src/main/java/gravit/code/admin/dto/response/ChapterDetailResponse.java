package gravit.code.admin.dto.response;

import gravit.code.chapter.domain.Chapter;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "챕터 상세")
public record ChapterDetailResponse(

        long chapterId,

        String title,

        String description,

        long unitCount
) {
    public static ChapterDetailResponse of(
            Chapter chapter,
            long unitCount
    ) {
        return new ChapterDetailResponse(chapter.getId(), chapter.getTitle(), chapter.getDescription(), unitCount);
    }
}
