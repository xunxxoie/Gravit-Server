package gravit.code.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import gravit.code.admin.domain.staging.AnswerStaging;
import gravit.code.admin.domain.staging.LabelStatus;
import gravit.code.admin.domain.staging.LessonStaging;
import gravit.code.admin.domain.staging.OptionStaging;
import gravit.code.admin.domain.staging.ProblemStaging;
import gravit.code.admin.domain.staging.StagingLabel;
import gravit.code.problem.domain.ProblemType;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
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
        return StagingLabelDetailResponse.builder()
                .label(stagingLabel.getLabel())
                .unitId(stagingLabel.getUnitId())
                .description(stagingLabel.getDescription())
                .status(stagingLabel.getStatus())
                .createdAt(stagingLabel.getCreatedAt())
                .lesson(lesson)
                .problems(problems)
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    public record StagingLessonResponse(

            long lessonId,

            String title
    ) {
        public static StagingLessonResponse from(LessonStaging lesson) {
            return StagingLessonResponse.builder()
                    .lessonId(lesson.getId())
                    .title(lesson.getTitle())
                    .build();
        }
    }

    @Builder(access = AccessLevel.PRIVATE)
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
            return StagingProblemResponse.builder()
                    .problemId(problem.getId())
                    .problemType(problem.getProblemType())
                    .instruction(problem.getInstruction())
                    .content(problem.getContent())
                    .options(options.stream().map(StagingOptionResponse::from).toList())
                    .answer(null)
                    .build();
        }

        public static StagingProblemResponse subjective(
                ProblemStaging problem,
                AnswerStaging answer
        ) {
            return StagingProblemResponse.builder()
                    .problemId(problem.getId())
                    .problemType(problem.getProblemType())
                    .instruction(problem.getInstruction())
                    .content(problem.getContent())
                    .options(null)
                    .answer(answer == null ? null : StagingAnswerResponse.from(answer))
                    .build();
        }
    }

    @Builder(access = AccessLevel.PRIVATE)
    public record StagingOptionResponse(

            long optionId,

            String content,

            String explanation,

            @JsonProperty("isAnswer")
            boolean isAnswer
    ) {
        public static StagingOptionResponse from(OptionStaging option) {
            return StagingOptionResponse.builder()
                    .optionId(option.getId())
                    .content(option.getContent())
                    .explanation(option.getExplanation())
                    .isAnswer(option.isAnswer())
                    .build();
        }
    }

    @Builder(access = AccessLevel.PRIVATE)
    public record StagingAnswerResponse(

            long answerId,

            String content,

            String explanation
    ) {
        public static StagingAnswerResponse from(AnswerStaging answer) {
            return StagingAnswerResponse.builder()
                    .answerId(answer.getId())
                    .content(answer.getContent())
                    .explanation(answer.getExplanation())
                    .build();
        }
    }
}
