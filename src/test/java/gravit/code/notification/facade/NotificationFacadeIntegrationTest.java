package gravit.code.notification.facade;

import gravit.code.fcm.domain.FcmToken;
import gravit.code.fcm.dto.internal.PushMessage;
import gravit.code.fcm.repository.FcmTokenRepository;
import gravit.code.fcm.service.FcmService;
import gravit.code.notification.domain.Notification;
import gravit.code.notification.domain.NotificationActionType;
import gravit.code.notification.domain.NotificationType;
import gravit.code.notification.repository.NotificationRepository;
import gravit.code.notification.support.NotificationMessageProvider;
import gravit.code.season.batch.SeasonBatchService;
import gravit.code.season.fixture.SeasonFixture;
import gravit.code.support.TCSpringBootTest;
import gravit.code.user.domain.User;
import gravit.code.user.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@TCSpringBootTest
class NotificationFacadeIntegrationTest {

    @Autowired
    private NotificationFacade notificationFacade;

    @Autowired
    private SeasonBatchService seasonBatchService;

    @Autowired
    private NotificationMessageProvider messageProvider;

    @Autowired
    private Clock clock; // FixedClockConfig: 2025-08-05T12:00+09:00 고정

    @Autowired
    private SeasonFixture seasonFixture;

    @Autowired
    private UserFixture userFixture;

    @Autowired
    private FcmTokenRepository fcmTokenRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    // FCM 외부 발송 경계만 격리하고, 토큰 조회·메시지 구성 등 우리 로직은 실제로 동작시킨다
    @MockitoBean
    private FcmService fcmService;

    // 전체 발송 대상이 될 FCM 토큰을 사전에 적재한다
    private List<String> 토큰_2개_준비() {
        User user1 = userFixture.일반_유저(1);
        User user2 = userFixture.일반_유저(2);
        fcmTokenRepository.save(FcmToken.create(user1.getId(), "device-1", "token-1"));
        fcmTokenRepository.save(FcmToken.create(user2.getId(), "device-2", "token-2"));
        return List.of("token-1", "token-2");
    }

    private String 종료_임박_문구(int daysBefore) {
        return messageProvider.seasonEndingMilestones().stream()
                .filter(milestone -> milestone.daysBefore() == daysBefore)
                .findFirst()
                .orElseThrow()
                .message();
    }

    @SuppressWarnings("unchecked")
    private List<PushMessage> 발송된_메시지_캡처(int timeoutMillis) {
        ArgumentCaptor<List<PushMessage>> captor = ArgumentCaptor.forClass(List.class);
        verify(fcmService, timeout(timeoutMillis)).sendNotifications(captor.capture());
        return captor.getValue();
    }

    @Nested
    @DisplayName("시즌 종료 임박 알림을 발송할 때")
    class SendSeasonEndingReminders {

        @Test
        void 종료_7일_전이면_일주일_문구를_전체_발송한다() {
            // given
            LocalDate today = LocalDate.now(clock);
            seasonFixture.진행중인_시즌("2025-S2",
                    today.minusMonths(4).atStartOfDay(),
                    today.plusDays(7).atStartOfDay());
            List<String> tokens = 토큰_2개_준비();

            // when
            notificationFacade.sendSeasonEndingReminders();

            // then - FCM 발송
            List<PushMessage> sent = 발송된_메시지_캡처(1000);
            assertSoftly(softly -> {
                softly.assertThat(sent).hasSize(1);
                softly.assertThat(sent.get(0).tokens()).containsExactlyInAnyOrderElementsOf(tokens);
                softly.assertThat(sent.get(0).title()).isEqualTo(종료_임박_문구(7));
                softly.assertThat(sent.get(0).data())
                        .containsEntry("type", NotificationType.SEASON_ENDING.name())
                        .containsEntry("actionType", NotificationActionType.GO_TO_LEARNING.name());
            });
            // DB 저장 - 토큰을 가진 2명 모두 알림함에 적재
            List<Notification> saved = notificationRepository.findAll();
            assertSoftly(softly -> {
                softly.assertThat(saved).hasSize(2);
                softly.assertThat(saved).allMatch(n -> n.getType() == NotificationType.SEASON_ENDING);
                softly.assertThat(saved).allMatch(n -> n.getMessage().equals(종료_임박_문구(7)));
            });
        }

        @Test
        void 종료_3일_전이면_3일_문구를_전체_발송한다() {
            // given
            LocalDate today = LocalDate.now(clock);
            seasonFixture.진행중인_시즌("2025-S2",
                    today.minusMonths(4).atStartOfDay(),
                    today.plusDays(3).atStartOfDay());
            토큰_2개_준비();

            // when
            notificationFacade.sendSeasonEndingReminders();

            // then - FCM 발송
            List<PushMessage> sent = 발송된_메시지_캡처(1000);
            assertSoftly(softly -> {
                softly.assertThat(sent.get(0).title()).isEqualTo(종료_임박_문구(3));
                softly.assertThat(sent.get(0).data())
                        .containsEntry("type", NotificationType.SEASON_ENDING.name());
            });
            // DB 저장
            assertThat(notificationRepository.findAll()).hasSize(2);
        }

        @Test
        void 마일스톤_일수가_아니면_발송하지_않는다() {
            // given (5일 전 - 7/3 어디에도 해당하지 않음)
            LocalDate today = LocalDate.now(clock);
            seasonFixture.진행중인_시즌("2025-S2",
                    today.minusMonths(4).atStartOfDay(),
                    today.plusDays(5).atStartOfDay());
            토큰_2개_준비();

            // when
            notificationFacade.sendSeasonEndingReminders();

            // then
            verify(fcmService, never()).sendNotifications(anyList());
            assertThat(notificationRepository.findAll()).isEmpty();
        }

