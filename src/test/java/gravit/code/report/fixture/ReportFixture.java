package gravit.code.report.fixture;

import gravit.code.report.domain.Report;
import gravit.code.report.domain.ReportType;
import org.springframework.test.util.ReflectionTestUtils;

public class ReportFixture {

    public static Report 기본_신고(
            long problemId,
            long userId
    ) {
        Report report = Report.builder()
                .reportType(ReportType.CONTENT_ERROR)
                .content("문제 내용이 잘못되었습니다.")
                .problemId(problemId)
                .userId(userId)
                .build();
        ReflectionTestUtils.setField(report, "id", 1L);
        return report;
    }

    public static Report 저장된_신고(
            long id,
            ReportType reportType,
            long problemId,
            long userId
    ) {
        Report report = Report.builder()
                .reportType(reportType)
                .content("신고 내용" + id)
                .problemId(problemId)
                .userId(userId)
                .build();
        ReflectionTestUtils.setField(report, "id", id);
        return report;
    }
}
