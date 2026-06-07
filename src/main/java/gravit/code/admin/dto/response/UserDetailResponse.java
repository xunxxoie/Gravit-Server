package gravit.code.admin.dto.response;

import gravit.code.admin.domain.AdminUser;
import gravit.code.user.domain.Role;
import gravit.code.user.domain.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "유저 상세")
public record UserDetailResponse(

        long userId,

        String email,

        String nickname,

        String handle,

        int profileImgNumber,

        Role role,

        UserStatus status,

        int level,

        LocalDateTime createdAt
) {
    public static UserDetailResponse from(AdminUser user) {
        return new UserDetailResponse(
                user.getUserId(),
                user.getEmail(),
                user.getNickname(),
                user.getHandle(),
                user.getProfileImgNumber(),
                Role.valueOf(user.getRole()),
                UserStatus.valueOf(user.getStatus()),
                user.getLevel(),
                user.getCreatedAt()
        );
    }
}
