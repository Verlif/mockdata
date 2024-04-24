package idea.verlif.mock.data.random;

import idea.verlif.mock.data.util.RandomUtil;

import java.time.LocalDate;

public class LocalDateRandom implements DataRandom<LocalDate> {

    private static final LocalDateRandom RANDOM;
    static {
        RANDOM = new LocalDateRandom();
    }

    public static LocalDateRandom getRANDOM() {
        return RANDOM;
    }

    @Override
    public LocalDate next() {
        LocalDate now = LocalDate.now();
        return next(LocalDate.now().plusYears(-80), now);
    }

    public LocalDate next(LocalDate min, LocalDate max) {
        int year = max.getYear() - min.getYear();
        if (year == 0) {
            return LocalDate.of(min.getYear(), 1, 1).plusDays(RandomUtil.range(min.getDayOfYear(), max.getDayOfYear()) - 1);
        } else if (year == 1) {
            int dayOfYear = LocalDate.of(min.getYear(), 1, 1).lengthOfYear();
            int day = dayOfYear + max.getDayOfYear() - min.getDayOfYear() + 1;
            return LocalDate.of(min.getYear(), 1, 1).plusDays(RandomUtil.nextInt(day) + min.getDayOfYear() - 1);
        } else {
            year -= 1;
            int dayOfYear = LocalDate.of(min.getYear(), 1, 1).lengthOfYear();
            int day = dayOfYear + max.getDayOfYear() - min.getDayOfYear();
            return LocalDate.of(min.getYear() + RandomUtil.nextInt(year), 1, 1).plusDays(RandomUtil.nextInt(day + dayOfYear) + min.getDayOfYear() - 1);
        }
    }

}
