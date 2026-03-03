-- V3__add_problem_index_and_fix_mission_type.sql

-- 1) Problem 조회 성능 향상용 인덱스 추가
CREATE INDEX IF NOT EXISTS ix_problem_lesson
    ON problem(lesson_id);

-- 2) mission_type 체크 제약 조건 수정
-- 기존 체크 제약 조건 삭제
ALTER TABLE mission DROP CONSTRAINT IF EXISTS mission_mission_type_check;

-- 새로운 체크 제약 조건 추가 (LEARNING_MINUTES_TWENTY -> LEARNING_MINUTES_FIVE로 변경)
ALTER TABLE mission
    ADD CONSTRAINT mission_mission_type_check
        CHECK (mission_type IN (
            'COMPLETE_LESSON_ONE', 'COMPLETE_LESSONS_TWO',
            'COMPLETE_LESSONS_THREE', 'PERFECT_LESSON_ONE',
            'PERFECT_LESSONS_TWO', 'PERFECT_LESSONS_THREE',
            'LEARNING_MINUTES_FIVE', 'LEARNING_MINUTES_TEN',
            'LEARNING_MINUTES_FIFTEEN', 'FOLLOW_NEW_FRIEND'
        ));

-- 3) Answer, Option에 problemId 인덱스 추가
CREATE INDEX IF NOT EXISTS ix_answer_problem
    ON answer(problem_id);

CREATE INDEX IF NOT EXISTS ix_option_problem
    ON option(problem_id);