package gravit.code.league.dto.response;

import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record LeagueDetail(
        long leagueId,
        String leagueName,
        int currentLP,
        int maxLP
) {
    public static LeagueDetail of(
            long leagueId,
            String leagueName,
            int currentLP,
            int maxLP
    ){
        return  LeagueDetail.builder()
                .leagueId(leagueId)
                .leagueName(leagueName)
                .currentLP(currentLP)
                .maxLP(maxLP)
                .build();
    }
}
