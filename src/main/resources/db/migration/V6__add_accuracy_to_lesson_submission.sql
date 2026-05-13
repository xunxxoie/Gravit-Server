-- V6__add_accuracy_to_lesson_submission.sql
-- lesson_submission 테이블에 정확도(accuracy) 컬럼 추가
-- 0~100 범위의 정수, 매 제출마다 갱신
ALTER TABLE lesson_submission
    ADD COLUMN accuracy INTEGER NOT NULL;
