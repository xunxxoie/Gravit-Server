package gravit.code.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "주관식 문제 수정 요청 (instruction/content 부분 수정, answer 단일)")
public record SubjectiveProblemUpdateRequest(

        @Schema(description = "지시문 (부분 수정)")
        String instruction,

        @Schema(description = "본문 (부분 수정)")
        String content,

        @Schema(description = "단일 정답 (제공 시 수정)")
        @Valid
        SubjectiveAnswerUpdateRequest answer
) {
    @Schema(description = "주관식 정답 수정 항목")
    public record SubjectiveAnswerUpdateRequest(

            @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull(message = "answerId 는 필수입니다.")
            Long answerId,

            @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "콤마 단일 텍스트")
            @NotBlank(message = "정답 내용은 비어있을 수 없습니다.")
            String content,

            @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
            @NotBlank(message = "정답 해설은 비어있을 수 없습니다.")
            String explanation
    ) {
    }
}
