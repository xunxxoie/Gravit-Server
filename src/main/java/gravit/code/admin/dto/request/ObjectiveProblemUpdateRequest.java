package gravit.code.admin.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ObjectiveProblemUpdateRequest(

        String instruction,

        String content,

        @Valid
        @Size(min = 4, max = 4, message = "옵션은 정확히 4개여야 합니다.")
        List<ObjectiveOptionUpdateRequest> options
) {
    public record ObjectiveOptionUpdateRequest(

            @NotNull(message = "optionId 는 필수입니다.")
            Long optionId,

            @NotBlank(message = "옵션 내용은 비어있을 수 없습니다.")
            String content,

            @NotBlank(message = "옵션 해설은 비어있을 수 없습니다.")
            String explanation,

            @JsonProperty("isAnswer")
            @NotNull(message = "isAnswer 는 필수입니다.")
            Boolean isAnswer
    ) {
    }
}
