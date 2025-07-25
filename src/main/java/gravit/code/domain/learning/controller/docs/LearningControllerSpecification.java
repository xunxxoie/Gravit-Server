package gravit.code.domain.learning.controller.docs;

import gravit.code.auth.oauth.LoginUser;
import gravit.code.domain.chapterProgress.dto.response.ChapterInfoResponse;
import gravit.code.domain.learning.dto.request.LearningResultSaveRequest;
import gravit.code.domain.lesson.dto.response.LessonResponse;
import gravit.code.domain.unitProgress.dto.response.UnitPageResponse;
import gravit.code.domain.user.dto.response.UserLevelResponse;
import gravit.code.global.exception.domain.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "LearningController", description = "학습 관련 API")
public interface LearningControllerSpecification {

    @Operation(summary = "챕터 목록 조회", description = "사용자의 챕터 진행 상황과 함께 전체 챕터 목록을 조회합니다<br>" +
            "🔐 <strong>Jwt 필요</strong><br>")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "✅ 챕터 목록 조회 성공"),
            @ApiResponse(responseCode = "USER_4041", description = "🚨 유저 조회 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "유저 조회 실패",
                                            value = "{\"error\" : \"USER_4041\", \"message\" : \"유저 조회 실패\"}"
                                    )
                            },
                            schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "GLOBAL_5001", description = "🚨 예기치 못한 예외 발생",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "예기치 못한 예외 발생",
                                            value = "{\"error\" : \"GLOBAL_5001\", \"message\" : \"예기치 못한 예외 발생\"}"
                                    )
                            },
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/chapters")
    ResponseEntity<List<ChapterInfoResponse>> getAllChapters(@AuthenticationPrincipal LoginUser loginUser);

    @Operation(summary = "유닛 목록 조회", description = "특정 챕터의 유닛 목록과 레슨 진행 상황을 조회합니다<br>" +
            "🔐 <strong>Jwt 필요</strong><br>")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "✅ 유닛 목록 조회 성공"),
            @ApiResponse(responseCode = "USER_4041", description = "🚨 유저 조회 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "유저 조회 실패",
                                            value = "{\"error\" : \"USER_4041\", \"message\" : \"유저 조회 실패\"}"
                                    )
                            },
                            schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "GLOBAL_5001", description = "🚨 예기치 못한 예외 발생",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "예기치 못한 예외 발생",
                                            value = "{\"error\" : \"GLOBAL_5001\", \"message\" : \"예기치 못한 예외 발생\"}"
                                    )
                            },
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{chapterId}/units")
    ResponseEntity<List<UnitPageResponse>> getAllUnits(@AuthenticationPrincipal LoginUser loginUser,
                                                       @PathVariable("chapterId") Long chapterId);

    @Operation(summary = "레슨 문제 목록 조회", description = "특정 레슨에 포함된 문제 목록을 조회합니다<br>" +
            "🔐 <strong>Jwt 필요</strong><br>")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "✅ 레슨 문제 목록 조회 성공"),
            @ApiResponse(responseCode = "USER_4041", description = "🚨 유저 조회 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "유저 조회 실패",
                                            value = "{\"error\" : \"USER_4041\", \"message\" : \"유저 조회 실패\"}"
                                    )
                            },
                            schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "LESSON_4041", description = "🚨 레슨 조회 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "레슨 조회 실패",
                                            value = "{\"error\" : \"LESSON_4041\", \"message\" : \"레슨 조회 실패\"}"
                                    )
                            },
                            schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "GLOBAL_5001", description = "🚨 예기치 못한 예외 발생",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "예기치 못한 예외 발생",
                                            value = "{\"error\" : \"GLOBAL_5001\", \"message\" : \"예기치 못한 예외 발생\"}"
                                    )
                            },
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{lessonId}/problems")
    ResponseEntity<List<LessonResponse>> getLessonProblems(@PathVariable("lessonId") Long lessonId);

    @Operation(summary = "학습 결과 저장", description = "레슨 완료 후 문제 풀이 결과를 저장하고 사용자 레벨을 업데이트합니다<br>" +
            "🔐 <strong>Jwt 필요</strong><br>")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "✅ 학습 결과 저장 성공"),
            @ApiResponse(responseCode = "USER_4041", description = "🚨 유저 조회 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "유저 조회 실패",
                                            value = "{\"error\" : \"USER_4041\", \"message\" : \"유저 조회 실패\"}"
                                    )
                            },
                            schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "CHAPTER_P_4041", description = "🚨 챕터 진행 결과 조회 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "챕터 진행 결과 조회 실패",
                                            value = "{\"error\" : \"CHAPTER_P_4041\", \"message\" : \"챕터 진행 결과 조회 실패\"}"
                                    )
                            },
                            schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "UNIT_P_4041", description = "🚨 유닛 진행 결과 조회 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "유닛 진행 결과 조회 실패",
                                            value = "{\"error\" : \"UNIT_P_4041\", \"message\" : \"유닛 진행 결과 조회 실패\"}"
                                    )
                            },
                            schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "LESSON_P_4041", description = "🚨 레슨 진행 결과 조회 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "레슨 진행 결과 조회 실패",
                                            value = "{\"error\" : \"LESSON_P_4041\", \"message\" : \"레슨 진행 결과 조회 실패\"}"
                                    )
                            },
                            schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "GLOBAL_4001", description = "🚨 유효성 검사 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "유효성 검사 실패",
                                            value = "{\"error\" : \"GLOBAL_4001\", \"message\" : \"유효성 검사 실패\"}"
                                    )
                            },
                            schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "GLOBAL_5001", description = "🚨 예기치 못한 예외 발생",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "예기치 못한 예외 발생",
                                            value = "{\"error\" : \"GLOBAL_5001\", \"message\" : \"예기치 못한 예외 발생\"}"
                                    )
                            },
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/results")
    ResponseEntity<UserLevelResponse> saveLearningResult(@AuthenticationPrincipal LoginUser loginUser,
                                                         @Valid @RequestBody LearningResultSaveRequest request);
}
