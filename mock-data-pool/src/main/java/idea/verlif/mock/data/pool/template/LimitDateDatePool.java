package idea.verlif.mock.data.pool.template;

import idea.verlif.mock.data.pool.SimplePool;
import idea.verlif.mock.data.pool.random.LocalDateRandom;
import idea.verlif.reflection.domain.ClassGrc;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class LimitDateDatePool extends LocalDateRandom implements SimplePool {

    private LocalDate min;
    private LocalDate max;

    public LimitDateDatePool() {
    }

    public LimitDateDatePool(LocalDate min, LocalDate max) {
        setMin(min);
        setMax(max);
    }

    public Date mock() {
        LocalDate localDate = next();
        Instant instant = localDate.atTime(LocalTime.MIDNIGHT).atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    @Override
    public Object fetch(ClassGrc classGrc, String key) {
        return mock();
    }

    public LocalDate getMin() {
        return min;
    }

    public void setMin(LocalDate min) {
        this.min = min;
    }

    public LocalDate getMax() {
        return max;
    }

    public void setMax(LocalDate max) {
        this.max = max;
    }
}
