package gravit.code.option.fixture;

import gravit.code.option.domain.Option;
import org.springframework.test.util.ReflectionTestUtils;

public class OptionFixture {

    public static Option 정답_선택지(long problemId) {
        Option option = Option.create("정답 내용", "정답 해설", true, problemId);
        ReflectionTestUtils.setField(option, "id", 1L);
        return option;
    }

    public static Option 오답_선택지(long problemId) {
        Option option = Option.create("오답 내용", "오답 해설", false, problemId);
        ReflectionTestUtils.setField(option, "id", 2L);
        return option;
    }

    public static Option 저장된_선택지(
            long id,
            boolean isAnswer,
            long problemId
    ) {
        Option option = Option.create("선택지" + id, "해설" + id, isAnswer, problemId);
        ReflectionTestUtils.setField(option, "id", id);
        return option;
    }
}
