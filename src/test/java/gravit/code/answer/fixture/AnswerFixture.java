package gravit.code.answer.fixture;

import gravit.code.answer.domain.Answer;
import org.springframework.test.util.ReflectionTestUtils;

public class AnswerFixture {

    public static Answer 기본_정답(long problemId) {
        Answer answer = Answer.create("정답 내용", "정답 해설", problemId);
        ReflectionTestUtils.setField(answer, "id", 1L);
        return answer;
    }

    public static Answer 저장된_정답(
            long id,
            long problemId
    ) {
        Answer answer = Answer.create("정답" + id, "해설" + id, problemId);
        ReflectionTestUtils.setField(answer, "id", id);
        return answer;
    }
}
