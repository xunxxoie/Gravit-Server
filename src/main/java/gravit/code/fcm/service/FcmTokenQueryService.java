package gravit.code.fcm.service;

import gravit.code.fcm.dto.response.FcmTokenExistsResponse;
import gravit.code.fcm.repository.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FcmTokenQueryService {

    private final FcmTokenRepository fcmTokenRepository;

    @Transactional(readOnly = true)
    public FcmTokenExistsResponse checkFcmTokenExist(
            long userId,
            String deviceId
    ) {
        boolean registered = fcmTokenRepository.existsByUserIdAndDeviceId(userId, deviceId);

        return FcmTokenExistsResponse.create(registered);
    }
}
