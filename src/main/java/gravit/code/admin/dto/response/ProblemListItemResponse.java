package gravit.code.admin.dto.response;

import gravit.code.problem.domain.Problem;
import gravit.code.problem.domain.ProblemType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "문제 목록 항목")
public record ProblemListItemResponse(

        long problemId,

        ProblemType problemType,

        String instruction
) {
    public static ProblemListItemResponse from(Problem problem) {
        return new ProblemListItemResponse(problem.getId(), problem.getProblemType(), problem.getInstruction());
    }
}
