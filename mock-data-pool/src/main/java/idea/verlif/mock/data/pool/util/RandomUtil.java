package idea.verlif.mock.data.pool.util;

import java.util.Random;

public class RandomUtil {

    private static final Random RANDOM = new Random();

    public static int next(int limit) {
        if (limit < 1) {
            return 0;
        }
        return RANDOM.nextInt(limit);
    }

    public static <T> T next(T[] ts) {
        return ts[RANDOM.nextInt(ts.length)];
    }

    public static int range(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    public static boolean bool() {
        return RANDOM.nextBoolean();
    }
}
