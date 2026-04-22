-- V4__add_learning_content_staging_tables.sql

-- 1) lesson_staging
CREATE TABLE IF NOT EXISTS lesson_staging (
    id      bigint       NOT NULL,
    unit_id bigint       NOT NULL,
    title   varchar(255) NOT NULL,
    label   varchar(32)  NOT NULL,
    CONSTRAINT lesson_staging_pkey PRIMARY KEY (id)
);

-- 2) problem_staging
CREATE TABLE IF NOT EXISTS problem_staging (
    id           bigint       NOT NULL,
    lesson_id    bigint       NOT NULL,
    content      text         NOT NULL,
    instruction  varchar(255) NOT NULL,
    problem_type varchar(255) NOT NULL,
    label        varchar(32)  NOT NULL,
    CONSTRAINT problem_staging_pkey PRIMARY KEY (id),
    CONSTRAINT problem_staging_problem_type_check
        CHECK (problem_type IN ('SUBJECTIVE', 'OBJECTIVE'))
);

CREATE INDEX IF NOT EXISTS ix_problem_staging_lesson
    ON problem_staging(lesson_id);

-- 3) option_staging
CREATE TABLE IF NOT EXISTS option_staging (
    id          bigint       NOT NULL,
    problem_id  bigint       NOT NULL,
    content     varchar(255) NOT NULL,
    explanation varchar(255) NOT NULL,
    is_answer   boolean      NOT NULL,
    label       varchar(32)  NOT NULL,
    CONSTRAINT option_staging_pkey PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS ix_option_staging_problem
    ON option_staging(problem_id);

-- 4) answer_staging
CREATE TABLE IF NOT EXISTS answer_staging (
    id          bigint       NOT NULL,
    problem_id  bigint,
    content     varchar(255) NOT NULL,
    explanation text         NOT NULL,
    label       varchar(32)  NOT NULL,
    CONSTRAINT answer_staging_pkey PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS ix_answer_staging_problem
    ON answer_staging(problem_id);
