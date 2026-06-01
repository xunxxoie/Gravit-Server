package gravit.code.fcm.service;

import gravit.code.fcm.domain.FcmToken;
import gravit.code.fcm.dto.response.FcmTokenExistsResponse;
import gravit.code.fcm.repository.FcmTokenRepository;
import gravit.code.support.TCSpringBootTest;
import gravit.code.user.domain.Role;
import gravit.code.user.domain.User;
import gravit.code.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@TCSpringBootTest
@Transactional
class FcmTokenQueryServiceIntegrationTest {

    @Autowired
    private FcmTokenQueryService fcmTokenQueryService;

    @Autowired
    private FcmTokenRepository fcmTokenRepository;

    @Autowired
    private UserRepository userRepository;

    private long userId;
    private long otherUserId;

    @BeforeEach
    void setUp() {
        User user = userRepository.save(User.create("a@test.com", "provider_a", "유저A", "handle_a", 1, Role.USER));
        User other = userRepository.save(User.create("b@test.com", "provider_b", "유저B", "handle_b", 1, Role.USER));
        userId = user.getId();
        otherUserId = other.getId();
    }

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
