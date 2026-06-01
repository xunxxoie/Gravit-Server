package gravit.code.fcm.domain;

import gravit.code.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class FcmToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false, unique = true)
    private String deviceId;

    @Column(nullable = false)
    private String token;

    @Builder(access = PRIVATE)
    private FcmToken(
            long userId,
            String deviceId,
            String token
    ){
        this.userId = userId;
        this.deviceId = deviceId;
        this.token = token;
    }

    public static FcmToken create(
            long userId,
            String deviceId,
            String token
    ){
        return FcmToken.builder()
                .userId(userId)
                .deviceId(deviceId)
                .token(token)
                .build();
    }

    public void updateOwnerAndToken(
            long userId,
            String token
    ) {
        this.userId = userId;
        this.token = token;
    }
}
