package gravit.code.unit.service;

import gravit.code.global.exception.domain.CustomErrorCode;
import gravit.code.global.exception.domain.RestApiException;
import gravit.code.unit.dto.response.RecommendedUnit;
import gravit.code.unit.dto.response.UnitProgressRow;
import gravit.code.unit.dto.response.UnitProgressSummary;
import gravit.code.unit.dto.response.UnitSummary;
import gravit.code.unit.repository.UnitRepository;
import gravit.code.unit.support.RandomUnitIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UnitQueryService {

    private static final ZoneId KST = ZoneId.of("Asia/Seoul");

    private final UnitRepository unitRepository;

    public List<UnitSummary> getAllUnitSummaryByChapterId(long chapterId) {
        return unitRepository.findAllUnitSummaryByChapterId(chapterId);
    }

    public UnitSummary getUnitSummaryByUnitId(long unitId){
        return unitRepository.findUnitSummaryById(unitId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.UNIT_NOT_FOUND));
    }

    public UnitSummary getUnitSummaryByLessonId(long lessonId) {
        return unitRepository.findUnitSummaryByLessonId(lessonId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.UNIT_NOT_FOUND));
    }

    public List<UnitProgressSummary> getAllUnitProgressSummariesInChapter(
            long chapterId,
            long userId
    ){
        return unitRepository.findUnitProgressByChapterIdAndUserId(chapterId, userId)
                .stream()
                .map(UnitProgressRow::toSummary)
                .toList();
    }

    public List<RecommendedUnit> getRecommendedUnits(long userId) {
        List<Long> allUnitIds = unitRepository.findAllUnitIdsOrderById();

        if (allUnitIds.size() < 2) {
            throw new RestApiException(CustomErrorCode.UNIT_NOT_FOUND);
        }

        long seed = userId * 31L + LocalDate.now(KST).toEpochDay();

        int[] indexes = RandomUnitIdGenerator.pickTwoDistinctIndexes(seed, allUnitIds.size());

        List<Long> targetIds = List.of(allUnitIds.get(indexes[0]), allUnitIds.get(indexes[1]));

        return unitRepository.findRecommendedUnitsByIds(targetIds);
    }
}
