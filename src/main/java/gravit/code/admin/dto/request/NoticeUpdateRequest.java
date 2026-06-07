package gravit.code.admin.dto.request;

import gravit.code.notice.domain.NoticeStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "공지 부분 수정 요청 (미제공 필드는 유지)")
public record NoticeUpdateRequest(

        @Schema(description = "제목")
        @Size(max = 50, message = "제목은 50자를 초과할 수 없습니다.")
        String title,

        @Schema(description = "요약")
        @Size(max = 255, message = "요약은 255자를 초과할 수 없습니다.")
        String summary,

        @Schema(description = "본문")
        String content,

        @Schema(description = "상태 (전이 가드 적용)")
        NoticeStatus status,

        @Schema(description = "상단 고정 여부")
        Boolean pinned
) {
}
