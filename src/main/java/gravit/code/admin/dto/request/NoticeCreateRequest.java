package gravit.code.admin.dto.request;

import gravit.code.notice.domain.NoticeStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "공지 생성 요청")
public record NoticeCreateRequest(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "제목은 비어있을 수 없습니다.")
        @Size(max = 50, message = "제목은 50자를 초과할 수 없습니다.")
        String title,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "요약은 비어있을 수 없습니다.")
        @Size(max = 255, message = "요약은 255자를 초과할 수 없습니다.")
        String summary,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "본문은 비어있을 수 없습니다.")
        String content,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "DRAFT 또는 PUBLISHED (작성 시 ARCHIVED 불가)")
        @NotNull(message = "공지 상태는 필수입니다.")
        NoticeStatus status,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "상단 고정 여부는 필수입니다.")
        Boolean pinned
) {
}
