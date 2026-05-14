package gravit.code.dailyLearningRecord.service;

import gravit.code.dailyLearningRecord.domain.DailyLearningRecord;
import gravit.code.dailyLearningRecord.dto.response.DailySolvedCountResponse;
import gravit.code.dailyLearningRecord.dto.response.WeeklyLearningRecordResponse;
import gravit.code.dailyLearningRecord.dto.response.WeeklyLearningReportResponse;
import gravit.code.dailyLearningRecord.repository.DailyLearningRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DailyLearningRecordService {

    private static final ZoneId KST = ZoneId.of("Asia/Seoul");

    private final DailyLearningRecordRepository dailyLearningRecordRepository;

    @Transactional(readOnly = true)
    public WeeklyLearningRecordResponse getWeeklyLearningRecord(long userId) {
        LocalDate today = LocalDate.now(KST);
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        LocalDate sunday = today.with(DayOfWeek.SUNDAY);

        Set<DayOfWeek> solvedDays = dailyLearningRecordRepository
                .findSolvedDatesByUserIdAndDateRange(userId, monday, sunday)
                .stream()
                .map(LocalDate::getDayOfWeek)
                .collect(Collectors.toUnmodifiableSet());

        return new WeeklyLearningRecordResponse(
                solvedDays.contains(DayOfWeek.MONDAY),
                solvedDays.contains(DayOfWeek.TUESDAY),
                solvedDays.contains(DayOfWeek.WEDNESDAY),
                solvedDays.contains(DayOfWeek.THURSDAY),
                solvedDays.contains(DayOfWeek.FRIDAY),
                solvedDays.contains(DayOfWeek.SATURDAY),
                solvedDays.contains(DayOfWeek.SUNDAY)
        );
    }

    @Transactional(readOnly = true)
    public List<DailySolvedCountResponse> getDailySolvedCounts(
            long userId,
            int year
    ) {
        LocalDate today = LocalDate.now(KST);
        LocalDate beginDate = LocalDate.of(year, 1, 1);
        LocalDate yearEnd = LocalDate.of(year, 12, 31);
        LocalDate endDate = yearEnd.isAfter(today) ? today : yearEnd;

        return dailyLearningRecordRepository.findDailySolvedCountsByUserIdBetween(userId, beginDate, endDate);
    }

    @Transactional(readOnly = true)
    public WeeklyLearningReportResponse getWeeklyLearningReport(long userId) {
        LocalDate today = LocalDate.now(KST);
        LocalDate thisMonday = today.with(DayOfWeek.MONDAY);
        LocalDate thisSunday = today.with(DayOfWeek.SUNDAY);
        LocalDate threeWeeksAgoMonday = thisMonday.minusWeeks(3);

        List<DailyLearningRecord> records = dailyLearningRecordRepository
                .findByUserIdAndSolvedDateBetween(userId, threeWeeksAgoMonday, thisSunday);

        Map<DayOfWeek, Integer> thisWeekCountsByDay = records.stream()
                .filter(dlr -> !dlr.getSolvedDate().isBefore(thisMonday))
                .collect(Collectors.toMap(
                        dlr -> dlr.getSolvedDate().getDayOfWeek(),
                        DailyLearningRecord::getSolvedLessonCount
                ));

        Map<LocalDate, Integer> countsByWeekStart = records.stream()
                .collect(Collectors.groupingBy(
                        dlr -> dlr.getSolvedDate().with(DayOfWeek.MONDAY),
                        Collectors.summingInt(DailyLearningRecord::getSolvedLessonCount)
                ));

        List<Integer> recentWeeklyCounts = new ArrayList<>(4);

        for (int weeksAgo = 3; weeksAgo >= 0; weeksAgo--) {
            recentWeeklyCounts.add(countsByWeekStart.getOrDefault(thisMonday.minusWeeks(weeksAgo), 0));
        }

        int weekOverWeekDelta = recentWeeklyCounts.get(3) - recentWeeklyCounts.get(2);

        return WeeklyLearningReportResponse.of(
                thisWeekCountsByDay,
                weekOverWeekDelta,
                recentWeeklyCounts
        );
    }
}
