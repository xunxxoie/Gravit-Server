package gravit.code.notification.repository;

import gravit.code.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // 전체 활성 유저에게 동일 알림을 1쿼리로 적재 (공지 등 브로드캐스트용)
    @Modifying
    @Query(value = """
        INSERT INTO notification (user_id, type, message, target_id, is_read, created_at, updated_at)
        SELECT u.id, :type, :message, :targetId, FALSE, :now, :now
        FROM users u
        WHERE u.deleted_at IS NULL
        """, nativeQuery = true)
    int insertForAllActiveUsers(
            @Param("type") String type,
            @Param("message") String message,
            @Param("targetId") Long targetId,
            @Param("now") LocalDateTime now
    );
}
