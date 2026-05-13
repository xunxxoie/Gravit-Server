package gravit.code.lesson.repository;

import gravit.code.lesson.domain.LessonSubmission;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LessonSubmissionRepository extends JpaRepository<LessonSubmission, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<LessonSubmission> findByLessonIdAndUserId(
            long lessonId,
            long userId
    );

    long countByUserId(long userId);

    @Query("""
        SELECT COUNT(DISTINCT l.id)
        FROM LessonSubmission ls
        JOIN Lesson l ON l.id = ls.lessonId
        JOIN Unit u ON u.id = l.unitId
        WHERE u.chapterId = :chapterId AND ls.userId = :userId
    """)
    int countSolvedLessonByChapterIdAndUserId(
            @Param("chapterId") long chapterId,
            @Param("userId") long userId
    );

    @Query("""
        SELECT COUNT(DISTINCT l.id)
        FROM LessonSubmission ls
        JOIN Lesson l ON l.id = ls.lessonId
        WHERE l.unitId = :unitId AND ls.userId = :userId
    """)
    int countSolvedLessonByUnitIdAndUserId(
            @Param("unitId") long unitId,
            @Param("userId") long userId
    );

    @Query("""
        SELECT COUNT(ls.id)
        FROM LessonSubmission ls
        WHERE ls.lessonId = :lessonId AND ls.userId = :userId
    """)
    int countLessonSubmissionByLessonIdAndUserId(
            @Param("lessonId") long lessonId,
            @Param("userId") long userId
    );

    boolean existsByLessonIdAndUserId(
            long lessonId,
            long userId
    );

    @Query(value = """
            SELECT CAST(CEIL(CUME_DIST() OVER (ORDER BY solved_count DESC) * 100) AS INTEGER)
            FROM (
                SELECT u.id AS user_id, COUNT(ls.id) AS solved_count
                FROM users u
                LEFT JOIN lesson_submission ls ON ls.user_id = u.id
                WHERE u.deleted_at IS NULL
                GROUP BY u.id
            ) ranked
            WHERE user_id = :userId
            """, nativeQuery = true)
    Optional<Integer> findLearningRateTopPercent(@Param("userId") long userId);

    @Query("""
        SELECT COALESCE(SUM(ls.learningTime), 0)
        FROM LessonSubmission ls
        WHERE ls.userId = :userId
    """)
    int getTotalLearningTime(@Param("userId") long userId);

    @Query("""
        SELECT COALESCE(AVG(ls.accuracy), 0)
        FROM LessonSubmission ls
        WHERE ls.userId = :userId
    """)
    int getAverageAccuracy(@Param("userId") long userId);

    @Query(value = """
        SELECT CAST(EXTRACT(HOUR FROM updated_at) AS INTEGER) AS hour
        FROM lesson_submission
        WHERE user_id = :userId AND updated_at IS NOT NULL
        GROUP BY hour
        ORDER BY COUNT(*) DESC, hour ASC
        LIMIT 1
    """, nativeQuery = true)
    Optional<Integer> getPeakLearningHour(@Param("userId") long userId);

    @Query(value = """
        SELECT
            c.id,
            c.title,
            COUNT(DISTINCT l.id)
        FROM lesson_submission ls
        JOIN lesson l ON l.id = ls.lesson_id
        JOIN unit u ON u.id = l.unit_id
        JOIN chapter c ON c.id = u.chapter_id
        WHERE ls.user_id = :userId
          AND ls.updated_at >= :weekStart
          AND ls.updated_at < :nextWeekStart
        GROUP BY c.id, c.title
        ORDER BY COUNT(DISTINCT l.id) DESC, c.id ASC
        LIMIT 3
    """, nativeQuery = true)
    List<Object[]> findTopChaptersByUserIdInWeek(
            @Param("userId") long userId,
            @Param("weekStart") LocalDateTime weekStart,
            @Param("nextWeekStart") LocalDateTime nextWeekStart
    );

    @Query("""
        SELECT COUNT(ls.id)
        FROM LessonSubmission ls
        WHERE ls.userId = :userId
          AND ls.updatedAt >= :weekStart
          AND ls.updatedAt < :nextWeekStart
    """)
    long countSolvedLessonsByUserIdInWeek(
            @Param("userId") long userId,
            @Param("weekStart") LocalDateTime weekStart,
            @Param("nextWeekStart") LocalDateTime nextWeekStart
    );

    @Query(value = """
        SELECT
            l.id,
            u.title,
            c.title,
            ls.accuracy,
            (SELECT COUNT(p.id) FROM problem p WHERE p.lesson_id = l.id)
        FROM lesson_submission ls
        JOIN lesson l ON l.id = ls.lesson_id
        JOIN unit u ON u.id = l.unit_id
        JOIN chapter c ON c.id = u.chapter_id
        WHERE ls.user_id = :userId
        ORDER BY ls.accuracy ASC, l.id ASC
        LIMIT 7
    """, nativeQuery = true)
    List<Object[]> findWeakLessonsByUserId(@Param("userId") long userId);
}
