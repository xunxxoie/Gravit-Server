package gravit.code.admin.domain;

import java.time.LocalDateTime;

/**
 * admin 전용 users 읽기 projection. User 엔티티의 @SQLRestriction(deleted_at IS NULL) 을 우회하기 위해
 * AdminUserRepository 의 native query 결과로 매핑된다(DELETED 포함 조회). users 테이블에 별도 엔티티를
 * 매핑하지 않으므로 스키마/식별자 생성에 영향이 없다. role/status 는 DB varchar 그대로 String 으로 노출.
 */
public interface AdminUser {

    long getUserId();

    String getEmail();

    String getNickname();

    String getHandle();

    int getProfileImgNumber();

    String getRole();

    String getStatus();

    int getLevel();

    LocalDateTime getCreatedAt();
}
