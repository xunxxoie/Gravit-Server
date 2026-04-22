package gravit.code.bookmark.fixture;

import gravit.code.bookmark.domain.Bookmark;
import org.springframework.test.util.ReflectionTestUtils;

public class BookmarkFixture {

    public static Bookmark 기본_북마크(
            long problemId,
            long userId
    ) {
        Bookmark bookmark = Bookmark.create(problemId, userId);
        ReflectionTestUtils.setField(bookmark, "id", 1L);
        return bookmark;
    }

    public static Bookmark 저장된_북마크(
            long id,
            long problemId,
            long userId
    ) {
        Bookmark bookmark = Bookmark.create(problemId, userId);
        ReflectionTestUtils.setField(bookmark, "id", id);
        return bookmark;
    }
}
