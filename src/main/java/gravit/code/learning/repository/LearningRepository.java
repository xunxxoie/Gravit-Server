package gravit.code.learning.repository;

import gravit.code.learning.domain.Learning;
import gravit.code.learning.dto.internal.ConsecutiveAtRiskUser;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LearningRepository extends JpaRepository<Learning,Long> {

    Optional<Learning> findByUserId(long userId);

    @Lock(LockModeType.OPTIMISTIC)
    List<Learning> findAll();

    @Query("""
            SELECT new gravit.code.learning.dto.internal.ConsecutiveAtRiskUser(l.userId, l.consecutiveSolvedDays)
            FROM Learning l
            WHERE l.consecutiveSolvedDays >= 1 AND l.todaySolved = false
    """)
    List<ConsecutiveAtRiskUser> findConsecutiveAtRiskUsers();

    @Query("""
            SELECT l.userId
            FROM Learning l
            WHERE l.consecutiveSolvedDays = 0 AND l.todaySolved = false
    """)
    List<Long> findDailyIncompleteUserIds();
}
