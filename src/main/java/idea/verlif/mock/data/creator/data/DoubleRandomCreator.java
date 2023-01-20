package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Verlif
 */
public class DoubleRandomCreator implements DataCreator<Double> {

    private final double offset;

    private final double point;

    private final Random random;

    public DoubleRandomCreator() {
        this(Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public DoubleRandomCreator(double min, double max) {
        offset = max / 2 - min / 2;
        point = (min / 2 + max / 2);
        random = new Random();
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(Double.class);
        list.add(double.class);
        return list;
    }

    @Override
    public Double mock(Field field, MockDataCreator.Creator creator) {
        double num = Math.random() * offset;
        if (random.nextBoolean()) {
            return point - num;
        } else {
            return point + num;
        }
    }
}
