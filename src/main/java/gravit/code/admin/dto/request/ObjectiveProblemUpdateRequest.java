package gravit.code.admin.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "객관식 문제 수정 요청 (instruction/content 부분 수정, options 제공 시 4개 전체 교체)")
public record ObjectiveProblemUpdateRequest(

        @Schema(description = "지시문 (부분 수정)")
        String instruction,

        @Schema(description = "본문 (부분 수정)")
        String content,

        @Schema(description = "옵션 4개 전체 교체 (제공 시 정확히 4개, 정답 1개)")
        @Valid
        @Size(min = 4, max = 4, message = "옵션은 정확히 4개여야 합니다.")
        List<ObjectiveOptionUpdateRequest> options
) {
    @Schema(description = "객관식 옵션 수정 항목")
    public record ObjectiveOptionUpdateRequest(

            @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull(message = "optionId 는 필수입니다.")
            Long optionId,

            @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
            @NotBlank(message = "옵션 내용은 비어있을 수 없습니다.")
            String content,

            @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
            @NotBlank(message = "옵션 해설은 비어있을 수 없습니다.")
            String explanation,

            @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("isAnswer")
            @NotNull(message = "isAnswer 는 필수입니다.")
            Boolean isAnswer
    ) {
    }
}
