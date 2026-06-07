package gravit.code.admin.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record ChapterStatsResponse(

        List<UnitStatItemResponse> units
) {
    public static ChapterStatsResponse of(List<UnitStatItemResponse> units) {
        return ChapterStatsResponse.builder()
                .units(units)
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    public record UnitStatItemResponse(

            long unitId,

            String unitTitle,

            int averageProgress,

            long participantCount
    ) {
        public static UnitStatItemResponse of(
                long unitId,
                String unitTitle,
                int averageProgress,
                long participantCount
        ) {
            return UnitStatItemResponse.builder()
                    .unitId(unitId)
                    .unitTitle(unitTitle)
                    .averageProgress(averageProgress)
                    .participantCount(participantCount)
                    .build();
        }
    }
}
