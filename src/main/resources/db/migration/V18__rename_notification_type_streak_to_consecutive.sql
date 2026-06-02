--V18__rename_notification_type_streak_to_consecutive.sql

UPDATE notification SET type = 'CONSECUTIVE_LEARNING_WARNING' WHERE type = 'STREAK_WARNING';

ALTER TABLE notification DROP CONSTRAINT ck_notification_type;

ALTER TABLE notification ADD CONSTRAINT ck_notification_type CHECK (
    type IN (
        'CONSECUTIVE_LEARNING_WARNING', 'DAILY_INCOMPLETE', 'INACTIVITY', 'SEASON_ENDING',
        'SEASON_RESET', 'FOLLOW', 'CONGRATULATION', 'FRIEND_ACTIVITY',
        'NOTICE', 'VERSION', 'NEW_CONTENT'
    )
);
