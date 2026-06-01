package gravit.code.admin.dto.response;

import gravit.code.notice.domain.Notice;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AdminNoticeDetailResponse(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        long noticeId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        String title,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        String contents,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        String authorName,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        LocalDateTime createdAt,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        LocalDateTime updatedAt,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        String noticeType,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        boolean pinned,
        LocalDateTime publishedAt
) {
    public static AdminNoticeDetailResponse from(Notice notice) {
        return AdminNoticeDetailResponse.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .contents(notice.getContent())
                .authorName(notice.getAuthor().getNickname())
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .publishedAt(notice.getPublishedAt())
                .noticeType(notice.getStatus().name())
                .pinned(notice.isPinned())
                .build();
    }
}
