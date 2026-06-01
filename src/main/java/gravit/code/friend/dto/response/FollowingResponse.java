package gravit.code.friend.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record FollowingResponse(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        long id,
        @NotNull
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String nickname,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        int profileImgNumber,
        @NotNull
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String handle
){
}
