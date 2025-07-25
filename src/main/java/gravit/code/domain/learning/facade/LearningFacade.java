package gravit.code.domain.learning.facade;

import gravit.code.domain.chapterProgress.dto.response.ChapterInfoResponse;
import gravit.code.domain.chapterProgress.service.ChapterProgressService;
import gravit.code.domain.learning.dto.request.LearningResultSaveRequest;
import gravit.code.domain.learning.service.LearningService;
import gravit.code.domain.lesson.dto.response.LessonResponse;
import gravit.code.domain.lessonProgress.dto.response.LessonInfo;
import gravit.code.domain.lessonProgress.service.LessonProgressService;
import gravit.code.domain.problem.service.ProblemService;
import gravit.code.domain.problemProgress.service.ProblemProgressService;
import gravit.code.domain.unitProgress.dto.response.UnitInfo;
import gravit.code.domain.unitProgress.dto.response.UnitPageResponse;
import gravit.code.domain.unitProgress.service.UnitProgressService;
import gravit.code.domain.user.dto.response.UserLevelResponse;
import gravit.code.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LearningFacade {

    private final UserService userService;
    private final LearningService learningService;
    private final ProblemService problemService;
    private final ChapterProgressService chapterProgressService;
    private final UnitProgressService unitProgressService;
    private final LessonProgressService lessonProgressService;
    private final ProblemProgressService problemProgressService;

    @Transactional(readOnly = true)
    public List<LessonResponse> getAllProblemsInLesson(Long lessonId){
        return problemService.getAllProblemsInLesson(lessonId);
    }

    @Transactional
    public UserLevelResponse saveLearningResult(Long userId, LearningResultSaveRequest request){

        // 챕터, 유닛, 레슨 중간테이블 초기화
        learningService.initLearningProgress(userId, request.chapterId(), request.unitId(), request.lessonId());

        problemProgressService.saveProblemResults(userId, request.problemResults());

        lessonProgressService.updateLessonProgressStatus(userId, request.lessonId());

        if(Boolean.TRUE.equals(unitProgressService.updateUnitProgress(userId, request.unitId())))
            chapterProgressService.updateChapterProgress(userId, request.chapterId());

        return userService.updateUserLevel(userId);
    }

    @Transactional(readOnly = true)
    public List<ChapterInfoResponse> getAllChapters(Long userId){
        return chapterProgressService.findChaptersWithProgressByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<UnitPageResponse> getAllUnitsInChapter(Long userId, Long chapterId){
        List<UnitInfo> unitInfos = unitProgressService.getUnitInfosByChapterId(userId, chapterId);

        return unitInfos.stream()
                .map(unitInfo -> {
                    List<LessonInfo> lessonInfos = lessonProgressService.getLessonInfosByUnitId(userId, unitInfo.unitId());

                    return UnitPageResponse.create(unitInfo, lessonInfos);
                })
                .toList();
    }
}
