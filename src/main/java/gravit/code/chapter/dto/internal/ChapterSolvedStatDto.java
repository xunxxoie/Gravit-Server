package gravit.code.chapter.dto.internal;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ChapterSolvedStatDto(
        long chapterId,
        String chapterTitle,
        long solvedLessonCount
) {
    public static ChapterSolvedStatDto of(
            long chapterId,
            String chapterTitle,
            long solvedLessonCount
    ) {
        return ChapterSolvedStatDto.builder()
                .chapterId(chapterId)
                .chapterTitle(chapterTitle)
                .solvedLessonCount(solvedLessonCount)
                .build();
    }
}
