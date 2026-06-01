package gravit.code.fcm.service;

import gravit.code.fcm.domain.FcmToken;
import gravit.code.fcm.dto.response.FcmTokenExistsResponse;
import gravit.code.fcm.repository.FcmTokenRepository;
import gravit.code.support.TCSpringBootTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@TCSpringBootTest
@Transactional
class FcmTokenQueryServiceIntegrationTest extends FcmServiceIntegrationTestBase {

    @Autowired
    private FcmTokenQueryService fcmTokenQueryService;

    @Autowired
    private FcmTokenRepository fcmTokenRepository;

    @Nested
    @DisplayName("FCM 토큰 등록 여부를 확인할 때")
    class CheckFcmTokenExist {

        @Test
        void 해당_기기에_토큰이_등록되어_있으면_true를_반환한다() {
            // given
            fcmTokenRepository.save(FcmToken.create(userId, "device-1", "token-1"));

            // when
            FcmTokenExistsResponse response = fcmTokenQueryService.checkFcmTokenExist(userId, "device-1");

            // then
            assertThat(response.registered()).isTrue();
        }

        @Test
        void 등록된_토큰이_없으면_false를_반환한다() {
            // when
            FcmTokenExistsResponse response = fcmTokenQueryService.checkFcmTokenExist(userId, "device-1");

            // then
            assertThat(response.registered()).isFalse();
        }

        @Test
        void 같은_기기라도_다른_유저의_토큰이면_false를_반환한다() {
            // given
            fcmTokenRepository.save(FcmToken.create(otherUserId, "device-1", "token-1"));

            // when
            FcmTokenExistsResponse response = fcmTokenQueryService.checkFcmTokenExist(userId, "device-1");

            // then
            assertThat(response.registered()).isFalse();
        }
    }
}
