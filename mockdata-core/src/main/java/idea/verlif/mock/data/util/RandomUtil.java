package idea.verlif.mock.data.util;

import java.util.Random;

public class RandomUtil {

    private static final Random RANDOM = new Random();

    public static int nextInt(int limit) {
        if (limit < 1) {
            return 0;
        }
        return RANDOM.nextInt(limit);
    }

    public static double nextDouble() {
        return RANDOM.nextDouble();
    }

    public static long nextLong() {
        return RANDOM.nextLong();
    }

    public static <T> T next(T[] ts) {
        return ts[RANDOM.nextInt(ts.length)];
    }

    public static int range(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    public static boolean nextBoolean() {
        return RANDOM.nextBoolean();
    }

    public static float nextFloat() {
        return RANDOM.nextFloat();
    }
}
