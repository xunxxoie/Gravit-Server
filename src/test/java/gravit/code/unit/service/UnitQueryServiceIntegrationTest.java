package gravit.code.unit.service;

import gravit.code.chapter.domain.Chapter;
import gravit.code.chapter.repository.ChapterRepository;
import gravit.code.global.exception.domain.RestApiException;
import gravit.code.lesson.domain.Lesson;
import gravit.code.lesson.repository.LessonRepository;
import gravit.code.support.TCSpringBootTest;
import gravit.code.unit.domain.Unit;
import gravit.code.unit.dto.response.UnitSummary;
import gravit.code.unit.repository.UnitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@TCSpringBootTest
@Sql(scripts = "classpath:sql/truncate_all.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UnitQueryServiceIntegrationTest {

    @Autowired
    private UnitQueryService unitQueryService;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Nested
    @DisplayName("챕터별 유닛 목록을 조회할 때")
    class GetAllUnitSummaryByChapterId {

        @Test
        void 성공한다() {
            // given
            Chapter chapter = chapterRepository.save(Chapter.create("운영체제", "운영체제 기초 개념"));
            unitRepository.save(Unit.create("프로세스", "프로세스 개념", chapter.getId()));
            unitRepository.save(Unit.create("스레드", "스레드 개념", chapter.getId()));

            // when
            List<UnitSummary> result = unitQueryService.getAllUnitSummaryByChapterId(chapter.getId());

            // then
            assertSoftly(softly -> {
                softly.assertThat(result).hasSize(2);
                softly.assertThat(result.get(0).title()).isEqualTo("프로세스");
                softly.assertThat(result.get(1).title()).isEqualTo("스레드");
            });
        }

        @Test
        void 유닛이_없으면_빈_리스트를_반환한다() {
            // given
            Chapter chapter = chapterRepository.save(Chapter.create("운영체제", "운영체제 기초 개념"));

            // when
            List<UnitSummary> result = unitQueryService.getAllUnitSummaryByChapterId(chapter.getId());

            // then
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("유닛을 단건 조회할 때")
    class GetUnitSummaryByUnitId {

        @Test
        void 성공한다() {
            // given
            Chapter chapter = chapterRepository.save(Chapter.create("운영체제", "운영체제 기초 개념"));
            Unit unit = unitRepository.save(Unit.create("프로세스", "프로세스 개념", chapter.getId()));

            // when
            UnitSummary result = unitQueryService.getUnitSummaryByUnitId(unit.getId());

            // then
            assertSoftly(softly -> {
                softly.assertThat(result.unitId()).isEqualTo(unit.getId());
                softly.assertThat(result.title()).isEqualTo("프로세스");
            });
        }

        @Test
        void 존재하지_않으면_예외를_던진다() {
            // when & then
            assertThatThrownBy(() -> unitQueryService.getUnitSummaryByUnitId(999L))
                    .isInstanceOf(RestApiException.class);
        }
    }

    @Nested
    @DisplayName("레슨 ID로 유닛을 조회할 때")
    class GetUnitSummaryByLessonId {

        @Test
        void 성공한다() {
            // given
            Chapter chapter = chapterRepository.save(Chapter.create("운영체제", "운영체제 기초 개념"));
            Unit unit = unitRepository.save(Unit.create("프로세스", "프로세스 개념", chapter.getId()));
            Lesson lesson = lessonRepository.save(Lesson.create("프로세스와 스레드", unit.getId()));

            // when
            UnitSummary result = unitQueryService.getUnitSummaryByLessonId(lesson.getId());

            // then
            assertSoftly(softly -> {
                softly.assertThat(result.unitId()).isEqualTo(unit.getId());
                softly.assertThat(result.title()).isEqualTo("프로세스");
            });
        }

        @Test
        void 존재하지_않으면_예외를_던진다() {
            // when & then
            assertThatThrownBy(() -> unitQueryService.getUnitSummaryByLessonId(999L))
                    .isInstanceOf(RestApiException.class);
        }
    }
}
