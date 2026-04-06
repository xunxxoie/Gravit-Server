package gravit.code.wrongAnsweredNote.fixture;

import gravit.code.wrongAnsweredNote.domain.WrongAnsweredNote;
import org.springframework.test.util.ReflectionTestUtils;

public class WrongAnsweredNoteFixture {

    public static WrongAnsweredNote 기본_오답노트(
            long problemId,
            long userId
    ) {
        WrongAnsweredNote note = WrongAnsweredNote.create(problemId, userId);
        ReflectionTestUtils.setField(note, "id", 1L);
        return note;
    }

    public static WrongAnsweredNote 저장된_오답노트(
            long id,
            long problemId,
            long userId
    ) {
        WrongAnsweredNote note = WrongAnsweredNote.create(problemId, userId);
        ReflectionTestUtils.setField(note, "id", id);
        return note;
    }
}
