package gravit.code.dailyLearningRecord.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record WeeklyLearningRecordResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        boolean MONDAY,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        boolean TUESDAY,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        boolean WEDNESDAY,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        boolean THURSDAY,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        boolean FRIDAY,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        boolean SATURDAY,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        boolean SUNDAY
) {
}
