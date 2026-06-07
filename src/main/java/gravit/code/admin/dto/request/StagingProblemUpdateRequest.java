package gravit.code.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "스테이징 문제 수정 요청 (부분 수정)")
public record StagingProblemUpdateRequest(

        @Schema(description = "지시문")
        String instruction,

        @Schema(description = "본문")
        String content
) {
}
