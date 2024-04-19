package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.GenericDataFiller;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.reflection.domain.ClassGrc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Verlif
 */
public class MapCreator extends GenericDataFiller<Map<?, ?>> {

    @Override
    protected Class<?> collectTarget(MockSrc src) {
        Class<?> target = src.getClassGrc().getTarget();
        if (target == Map.class) {
            target = HashMap.class;
        }
        return target;
    }

    @Override
    protected Map<?, ?> mock(Class<?> target, ClassGrc[] generics, MockDataCreator.Creator creator) {
        int size = getSize(creator);
        Map<Object, Object> map = newInstance(target, creator);
        if (generics.length > 1) {
            for (int i = 0; i < size; i++) {
                Object key = creator.mockClass(generics[0]);
                Object value = creator.mockClass(generics[1]);
                map.put(key, value);
            }
        }
        return map;
    }

    public Map<Object, Object> newInstance(Class<?> cla, MockDataCreator.Creator creator) {
        return (Map<Object, Object>) creator.newInstance(cla);
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(Map.class);
        list.add(HashMap.class);
        return list;
    }
}
