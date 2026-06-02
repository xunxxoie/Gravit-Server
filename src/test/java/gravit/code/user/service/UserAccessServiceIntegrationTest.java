package gravit.code.user.service;

import gravit.code.support.TCSpringBootTest;
import gravit.code.user.domain.Role;
import gravit.code.user.domain.User;
import gravit.code.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// 고정 시계 기준 now = 2025-08-05T12:00 (Asia/Seoul)
@TCSpringBootTest
class UserAccessServiceIntegrationTest {

    private static final LocalDateTime NOW = LocalDateTime.of(2025, 8, 5, 12, 0);

    @Autowired
    private UserAccessService userAccessService;

    @Autowired
    private UserRepository userRepository;

    @Nested
    @DisplayName("접속 시각을 갱신할 때")
    class UpdateLastAccessed {

        @Test
        void 오늘자_기록이_없으면_현재_시각으로_갱신한다() {
            // given
            User user = createUser(1, LocalDateTime.of(2025, 8, 4, 9, 0)); // 어제 접속

            // when
            userAccessService.updateLastAccessed(user.getId());

            // then
            assertThat(findById(user.getId()).getLastAccessedAt()).isEqualTo(NOW);
        }

        @Test
        void lastAccessedAt이_null이면_현재_시각으로_갱신한다() {
            // given
            User user = createUser(1, null);

            // when
            userAccessService.updateLastAccessed(user.getId());

            // then
            assertThat(findById(user.getId()).getLastAccessedAt()).isEqualTo(NOW);
        }

        @Test
        void 오늘_이미_기록이_있으면_갱신하지_않는다() {
            // given
            LocalDateTime todayMorning = LocalDateTime.of(2025, 8, 5, 9, 0);
            User user = createUser(1, todayMorning);

            // when
            userAccessService.updateLastAccessed(user.getId());

            // then
            assertThat(findById(user.getId()).getLastAccessedAt()).isEqualTo(todayMorning);
        }
    }

    @Nested
    @DisplayName("정확히 N일 미접속 유저를 조회할 때")
    class GetUserIdsInactiveForExactly {

        @Test
        void 마지막_접속이_정확히_N일_전인_유저만_반환한다() {
            // given (today 2025-08-05 기준 7일 전 = 2025-07-29)
            long target = createUser(1, LocalDateTime.of(2025, 7, 29, 10, 0)).getId();
            createUser(2, LocalDateTime.of(2025, 7, 30, 10, 0)); // 6일 전 → 제외
            createUser(3, LocalDateTime.of(2025, 7, 28, 10, 0)); // 8일 전 → 제외

            // when
            List<Long> result = userAccessService.getUserIdsInactiveForExactly(7);

            // then
            assertThat(result).containsExactly(target);
        }

        @Test
        void 해당_날짜의_경계_시각을_포함한다() {
            // given (2025-07-29 하루 구간: 00:00 포함 ~ 07-30 00:00 미포함)
            long start = createUser(1, LocalDateTime.of(2025, 7, 29, 0, 0)).getId();
            long end = createUser(2, LocalDateTime.of(2025, 7, 29, 23, 59, 59)).getId();
            createUser(3, LocalDateTime.of(2025, 7, 30, 0, 0)); // 경계 밖 → 제외

            // when
            List<Long> result = userAccessService.getUserIdsInactiveForExactly(7);

            // then
            assertThat(result).containsExactlyInAnyOrder(start, end);
        }
    }

    private User createUser(
            int index,
            LocalDateTime lastAccessedAt
    ) {
        User user = userRepository.save(
                User.create("e" + index + "@test.com", "p" + index, "유저" + index, "h" + index, 1, Role.USER));
        if (lastAccessedAt != null) {
            ReflectionTestUtils.setField(user, "lastAccessedAt", lastAccessedAt);
            userRepository.save(user);
        }
        return user;
    }

    private User findById(long userId) {
        return userRepository.findById(userId).orElseThrow();
    }
}
