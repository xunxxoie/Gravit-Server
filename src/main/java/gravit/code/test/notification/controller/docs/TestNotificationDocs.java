package gravit.code.test.notification.controller.docs;

import gravit.code.auth.domain.LoginUser;
import gravit.code.global.exception.domain.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Test Notification API", description = "[QA 전용] 발송 조건과 무관하게 로그인한 본인에게 푸시 알림을 즉시 발송하는 테스트 API")
public interface TestNotificationDocs {

    @Operation(
            summary = "[테스트] 연속학습 끊길 위기 알림 발송",
            description = "토큰으로 식별된 <strong>본인</strong>에게 CONSECUTIVE_LEARNING_WARNING 알림을 즉시 발송합니다.<br>"
                    + "🔐 <strong>Jwt 필요</strong> (수신자는 토큰의 유저로 결정되며, 별도 userId를 받지 않습니다)<br>"
                    + "연속학습 일수 조건을 검사하지 않고 바로 발송합니다.<br>"
                    + "<strong>consecutiveDays</strong>: 메시지에 표시될 연속학습 일수 (예: 3 → \"오늘 학습을 하지 않으면 3일 연속학습이 끊겨요!\")<br>"
                    + "본인 기기에 FCM 토큰이 등록돼 있지 않으면 발송 없이 무시됩니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "✅ 발송 요청 성공 (수신자 userId 반환)"),
            @ApiResponse(responseCode = "401", description = "🚨 인증 실패 (토큰 누락/만료)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "500", description = "🚨 예기치 못한 예외 발생",
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
    ResponseEntity<Long> sendConsecutiveLearningWarning(
            @AuthenticationPrincipal LoginUser loginUser,
            @Parameter(description = "메시지에 표시될 연속학습 일수", example = "3")
            @RequestParam(defaultValue = "3") int consecutiveDays
    );

    @Operation(
            summary = "[테스트] 오늘 학습 미완료 알림 발송",
            description = "토큰으로 식별된 <strong>본인</strong>에게 DAILY_INCOMPLETE 알림을 즉시 발송합니다.<br>"
                    + "🔐 <strong>Jwt 필요</strong> (수신자는 토큰의 유저로 결정됩니다)<br>"
                    + "학습 미완료 조건을 검사하지 않고 바로 발송합니다.<br>"
                    + "메시지는 사전 정의된 문구 중 무작위로 선택됩니다.<br>"
                    + "본인 기기에 FCM 토큰이 등록돼 있지 않으면 발송 없이 무시됩니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "✅ 발송 요청 성공 (수신자 userId 반환)"),
            @ApiResponse(responseCode = "401", description = "🚨 인증 실패 (토큰 누락/만료)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "500", description = "🚨 예기치 못한 예외 발생",
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
    ResponseEntity<Long> sendDailyIncomplete(
            @AuthenticationPrincipal LoginUser loginUser
    );

    @Operation(
            summary = "[테스트] 장기 미접속 알림 발송",
            description = "토큰으로 식별된 <strong>본인</strong>에게 INACTIVITY 알림을 즉시 발송합니다.<br>"
                    + "🔐 <strong>Jwt 필요</strong> (수신자는 토큰의 유저로 결정됩니다)<br>"
                    + "미접속 일수 조건을 검사하지 않고 바로 발송합니다.<br>"
                    + "<strong>inactiveDays</strong>: 메시지를 결정하는 미접속 일수. 7 / 14 / 30 / 60 / 90 은 전용 문구가 매칭되며, 그 외 값은 기본 문구가 사용됩니다.<br>"
                    + "본인 기기에 FCM 토큰이 등록돼 있지 않으면 발송 없이 무시됩니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "✅ 발송 요청 성공 (수신자 userId 반환)"),
            @ApiResponse(responseCode = "401", description = "🚨 인증 실패 (토큰 누락/만료)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "500", description = "🚨 예기치 못한 예외 발생",
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
    ResponseEntity<Long> sendInactivity(
            @AuthenticationPrincipal LoginUser loginUser,
            @Parameter(description = "메시지를 결정하는 미접속 일수 (7/14/30/60/90 전용 문구)", example = "7")
            @RequestParam(defaultValue = "7") int inactiveDays
    );

    @Operation(
            summary = "[테스트] 새 콘텐츠 알림 발송",
            description = "토큰으로 식별된 <strong>본인</strong>에게 NEW_CONTENT 알림을 즉시 발송합니다.<br>"
                    + "🔐 <strong>Jwt 필요</strong> (수신자는 토큰의 유저로 결정됩니다)<br>"
                    + "<strong>unitId</strong>: 딥링크 이동 대상이 되는 유닛 ID (FCM data의 targetId로 전달됩니다)<br>"
                    + "본인 기기에 FCM 토큰이 등록돼 있지 않으면 발송 없이 무시됩니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "✅ 발송 요청 성공 (수신자 userId 반환)"),
            @ApiResponse(responseCode = "401", description = "🚨 인증 실패 (토큰 누락/만료)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "500", description = "🚨 예기치 못한 예외 발생",
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
    ResponseEntity<Long> sendNewContent(
            @AuthenticationPrincipal LoginUser loginUser,
            @Parameter(description = "딥링크 대상 유닛 ID", example = "1")
            @RequestParam Long unitId
    );
}
