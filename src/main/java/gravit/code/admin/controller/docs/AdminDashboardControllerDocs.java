package gravit.code.admin.controller.docs;

import gravit.code.admin.dto.response.DashboardSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Admin Dashboard API", description = "백오피스 대시보드")
public interface AdminDashboardControllerDocs {

    @Operation(summary = "대시보드 요약", description = "전체 유저 수, 검수 대기 라벨 수, 미처리 신고 수를 반환합니다.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "조회 성공"))
    ResponseEntity<DashboardSummaryResponse> getSummary();
}
