package gravit.code.fcm.dto.internal;

import java.util.List;
import java.util.Map;

// 발송 단위: 한 유저의 토큰 목록 + 제목/본문 + 액션 라우팅용 data payload
public record PushMessage(
        List<String> tokens,
        String title,
        String body,
        Map<String, String> data
) {
    public static PushMessage of(
            List<String> tokens,
            String title,
            String body,
            Map<String, String> data
    ) {
        return new PushMessage(tokens, title, body, data);
    }

    public static PushMessage of(
            List<String> tokens,
            String title,
            String body
    ) {
        return new PushMessage(tokens, title, body, Map.of());
    }
}
