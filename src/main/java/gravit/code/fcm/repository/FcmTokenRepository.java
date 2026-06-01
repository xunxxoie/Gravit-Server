package gravit.code.fcm.repository;

import gravit.code.fcm.domain.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
    Optional<FcmToken> findByDeviceId(String deviceId);

    boolean existsByUserIdAndDeviceId(
            long userId,
            String deviceId
    );
}
