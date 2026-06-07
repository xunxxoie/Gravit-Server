package gravit.code.admin.dto.response;

import gravit.code.notice.domain.Notice;
import gravit.code.notice.domain.NoticeStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "공지 목록 항목")
public record NoticeListItemResponse(

        long noticeId,

        String title,

        NoticeStatus status,

        boolean pinned,

        LocalDateTime publishedAt,

        LocalDateTime createdAt
) {
    public static NoticeListItemResponse from(Notice notice) {
        return new NoticeListItemResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getStatus(),
                notice.isPinned(),
                notice.getPublishedAt(),
                notice.getCreatedAt()
        );
    }
}
