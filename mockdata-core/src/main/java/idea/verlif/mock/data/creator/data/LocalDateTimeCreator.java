package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.mock.data.util.RandomUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Verlif
 */
public class LocalDateTimeCreator implements DataCreator<LocalDateTime> {

    private static final int[] DAY_COUNT = new int[]{
            31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    private final int yearOffset;
    private final int yearPoint;

    public LocalDateTimeCreator() {
        yearOffset = 10;
        yearPoint = LocalDate.now().getYear() + yearOffset / 2;
    }

    @Override
    public LocalDateTime mock(MockSrc src, MockDataCreator.Creator creator) {
        int month = RandomUtil.nextInt(12) + 1;
        LocalDate date = LocalDate.of(yearPoint - RandomUtil.nextInt(yearOffset), month, RandomUtil.nextInt(DAY_COUNT[month - 1]) + 1);
        LocalTime time = LocalTime.of(RandomUtil.nextInt(24), RandomUtil.nextInt(60));
        return LocalDateTime.of(date, time);
    }
}
