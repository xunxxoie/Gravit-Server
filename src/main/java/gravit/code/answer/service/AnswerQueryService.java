package gravit.code.answer.service;

import gravit.code.answer.domain.Answer;
import gravit.code.answer.dto.response.AnswerResponse;
import gravit.code.answer.repository.AnswerRepository;
import gravit.code.problem.domain.ProblemType;
import gravit.code.problem.dto.response.ProblemDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerQueryService {

    private final AnswerRepository answerRepository;

    public Map<Long, AnswerResponse> getAnswersInProblem(List<ProblemDetail> problemDetails) {
        List<ProblemDetail> subjectiveProblems = problemDetails.stream()
                .filter(p -> p.problemType() == ProblemType.SUBJECTIVE)
                .toList();

        Map<Long, AnswerResponse> answerMap;
        if(!subjectiveProblems.isEmpty()){
            List<Long> subjectiveProblemIds = subjectiveProblems.stream()
                    .map(ProblemDetail::id)
                    .toList();

            List<Answer> answers = answerRepository.findByProblemIdIn(subjectiveProblemIds);

            answerMap = answers.stream()
                    .collect(Collectors.toMap(Answer::getProblemId, AnswerResponse::from));
        } else {
            answerMap = new HashMap<>();
        }
        return answerMap;
    }
}
