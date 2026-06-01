package gravit.code.notice.dto.response;

import gravit.code.notice.domain.Notice;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NoticeDetailResponse(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        long id,
        @NotNull
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String title,
        @NotNull
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String content,
        @NotNull
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String authorName,
        @NotNull
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime createdAt,
        @NotNull
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime updatedAt,
        @NotNull
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String status,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        boolean pinned,
        @NotNull
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime publishedAt
) {
    public static NoticeDetailResponse from(Notice notice) {
        return NoticeDetailResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .authorName(notice.getAuthor().getNickname())
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .publishedAt(notice.getPublishedAt())
                .status(notice.getStatus().name())
                .pinned(notice.isPinned())
                .build();
    }
}
