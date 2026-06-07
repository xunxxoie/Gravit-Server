package gravit.code.admin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "챕터 유닛별 통계")
public record ChapterStatsResponse(

        List<UnitStatItemResponse> units
) {
    public static ChapterStatsResponse of(List<UnitStatItemResponse> units) {
        return new ChapterStatsResponse(units);
    }

    @Schema(description = "유닛 통계 항목")
    public record UnitStatItemResponse(

            long unitId,

            String unitTitle,

            @Schema(description = "참여율(참여자/전체 유저, 0~100 정수)")
            int averageProgress,

            @Schema(description = "참여 인원(해당 유닛 레슨 제출 distinct 유저)")
            long participantCount
    ) {
        public static UnitStatItemResponse of(
                long unitId,
                String unitTitle,
                int averageProgress,
                long participantCount
        ) {
            return new UnitStatItemResponse(unitId, unitTitle, averageProgress, participantCount);
        }
    }
}
