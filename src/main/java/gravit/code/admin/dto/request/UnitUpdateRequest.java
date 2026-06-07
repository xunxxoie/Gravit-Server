package gravit.code.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "유닛 부분 수정 요청 (미제공 필드는 유지)")
public record UnitUpdateRequest(

        @Schema(description = "제목 (제공 시 빈 값 불가)")
        @Size(max = 255)
        String title,

        @Schema(description = "설명")
        @Size(max = 255)
        String description
) {
}
