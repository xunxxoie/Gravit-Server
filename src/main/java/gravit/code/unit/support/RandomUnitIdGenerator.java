package gravit.code.unit.support;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class RandomUnitIdGenerator {

    public static long[] pickTwoRandomUnitId(
            long seed,
            long totalUnits
    ) {
        Random random = new Random(seed);

        long first = random.nextLong(totalUnits) + 1;
        long second;

        do {
            second = random.nextLong(totalUnits) + 1;
        } while (second == first);

        return new long[]{first, second};
    }
}
