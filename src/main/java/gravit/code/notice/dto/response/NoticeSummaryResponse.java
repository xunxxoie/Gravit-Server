package gravit.code.notice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record NoticeSummaryResponse(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        long id,
        @NotNull
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String title,
        @NotNull
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String summary,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        boolean pinned,
        @NotNull
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime publishedAt
) {
}
