package gravit.code.admin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AdminNoticeSummaryResponse(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        long id,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        String title,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        String summary,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        boolean pinned,
        LocalDateTime publishedAt
) {
}
