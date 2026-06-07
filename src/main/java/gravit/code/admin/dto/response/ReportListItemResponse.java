package gravit.code.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import gravit.code.report.domain.Report;
import gravit.code.report.domain.ReportType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "신고 목록 항목")
public record ReportListItemResponse(

        long reportId,

        ReportType reportType,

        long problemId,

        @JsonProperty("isResolved")
        boolean isResolved,

        LocalDateTime submittedAt
) {
    public static ReportListItemResponse from(Report report) {
        return new ReportListItemResponse(
                report.getId(),
                report.getReportType(),
                report.getProblemId(),
                report.isResolved(),
                report.getSubmittedAt()
        );
    }
}
