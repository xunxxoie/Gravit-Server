package gravit.code.admin.support;

import gravit.code.global.exception.domain.CustomErrorCode;
import gravit.code.global.exception.domain.RestApiException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * admin 목록 공통 페이지 정책: 1-based, size 20 고정. page < 1 이면 PAGE_MUST_START_FROM_1.
 */
public final class AdminPages {

    public static final int PAGE_SIZE = 20;

    private AdminPages() {
    }

    public static PageRequest of(int page) {
        validate(page);
        return PageRequest.of(page - 1, PAGE_SIZE);
    }

    public static PageRequest of(
            int page,
            Sort sort
    ) {
        validate(page);
        return PageRequest.of(page - 1, PAGE_SIZE, sort);
    }

    private static void validate(int page) {
        if (page < 1) {
            throw new RestApiException(CustomErrorCode.PAGE_MUST_START_FROM_1);
        }
    }
}
