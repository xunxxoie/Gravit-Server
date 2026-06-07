package gravit.code.admin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "대시보드 요약")
public record DashboardSummaryResponse(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        long totalUsers,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        long pendingLabelsCount,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        long unresolvedReportsCount
) {
    public static DashboardSummaryResponse of(
            long totalUsers,
            long pendingLabelsCount,
            long unresolvedReportsCount
    ) {
        return new DashboardSummaryResponse(totalUsers, pendingLabelsCount, unresolvedReportsCount);
    }
}
