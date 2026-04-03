package gravit.code.problem.fixture;

import gravit.code.problem.domain.ProblemSubmission;
import org.springframework.test.util.ReflectionTestUtils;

public class ProblemSubmissionFixture {

    public static ProblemSubmission 정답_제출(
            long problemId,
            long userId
    ) {
        ProblemSubmission submission = ProblemSubmission.create(true, problemId, userId);
        ReflectionTestUtils.setField(submission, "id", 1L);
        return submission;
    }

    public static ProblemSubmission 오답_제출(
            long problemId,
            long userId
    ) {
        ProblemSubmission submission = ProblemSubmission.create(false, problemId, userId);
        ReflectionTestUtils.setField(submission, "id", 2L);
        return submission;
    }

    public static ProblemSubmission 저장된_문제_제출(
            long id,
            boolean isCorrect,
            long problemId,
            long userId
    ) {
        ProblemSubmission submission = ProblemSubmission.create(isCorrect, problemId, userId);
        ReflectionTestUtils.setField(submission, "id", id);
        return submission;
    }
}
