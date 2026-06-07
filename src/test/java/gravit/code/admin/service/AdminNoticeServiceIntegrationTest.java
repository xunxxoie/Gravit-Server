package gravit.code.admin.service;

import gravit.code.admin.dto.request.NoticeCreateRequest;
import gravit.code.admin.dto.request.NoticeUpdateRequest;
import gravit.code.admin.dto.response.NoticeDetailResponse;
import gravit.code.admin.dto.response.NoticeListItemResponse;
import gravit.code.global.dto.response.PageResponse;
import gravit.code.global.exception.domain.CustomErrorCode;
import gravit.code.global.exception.domain.RestApiException;
import gravit.code.notice.domain.NoticeStatus;
import gravit.code.support.TCSpringBootTest;
import gravit.code.user.domain.User;
import gravit.code.user.fixture.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@TCSpringBootTest
class AdminNoticeServiceIntegrationTest {

    @Autowired
    private AdminNoticeService adminNoticeService;

    @Autowired
    private UserFixture userFixture;

    private long adminId;

    @BeforeEach
    void setUp() {
        User admin = userFixture.일반_유저(1);
        adminId = admin.getId();
    }

    @Test
    @DisplayName("공지 생성: PUBLISHED 면 publishedAt 세팅")
    void createNotice_published() {
        NoticeDetailResponse created = adminNoticeService.createNotice(adminId,
                new NoticeCreateRequest("제목", "요약", "본문", NoticeStatus.PUBLISHED, false));

        assertSoftly(softly -> {
            softly.assertThat(created.status()).isEqualTo(NoticeStatus.PUBLISHED);
            softly.assertThat(created.publishedAt()).isNotNull();
            softly.assertThat(created.summary()).isEqualTo("요약");
        });
    }

    @Test
    @DisplayName("공지 생성: DRAFT 면 publishedAt null")
    void createNotice_draft() {
        NoticeDetailResponse created = adminNoticeService.createNotice(adminId,
                new NoticeCreateRequest("제목", "요약", "본문", NoticeStatus.DRAFT, false));

        assertThat(created.status()).isEqualTo(NoticeStatus.DRAFT);
        assertThat(created.publishedAt()).isNull();
    }

    @Test
    @DisplayName("공지 생성: pinned + DRAFT 는 400")
    void createNotice_pinnedDraft() {
        assertThatThrownBy(() -> adminNoticeService.createNotice(adminId,
                new NoticeCreateRequest("제목", "요약", "본문", NoticeStatus.DRAFT, true)))
                .isInstanceOf(RestApiException.class)
                .extracting(e -> ((RestApiException) e).getErrorCode())
                .isEqualTo(CustomErrorCode.NOTICE_PINNED_MUST_BE_PUBLISHED);
    }

    @Test
    @DisplayName("공지 생성: 작성 시 ARCHIVED 불가 (400)")
    void createNotice_archived() {
        assertThatThrownBy(() -> adminNoticeService.createNotice(adminId,
                new NoticeCreateRequest("제목", "요약", "본문", NoticeStatus.ARCHIVED, false)))
                .isInstanceOf(RestApiException.class)
                .extracting(e -> ((RestApiException) e).getErrorCode())
                .isEqualTo(CustomErrorCode.NOTICE_STATUS_INVALID);
    }

    @Test
    @DisplayName("공지 상태 전이: PUBLISHED -> ARCHIVED 허용")
    void update_published_to_archived() {
        NoticeDetailResponse published = adminNoticeService.createNotice(adminId,
                new NoticeCreateRequest("제목", "요약", "본문", NoticeStatus.PUBLISHED, false));

        NoticeDetailResponse updated = adminNoticeService.updateNotice(adminId, published.noticeId(),
                new NoticeUpdateRequest(null, null, null, NoticeStatus.ARCHIVED, null));

        assertThat(updated.status()).isEqualTo(NoticeStatus.ARCHIVED);
    }

