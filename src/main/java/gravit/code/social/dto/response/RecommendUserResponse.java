package gravit.code.social.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record RecommendUserResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        long userId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String nickname,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        int profileImgNumber,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        int mutualFollowCount
) {
    public static RecommendUserResponse of(
            long userId,
            String nickname,
            int profileImgNumber,
            int mutualFollowCount
    ) {
        return RecommendUserResponse.builder()
                .userId(userId)
                .nickname(nickname)
                .profileImgNumber(profileImgNumber)
                .mutualFollowCount(mutualFollowCount)
                .build();
    }
}
