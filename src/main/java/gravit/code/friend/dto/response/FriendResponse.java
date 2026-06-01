package gravit.code.friend.dto.response;

import gravit.code.friend.domain.Friend;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record FriendResponse(

        @NotNull
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        Long followeeId,
        @NotNull
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        Long followerId
) {
    public static FriendResponse from(Friend friend) {
        return FriendResponse.builder()
                .followeeId(friend.getFolloweeId())
                .followerId(friend.getFollowerId())
                .build();
    }
}
