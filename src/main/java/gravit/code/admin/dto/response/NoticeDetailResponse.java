package gravit.code.admin.dto.response;

import gravit.code.notice.domain.Notice;
import gravit.code.notice.domain.NoticeStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "공지 상세")
public record NoticeDetailResponse(

        long noticeId,

        String title,

        String summary,

        String content,

        NoticeStatus status,

        boolean pinned,

        LocalDateTime publishedAt,

        LocalDateTime createdAt
) {
    public static NoticeDetailResponse from(Notice notice) {
        return new NoticeDetailResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getSummary(),
                notice.getContent(),
                notice.getStatus(),
                notice.isPinned(),
                notice.getPublishedAt(),
                notice.getCreatedAt()
        );
    }
}
