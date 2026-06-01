package gravit.code.notification.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static gravit.code.notification.domain.NotificationActionType.CONGRATULATE;
import static gravit.code.notification.domain.NotificationActionType.FOLLOW_BACK;
import static gravit.code.notification.domain.NotificationActionType.GO_TO_LEARNING;
import static gravit.code.notification.domain.NotificationActionType.GO_TO_NOTICE;
import static gravit.code.notification.domain.NotificationActionType.NONE;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

    STREAK_WARNING(GO_TO_LEARNING),    // 3.1  연속학습 끊길 위기
    DAILY_INCOMPLETE(GO_TO_LEARNING),  // 3.2  오늘 학습 미완료
    INACTIVITY(GO_TO_LEARNING),        // 3.3  장기 미접속
    SEASON_ENDING(GO_TO_LEARNING),     // 3.7  시즌 종료 임박
    SEASON_RESET(NONE),                // 3.8  시즌 종료 + 새 시즌 시작
    FOLLOW(FOLLOW_BACK),               // 3.9  팔로우
    CONGRATULATION(NONE),              // 3.10 축하하기 받음
    FRIEND_ACTIVITY(CONGRATULATE),     // 3.11 친구 활동
    NOTICE(GO_TO_NOTICE),              // 3.12 공지사항
    VERSION(GO_TO_NOTICE),             // 3.13 버전 관리
    NEW_CONTENT(GO_TO_LEARNING);       // 3.14 새 콘텐츠 업데이트

    private final NotificationActionType actionType;
}
