package gravit.code.fcm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record RegisterFcmTokenRequest(
        @Schema(description = "클라이언트 디바이스 식별자(설치 단위 고유 ID)", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
        @NotBlank(message = "디바이스 아이디가 비어있습니다")
        String deviceId,

        @Schema(description = "FCM 등록 토큰", example = "fGcT9...:APA91bH...")
        @NotBlank(message = "FCM 토큰이 비어있습니다.")
        String fcmToken
) {
}