        @Test
        void ACTIVE_시즌이_없으면_발송하지_않는다() {
            // given - 시즌 미생성
            토큰_2개_준비();

            // when
            notificationFacade.sendSeasonEndingReminders();

            // then
            verify(fcmService, never()).sendNotifications(anyList());
            assertThat(notificationRepository.findAll()).isEmpty();
        }
    }

    @Nested
    @DisplayName("시즌 롤오버가 실행되면")
    class WhenSeasonRolledOver {

        @Test
        void 새_시즌_시작_알림을_전체_발송한다() {
            // given - 종료 시각이 고정된 현재 시각(2025-08-05) 이전인 ACTIVE 시즌 → 롤오버 대상
            seasonFixture.진행중인_시즌("2025-S1");
            토큰_2개_준비();

            // when - 현재 시각 기준으로 롤오버 실행 (커밋 후 AFTER_COMMIT 리스너가 발송)
            seasonBatchService.finalizeAndRollover();

            // then - FCM 발송
            List<PushMessage> sent = 발송된_메시지_캡처(3000);
            assertSoftly(softly -> {
                softly.assertThat(sent).hasSize(1);
                softly.assertThat(sent.get(0).title()).isEqualTo(messageProvider.seasonReset());
                softly.assertThat(sent.get(0).data())
                        .containsEntry("type", NotificationType.SEASON_RESET.name())
                        .containsEntry("actionType", NotificationActionType.NONE.name());
            });
            // DB 저장 - 2명 모두 알림함에 적재
            List<Notification> saved = notificationRepository.findAll();
            assertSoftly(softly -> {
                softly.assertThat(saved).hasSize(2);
                softly.assertThat(saved).allMatch(n -> n.getType() == NotificationType.SEASON_RESET);
                softly.assertThat(saved).allMatch(n -> n.getMessage().equals(messageProvider.seasonReset()));
            });
        }

        @Test
        void 등록된_토큰이_없으면_발송하지_않는다() {
            // given - 토큰 미적재
            seasonFixture.진행중인_시즌("2025-S1");

            // when
            seasonBatchService.finalizeAndRollover();

            // then
            verify(fcmService, never()).sendNotifications(anyList());
        }
    }

    @Nested
    @DisplayName("단일 유저에게 알림을 발송할 때")
    class NotifyUser {

        @Test
        void 인앱_알림이_저장되고_FCM이_발송된다() {
            // given
            User user = userFixture.일반_유저(1);
            fcmTokenRepository.save(FcmToken.create(user.getId(), "device-1", "token-1"));

            // when
            notificationFacade.notifyUser(user.getId(), NotificationType.FOLLOW, "유저2님이 나를 팔로우했어요! 👀");

            // then - DB 저장
            List<Notification> saved = notificationRepository.findAll();
            assertSoftly(softly -> {
                softly.assertThat(saved).hasSize(1);
                softly.assertThat(saved.get(0).getUserId()).isEqualTo(user.getId());
                softly.assertThat(saved.get(0).getType()).isEqualTo(NotificationType.FOLLOW);
                softly.assertThat(saved.get(0).getMessage()).isEqualTo("유저2님이 나를 팔로우했어요! 👀");
                softly.assertThat(saved.get(0).isRead()).isFalse();
            });
            // FCM 발송
            verify(fcmService, timeout(1000)).sendNotifications(anyList());
        }

        @Test
        void FCM_토큰이_없어도_인앱_알림은_저장된다() {
            // given - 토큰 미등록
            User user = userFixture.일반_유저(1);

            // when
            notificationFacade.notifyUser(user.getId(), NotificationType.CONGRATULATION, "유저2님이 축하해줬어요! 🎉");

            // then - DB 저장
            assertThat(notificationRepository.findAll()).hasSize(1);
            // FCM 미발송
            verify(fcmService, never()).sendNotifications(anyList());
        }
    }

    @Nested
    @DisplayName("여러 유저에게 알림을 발송할 때")
    class NotifyUsers {

        @Test
        void 인앱_알림이_저장되고_FCM이_발송된다() {
            // given
            User user1 = userFixture.일반_유저(1);
            User user2 = userFixture.일반_유저(2);
            fcmTokenRepository.save(FcmToken.create(user1.getId(), "device-1", "token-1"));
            fcmTokenRepository.save(FcmToken.create(user2.getId(), "device-2", "token-2"));

            // when
            notificationFacade.notifyUsers(
                    List.of(user1.getId(), user2.getId()),
                    NotificationType.FRIEND_ACTIVITY,
                    "유저3님이 LV.5이 됐어요! 💪"
            );

            // then - DB 저장
            List<Notification> saved = notificationRepository.findAll();
            assertSoftly(softly -> {
                softly.assertThat(saved).hasSize(2);
                softly.assertThat(saved).extracting(Notification::getUserId)
                        .containsExactlyInAnyOrder(user1.getId(), user2.getId());
                softly.assertThat(saved).allMatch(n -> n.getType() == NotificationType.FRIEND_ACTIVITY);
                softly.assertThat(saved).allMatch(n -> n.getMessage().equals("유저3님이 LV.5이 됐어요! 💪"));
            });
            // FCM 발송
            verify(fcmService, timeout(1000)).sendNotifications(anyList());
        }
    }
}
