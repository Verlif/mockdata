package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.mock.data.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Verlif
 */
public class DoubleRandomCreator implements DataCreator<Double> {

    private final double offset;

    private final double point;

    public DoubleRandomCreator() {
        this(Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public DoubleRandomCreator(double min, double max) {
        offset = max / 2 - min / 2;
        point = (min / 2 + max / 2);
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(Double.class);
        list.add(double.class);
        return list;
    }

    @Override
    public Double mock(MockSrc src, MockDataCreator.Creator creator) {
        double num = RandomUtil.nextDouble() * offset;
        if (RandomUtil.nextBoolean()) {
            return point - num;
        } else {
            return point + num;
        }
    }
}
