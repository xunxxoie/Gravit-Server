package gravit.code.problem.facade;

import gravit.code.answer.dto.response.AnswerResponse;
import gravit.code.answer.service.AnswerQueryService;
import gravit.code.global.annotation.Facade;
import gravit.code.global.exception.domain.CustomErrorCode;
import gravit.code.global.exception.domain.RestApiException;
import gravit.code.lesson.dto.response.LessonResponse;
import gravit.code.option.dto.response.OptionResponse;
import gravit.code.option.service.OptionQueryService;
import gravit.code.problem.domain.ProblemType;
import gravit.code.problem.dto.response.ProblemDetail;
import gravit.code.problem.dto.response.ProblemResponse;
import gravit.code.problem.service.ProblemQueryService;
import gravit.code.unit.dto.response.UnitSummary;
import gravit.code.unit.service.UnitQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Log4j2
@Facade
@RequiredArgsConstructor
public class ProblemFacade {

    private final ProblemQueryService problemQueryService;

    private final UnitQueryService unitQueryService;
    private final AnswerQueryService answerQueryService;
    private final OptionQueryService optionQueryService;

    @Transactional(readOnly = true)
    public LessonResponse getAllProblemInLesson(
            long userId,
            long lessonId
    ){
        long startTime = System.currentTimeMillis();

        UnitSummary unitSummary = unitQueryService.getUnitSummaryByLessonId(lessonId);

        // Problem 조회
        List<ProblemDetail> problemDetails = problemQueryService.getAllProblemInLesson(userId, lessonId);

        // Answer(주관식 답안), Option(객관식 선지들) 조회
        Map<Long, AnswerResponse> answerMap = answerQueryService.getAnswersInProblem(problemDetails);
        Map<Long, List<OptionResponse>> optionMap = optionQueryService.getOptionsInProblem(problemDetails);

        List<ProblemResponse> problemResponses = problemDetails.stream()
                .map(problemDetail -> {
                    if(problemDetail.problemType() == ProblemType.SUBJECTIVE){
                        return getSubjectiveProblemResponse(problemDetail, answerMap);
                    }else{
                        return getObjectiveProblemResponse(problemDetail, optionMap);
                    }
                })
                .toList();

        long endTime = System.currentTimeMillis();

        log.info("getAnswerOrOptionInProblems took " + (endTime - startTime) + " ms");

        return LessonResponse.of(
                unitSummary,
                problemResponses
        );
    }

    private ProblemResponse getSubjectiveProblemResponse(
            ProblemDetail problemDetail,
            Map<Long, AnswerResponse> answerMap
    ) {
        AnswerResponse answerResponse = answerMap.get(problemDetail.id());

        if(answerResponse == null){
            throw new RestApiException(CustomErrorCode.ANSWER_NOT_FOUND);
        }

        return ProblemResponse.createSubjectiveProblem(problemDetail, answerResponse);
    }

    private ProblemResponse getObjectiveProblemResponse(
            ProblemDetail problemDetail,
            Map<Long, List<OptionResponse>> optionMap
    ) {
        List<OptionResponse> optionResponses = optionMap.get(problemDetail.id());

        if(optionResponses == null || optionResponses.isEmpty()){
            throw new RestApiException(CustomErrorCode.OPTION_NOT_FOUND);
        }

        return ProblemResponse.createObjectiveProblem(problemDetail, optionResponses);
    }
}
