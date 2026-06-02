package gravit.code.fcm.dto.internal;

import java.util.List;
import java.util.Map;

public record PushMessage(
        List<String> tokens,
        String title,
        String body,
        Map<String, String> data
) {
    public PushMessage {
        tokens = List.copyOf(tokens);
        data = Map.copyOf(data);
    }

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
