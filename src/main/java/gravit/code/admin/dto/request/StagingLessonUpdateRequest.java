package gravit.code.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "스테이징 레슨 수정 요청")
public record StagingLessonUpdateRequest(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "레슨 제목은 비어있을 수 없습니다.")
        String title
) {
}
