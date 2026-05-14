package gravit.code.user.dto.response;

import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record MyPageBannerResponse(
        int profileImageNumber,
        String nickname,
        String handle,
        int level,
        String currentLeague,
        int consecutiveSolvedDays
) {
    public static MyPageBannerResponse of(
            int profileImageNumber,
            String nickname,
            String handle,
            int level,
            String currentLeague,
            int consecutiveSolvedDays
    ) {
        return MyPageBannerResponse.builder()
                .profileImageNumber(profileImageNumber)
                .nickname(nickname)
                .handle(handle)
                .level(level)
                .currentLeague(currentLeague)
                .consecutiveSolvedDays(consecutiveSolvedDays)
                .build();
    }
}
