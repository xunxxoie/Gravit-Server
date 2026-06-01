package gravit.code.friend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record FollowCountsResponse(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        long followerCount,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        long followingCount
) {
}
