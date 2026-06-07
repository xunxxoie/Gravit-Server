package gravit.code.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import gravit.code.answer.domain.Answer;
import gravit.code.option.domain.Option;
import gravit.code.problem.domain.Problem;
import gravit.code.problem.domain.ProblemType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "문제 상세 (OBJECTIVE=options / SUBJECTIVE=answer)")
public record ProblemDetailResponse(

        long problemId,

        long lessonId,

        ProblemType problemType,

        String instruction,

        String content,

        @Schema(description = "객관식 옵션 4개 (주관식이면 null)")
        List<ProblemOptionResponse> options,

        @Schema(description = "주관식 단일 정답 (객관식이면 null)")
        ProblemAnswerResponse answer
) {
    public static ProblemDetailResponse objective(
            Problem problem,
            List<Option> options
    ) {
        return new ProblemDetailResponse(
                problem.getId(),
                problem.getLessonId(),
                problem.getProblemType(),
                problem.getInstruction(),
                problem.getContent(),
                options.stream().map(ProblemOptionResponse::from).toList(),
                null
        );
    }

    public static ProblemDetailResponse subjective(
            Problem problem,
            Answer answer
    ) {
        return new ProblemDetailResponse(
                problem.getId(),
                problem.getLessonId(),
                problem.getProblemType(),
                problem.getInstruction(),
                problem.getContent(),
                null,
                ProblemAnswerResponse.from(answer)
        );
    }

    @Schema(description = "객관식 옵션")
    public record ProblemOptionResponse(

            long optionId,

            String content,

            String explanation,

            @JsonProperty("isAnswer")
            boolean isAnswer
    ) {
        public static ProblemOptionResponse from(Option option) {
            return new ProblemOptionResponse(option.getId(), option.getContent(), option.getExplanation(), option.isAnswer());
        }
    }

    @Schema(description = "주관식 정답")
    public record ProblemAnswerResponse(

            long answerId,

            String content,

            String explanation
    ) {
        public static ProblemAnswerResponse from(Answer answer) {
            return new ProblemAnswerResponse(answer.getId(), answer.getContent(), answer.getExplanation());
        }
    }
}
