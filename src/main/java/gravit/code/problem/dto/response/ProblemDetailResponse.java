package gravit.code.problem.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import gravit.code.problem.domain.ProblemType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ProblemDetailResponse(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        long id,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        ProblemType problemType,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        String instruction,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        String content,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @JsonProperty("isBookmarked")
        boolean isBookmarked
) {
}
