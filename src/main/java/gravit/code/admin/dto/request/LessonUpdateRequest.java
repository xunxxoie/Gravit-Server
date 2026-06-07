package gravit.code.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "레슨 부분 수정 요청 (title 만)")
public record LessonUpdateRequest(

        @Schema(description = "제목 (제공 시 빈 값 불가)")
        @Size(max = 255)
        String title
) {
}
