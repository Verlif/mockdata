package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Random;

/**
 * @author Verlif
 */
public class LocalDateCreator implements DataCreator<LocalDate> {

    private static final int[] DAY_COUNT = new int[]{
            31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    private final int yearOffset;
    private final int yearPoint;

    private final Random random;

    public LocalDateCreator() {
        yearOffset = 10;
        yearPoint = LocalDate.now().getYear() + yearOffset / 2;

        random = new Random();
    }

    @Override
    public LocalDate mock(Class<?> cla, Field field, MockDataCreator.Creator creator) {
        int month = random.nextInt(12) + 1;
        return LocalDate.of(yearPoint - random.nextInt(yearOffset), month, random.nextInt(DAY_COUNT[month - 1]) + 1);
    }
}
