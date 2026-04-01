-- Remove badge-related tables and constraints

ALTER TABLE user_badge DROP CONSTRAINT IF EXISTS FKjqx9n26pk9mqf1qo8f7xvvoq9;
ALTER TABLE badge DROP CONSTRAINT IF EXISTS FKclxvp96k01rlpfhwvpnhxcyu6;

DROP TABLE IF EXISTS user_badge;
DROP TABLE IF EXISTS user_mission_stat;
DROP TABLE IF EXISTS user_planet_completion;
DROP TABLE IF EXISTS user_qualified_solve_stat;
DROP TABLE IF EXISTS badge;
DROP TABLE IF EXISTS badge_category;