    @Test
    @DisplayName("공지 상태 전이: ARCHIVED -> PUBLISHED 차단 (409)")
    void update_archived_to_published_blocked() {
        NoticeDetailResponse published = adminNoticeService.createNotice(adminId,
                new NoticeCreateRequest("제목", "요약", "본문", NoticeStatus.PUBLISHED, false));
        adminNoticeService.updateNotice(adminId, published.noticeId(),
                new NoticeUpdateRequest(null, null, null, NoticeStatus.ARCHIVED, null));

        assertThatThrownBy(() -> adminNoticeService.updateNotice(adminId, published.noticeId(),
                new NoticeUpdateRequest(null, null, null, NoticeStatus.PUBLISHED, null)))
                .isInstanceOf(RestApiException.class)
                .extracting(e -> ((RestApiException) e).getErrorCode())
                .isEqualTo(CustomErrorCode.NOTICE_INVALID_STATUS_TRANSITION);
    }

    @Test
    @DisplayName("공지 상태 전이: DRAFT -> ARCHIVED 차단 (409)")
    void update_draft_to_archived_blocked() {
        NoticeDetailResponse draft = adminNoticeService.createNotice(adminId,
                new NoticeCreateRequest("제목", "요약", "본문", NoticeStatus.DRAFT, false));

        assertThatThrownBy(() -> adminNoticeService.updateNotice(adminId, draft.noticeId(),
                new NoticeUpdateRequest(null, null, null, NoticeStatus.ARCHIVED, null)))
                .isInstanceOf(RestApiException.class)
                .extracting(e -> ((RestApiException) e).getErrorCode())
                .isEqualTo(CustomErrorCode.NOTICE_INVALID_STATUS_TRANSITION);
    }

    @Test
    @DisplayName("공지 상태 전이: DRAFT -> PUBLISHED 시 publishedAt 세팅")
    void update_draft_to_published_setsPublishedAt() {
        NoticeDetailResponse draft = adminNoticeService.createNotice(adminId,
                new NoticeCreateRequest("제목", "요약", "본문", NoticeStatus.DRAFT, false));

        NoticeDetailResponse updated = adminNoticeService.updateNotice(adminId, draft.noticeId(),
                new NoticeUpdateRequest(null, null, null, NoticeStatus.PUBLISHED, null));

        assertThat(updated.status()).isEqualTo(NoticeStatus.PUBLISHED);
        assertThat(updated.publishedAt()).isNotNull();
    }

    @Test
    @DisplayName("공지 부분 수정: 미제공 필드는 유지")
    void update_partial_keepsFields() {
        NoticeDetailResponse created = adminNoticeService.createNotice(adminId,
                new NoticeCreateRequest("원제목", "원요약", "원본문", NoticeStatus.PUBLISHED, false));

        NoticeDetailResponse updated = adminNoticeService.updateNotice(adminId, created.noticeId(),
                new NoticeUpdateRequest("새제목", null, null, null, null));

        assertSoftly(softly -> {
            softly.assertThat(updated.title()).isEqualTo("새제목");
            softly.assertThat(updated.summary()).isEqualTo("원요약");
            softly.assertThat(updated.content()).isEqualTo("원본문");
            softly.assertThat(updated.status()).isEqualTo(NoticeStatus.PUBLISHED);
        });
    }

    @Test
    @DisplayName("공지 삭제(soft delete): 목록/상세에서 제외")
    void deleteNotice_softDelete() {
        NoticeDetailResponse created = adminNoticeService.createNotice(adminId,
                new NoticeCreateRequest("제목", "요약", "본문", NoticeStatus.PUBLISHED, false));

        adminNoticeService.deleteNotice(adminId, created.noticeId());

        PageResponse<NoticeListItemResponse> list = adminNoticeService.getNotices(1);
        assertThat(list.contents()).isEmpty();
        assertThatThrownBy(() -> adminNoticeService.getNotice(created.noticeId()))
                .isInstanceOf(RestApiException.class)
                .extracting(e -> ((RestApiException) e).getErrorCode())
                .isEqualTo(CustomErrorCode.NOTICE_NOT_FOUND);
    }
}
