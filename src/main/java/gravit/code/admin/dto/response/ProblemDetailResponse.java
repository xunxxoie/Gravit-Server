package gravit.code.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import gravit.code.answer.domain.Answer;
import gravit.code.option.domain.Option;
import gravit.code.problem.domain.Problem;
import gravit.code.problem.domain.ProblemType;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record ProblemDetailResponse(

        long problemId,

        long lessonId,

        ProblemType problemType,

        String instruction,

        String content,

        List<ProblemOptionResponse> options,

        ProblemAnswerResponse answer
) {
    public static ProblemDetailResponse objective(
            Problem problem,
            List<Option> options
    ) {
        return ProblemDetailResponse.builder()
                .problemId(problem.getId())
                .lessonId(problem.getLessonId())
                .problemType(problem.getProblemType())
                .instruction(problem.getInstruction())
                .content(problem.getContent())
                .options(options.stream().map(ProblemOptionResponse::from).toList())
                .answer(null)
                .build();
    }

    public static ProblemDetailResponse subjective(
            Problem problem,
            Answer answer
    ) {
        return ProblemDetailResponse.builder()
                .problemId(problem.getId())
                .lessonId(problem.getLessonId())
                .problemType(problem.getProblemType())
                .instruction(problem.getInstruction())
                .content(problem.getContent())
                .options(null)
                .answer(ProblemAnswerResponse.from(answer))
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    public record ProblemOptionResponse(

            long optionId,

            String content,

            String explanation,

            @JsonProperty("isAnswer")
            boolean isAnswer
    ) {
        public static ProblemOptionResponse from(Option option) {
            return ProblemOptionResponse.builder()
                    .optionId(option.getId())
                    .content(option.getContent())
                    .explanation(option.getExplanation())
                    .isAnswer(option.isAnswer())
                    .build();
        }
    }

    @Builder(access = AccessLevel.PRIVATE)
    public record ProblemAnswerResponse(

            long answerId,

            String content,

            String explanation
    ) {
        public static ProblemAnswerResponse from(Answer answer) {
            return ProblemAnswerResponse.builder()
                    .answerId(answer.getId())
                    .content(answer.getContent())
                    .explanation(answer.getExplanation())
                    .build();
        }
    }
}
