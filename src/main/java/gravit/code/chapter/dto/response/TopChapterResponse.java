package gravit.code.chapter.dto.response;

import gravit.code.chapter.dto.internal.ChapterSolvedStatDto;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record TopChapterResponse(
        int rank,
        String chapterTitle,
        int solvedLessonCount,
        int ratio
) {
    public static TopChapterResponse of(
            int rank,
            ChapterSolvedStatDto stat,
            long weeklySolvedTotal
    ) {
        int ratio = weeklySolvedTotal == 0
                ? 0
                : Math.toIntExact(stat.solvedLessonCount() * 100 / weeklySolvedTotal);

        return TopChapterResponse.builder()
                .rank(rank)
                .chapterTitle(stat.chapterTitle())
                .solvedLessonCount(Math.toIntExact(stat.solvedLessonCount()))
                .ratio(ratio)
                .build();
    }
}
