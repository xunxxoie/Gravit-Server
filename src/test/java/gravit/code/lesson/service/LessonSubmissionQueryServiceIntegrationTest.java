package gravit.code.lesson.service;

import gravit.code.chapter.domain.Chapter;
import gravit.code.chapter.repository.ChapterRepository;
import gravit.code.lesson.domain.Lesson;
import gravit.code.lesson.domain.LessonSubmission;
import gravit.code.lesson.repository.LessonRepository;
import gravit.code.lesson.repository.LessonSubmissionRepository;
import gravit.code.support.TCSpringBootTest;
import gravit.code.unit.domain.Unit;
import gravit.code.unit.repository.UnitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@TCSpringBootTest
@Sql(scripts = "classpath:sql/truncate_all.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class LessonSubmissionQueryServiceIntegrationTest {

    @Autowired
    private LessonSubmissionQueryService lessonSubmissionQueryService;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private LessonSubmissionRepository lessonSubmissionRepository;

    @Nested
    @DisplayName("레슨 제출 횟수를 조회할 때")
    class GetLessonSubmissionCount {

        @Test
        void 제출_기록이_있으면_횟수를_반환한다() {
            // given
            long userId = 1L;
            Chapter chapter = chapterRepository.save(Chapter.create("운영체제", "운영체제 기초 개념"));
            Unit unit = unitRepository.save(Unit.create("프로세스", "프로세스 개념", chapter.getId()));
            Lesson lesson = lessonRepository.save(Lesson.create("레슨1", unit.getId()));
            lessonSubmissionRepository.save(LessonSubmission.create(120, lesson.getId(), userId));

            // when
            int result = lessonSubmissionQueryService.getLessonSubmissionCount(userId, lesson.getId());

            // then
            assertThat(result).isEqualTo(1);
        }

        @Test
        void 제출_기록이_없으면_0을_반환한다() {
            // given
            long userId = 1L;
            Chapter chapter = chapterRepository.save(Chapter.create("운영체제", "운영체제 기초 개념"));
            Unit unit = unitRepository.save(Unit.create("프로세스", "프로세스 개념", chapter.getId()));
            Lesson lesson = lessonRepository.save(Lesson.create("레슨1", unit.getId()));

            // when
            int result = lessonSubmissionQueryService.getLessonSubmissionCount(userId, lesson.getId());

            // then
            assertThat(result).isZero();
        }
    }

    @Nested
    @DisplayName("첫 번째 레슨 제출인지 확인할 때")
    class CheckFirstLessonSubmission {

        @Test
        void 첫_제출이면_true를_반환한다() {
            // given
            long userId = 1L;
            Chapter chapter = chapterRepository.save(Chapter.create("운영체제", "운영체제 기초 개념"));
            Unit unit = unitRepository.save(Unit.create("프로세스", "프로세스 개념", chapter.getId()));
            Lesson lesson = lessonRepository.save(Lesson.create("레슨1", unit.getId()));

            // when
            boolean result = lessonSubmissionQueryService.checkFirstLessonSubmission(userId, lesson.getId());

            // then
            assertThat(result).isTrue();
        }

        @Test
        void 이미_제출한_적이_있으면_false를_반환한다() {
            // given
            long userId = 1L;
            Chapter chapter = chapterRepository.save(Chapter.create("운영체제", "운영체제 기초 개념"));
            Unit unit = unitRepository.save(Unit.create("프로세스", "프로세스 개념", chapter.getId()));
            Lesson lesson = lessonRepository.save(Lesson.create("레슨1", unit.getId()));
            lessonSubmissionRepository.save(LessonSubmission.create(120, lesson.getId(), userId));

            // when
            boolean result = lessonSubmissionQueryService.checkFirstLessonSubmission(userId, lesson.getId());

            // then
            assertThat(result).isFalse();
        }
    }
}
