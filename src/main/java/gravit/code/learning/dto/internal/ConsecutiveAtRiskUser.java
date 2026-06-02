package gravit.code.learning.dto.internal;

public record ConsecutiveAtRiskUser(
        long userId,
        int consecutiveSolvedDays
) {
}
