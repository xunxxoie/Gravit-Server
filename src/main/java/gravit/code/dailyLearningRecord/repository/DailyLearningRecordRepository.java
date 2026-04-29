package gravit.code.dailyLearningRecord.repository;

import gravit.code.dailyLearningRecord.domain.DailyLearningRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyLearningRecordRepository extends JpaRepository<DailyLearningRecord, Long> {
}
