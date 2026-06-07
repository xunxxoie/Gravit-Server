package gravit.code.admin.service;

import gravit.code.admin.domain.staging.LabelStatus;
import gravit.code.admin.dto.response.DashboardSummaryResponse;
import gravit.code.admin.repository.AdminReportRepository;
import gravit.code.admin.repository.AdminUserRepository;
import gravit.code.admin.repository.StagingLabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final AdminUserRepository adminUserRepository;
    private final StagingLabelRepository stagingLabelRepository;
    private final AdminReportRepository adminReportRepository;

    @Transactional(readOnly = true)
    public DashboardSummaryResponse getSummary() {
        long totalUsers = adminUserRepository.countActiveUsers();
        long pendingLabelsCount = stagingLabelRepository.countByStatus(LabelStatus.PENDING);
        long unresolvedReportsCount = adminReportRepository.countByIsResolvedFalse();

        return DashboardSummaryResponse.of(totalUsers, pendingLabelsCount, unresolvedReportsCount);
    }
}
