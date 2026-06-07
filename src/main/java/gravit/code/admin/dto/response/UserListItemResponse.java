package gravit.code.admin.dto.response;

import gravit.code.admin.domain.AdminUser;
import gravit.code.user.domain.Role;
import gravit.code.user.domain.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "유저 목록 항목")
public record UserListItemResponse(

        long userId,

        String email,

        String nickname,

        String handle,

        Role role,

        UserStatus status,

        LocalDateTime createdAt
) {
    public static UserListItemResponse from(AdminUser user) {
        return new UserListItemResponse(
                user.getUserId(),
                user.getEmail(),
                user.getNickname(),
                user.getHandle(),
                Role.valueOf(user.getRole()),
                UserStatus.valueOf(user.getStatus()),
                user.getCreatedAt()
        );
    }
}
