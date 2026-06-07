package gravit.code.admin.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "스테이징 옵션 수정 요청 (부분 수정, isAnswer 라벨당 강제 없음)")
public record StagingOptionUpdateRequest(

        @Schema(description = "내용")
        String content,

        @Schema(description = "해설")
        String explanation,

        @Schema(description = "정답 여부")
        @JsonProperty("isAnswer")
        Boolean isAnswer
) {
}
