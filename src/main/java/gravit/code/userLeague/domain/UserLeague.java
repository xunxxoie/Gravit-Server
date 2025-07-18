package gravit.code.userLeague.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "user_league")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLeague {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "integer", nullable = false)
    private Integer level;

    @Column(columnDefinition = "integer", nullable = false)
    private Integer xp;

    @Column(name = "user_id", columnDefinition = "bigint", nullable = false)
    private Long userId;

    @Column(name = "league_id", columnDefinition = "bigint", nullable = false)
    private Long leagueId;

    @Builder
    private UserLeague(Integer level, Integer xp, Long userId, Long leagueId) {
        this.level = level;
        this.xp = xp;
        this.userId = userId;
        this.leagueId = leagueId;
    }

    public static UserLeague create(Integer level, Integer xp, Long userId, Long leagueId) {
        return UserLeague.builder()
                .level(level)
                .xp(xp)
                .userId(userId)
                .leagueId(leagueId)
                .build();
    }
}
