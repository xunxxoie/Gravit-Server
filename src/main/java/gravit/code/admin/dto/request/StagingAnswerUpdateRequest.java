package gravit.code.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "스테이징 정답 수정 요청 (부분 수정)")
public record StagingAnswerUpdateRequest(

        @Schema(description = "내용(콤마 단일 텍스트)")
        String content,

        @Schema(description = "해설")
        String explanation
) {
}
