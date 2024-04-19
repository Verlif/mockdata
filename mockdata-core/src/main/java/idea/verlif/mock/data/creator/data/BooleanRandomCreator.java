package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.mock.data.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class BooleanRandomCreator implements DataCreator<Boolean> {

    public BooleanRandomCreator() {
    }

    @Override
    public Boolean mock(MockSrc src, MockDataCreator.Creator creator) {
        return RandomUtil.nextBoolean();
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(Boolean.class);
        list.add(boolean.class);
        return list;
    }
}
