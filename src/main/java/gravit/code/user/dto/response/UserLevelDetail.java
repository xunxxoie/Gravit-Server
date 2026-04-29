package gravit.code.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UserLevelDetail(
        int level,
        int currentXp,
        int maxXp,
        double levelRate
) {
    public static UserLevelDetail of(
            int level,
            int currentXp,
            int maxXp,
            double levelRate
    ) {
        return UserLevelDetail.builder()
                .level(level)
                .currentXp(currentXp)
                .maxXp(maxXp)
                .levelRate(levelRate)
                .build();
    }
}
