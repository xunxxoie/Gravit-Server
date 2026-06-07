package gravit.code.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import gravit.code.admin.domain.staging.AnswerStaging;
import gravit.code.admin.domain.staging.LabelStatus;
import gravit.code.admin.domain.staging.LessonStaging;
import gravit.code.admin.domain.staging.OptionStaging;
import gravit.code.admin.domain.staging.ProblemStaging;
import gravit.code.admin.domain.staging.StagingLabel;
import gravit.code.problem.domain.ProblemType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "스테이징 라벨 상세 (그루핑)")
public record StagingLabelDetailResponse(

        String label,

        long unitId,

        String description,

        LabelStatus status,

        LocalDateTime createdAt,

        StagingLessonResponse lesson,

        List<StagingProblemResponse> problems
) {
    public static StagingLabelDetailResponse of(
            StagingLabel stagingLabel,
            StagingLessonResponse lesson,
            List<StagingProblemResponse> problems
    ) {
        return new StagingLabelDetailResponse(
                stagingLabel.getLabel(),
                stagingLabel.getUnitId(),
                stagingLabel.getDescription(),
                stagingLabel.getStatus(),
                stagingLabel.getCreatedAt(),
                lesson,
                problems
        );
    }

    @Schema(description = "스테이징 레슨")
    public record StagingLessonResponse(

            long lessonId,

            String title
    ) {
        public static StagingLessonResponse from(LessonStaging lesson) {
            return new StagingLessonResponse(lesson.getId(), lesson.getTitle());
        }
    }

    @Schema(description = "스테이징 문제")
    public record StagingProblemResponse(

            long problemId,

            ProblemType problemType,

            String instruction,

            String content,

            List<StagingOptionResponse> options,

            StagingAnswerResponse answer
    ) {
        public static StagingProblemResponse objective(
                ProblemStaging problem,
                List<OptionStaging> options
        ) {
            return new StagingProblemResponse(
                    problem.getId(),
                    problem.getProblemType(),
                    problem.getInstruction(),
                    problem.getContent(),
                    options.stream().map(StagingOptionResponse::from).toList(),
                    null
            );
        }

        public static StagingProblemResponse subjective(
                ProblemStaging problem,
                AnswerStaging answer
        ) {
            return new StagingProblemResponse(
                    problem.getId(),
                    problem.getProblemType(),
                    problem.getInstruction(),
                    problem.getContent(),
                    null,
                    answer == null ? null : StagingAnswerResponse.from(answer)
            );
        }
    }

    @Schema(description = "스테이징 옵션")
    public record StagingOptionResponse(

            long optionId,

            String content,

            String explanation,

            @JsonProperty("isAnswer")
            boolean isAnswer
    ) {
        public static StagingOptionResponse from(OptionStaging option) {
            return new StagingOptionResponse(option.getId(), option.getContent(), option.getExplanation(), option.isAnswer());
        }
    }

    @Schema(description = "스테이징 정답")
    public record StagingAnswerResponse(

            long answerId,

            String content,

            String explanation
    ) {
        public static StagingAnswerResponse from(AnswerStaging answer) {
            return new StagingAnswerResponse(answer.getId(), answer.getContent(), answer.getExplanation());
        }
    }
}
