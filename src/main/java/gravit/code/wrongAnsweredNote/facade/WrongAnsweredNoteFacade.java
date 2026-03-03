package gravit.code.wrongAnsweredNote.facade;

import gravit.code.answer.dto.response.AnswerResponse;
import gravit.code.answer.service.AnswerQueryService;
import gravit.code.global.annotation.Facade;
import gravit.code.global.exception.domain.CustomErrorCode;
import gravit.code.global.exception.domain.RestApiException;
import gravit.code.option.dto.response.OptionResponse;
import gravit.code.option.service.OptionQueryService;
import gravit.code.problem.domain.ProblemType;
import gravit.code.problem.dto.response.ProblemDetail;
import gravit.code.problem.dto.response.ProblemResponse;
import gravit.code.problem.dto.response.WrongAnsweredProblemsResponse;
import gravit.code.unit.dto.response.UnitSummary;
import gravit.code.unit.service.UnitQueryService;
import gravit.code.wrongAnsweredNote.service.WrongAnsweredNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Facade
@RequiredArgsConstructor
public class WrongAnsweredNoteFacade {

    private final WrongAnsweredNoteService wrongAnsweredNoteService;

    private final UnitQueryService unitQueryService;
    private final AnswerQueryService answerQueryService;
    private final OptionQueryService optionQueryService;

    @Transactional(readOnly = true)
    public WrongAnsweredProblemsResponse getAllWrongAnsweredProblemInUnit(
            long userId,
            long unitId
    ) {
        UnitSummary unitSummary = unitQueryService.getUnitSummaryByUnitId(unitId);

        List<ProblemDetail> problemDetails = wrongAnsweredNoteService.getAllWrongAnsweredProblemInUnit(userId, unitId);

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

        return WrongAnsweredProblemsResponse.of(
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
