package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.GenericDataFiller;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.reflection.domain.ClassGrc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 列表构造器
 *
 * @author Verlif
 */
public class SetCreator extends GenericDataFiller<Set<?>> {

    @Override
    protected Class<?> collectTarget(MockSrc src) {
        Class<?> target = src.getClassGrc().getTarget();
        if (target == Set.class) {
            target = HashSet.class;
        }
        return target;
    }

    @Override
    protected Set<?> mock(Class<?> target, ClassGrc[] generics, MockDataCreator.Creator creator) {
        Set<Object> set = newInstance(target, creator);
        if (generics.length > 0) {
            for (int i = 0; i < size; i++) {
                Object o = creator.mockClass(generics[0]);
                set.add(o);
            }
        }
        return set;
    }

    public Set<Object> newInstance(Class<?> cla, MockDataCreator.Creator creator) {
        return (Set<Object>) creator.newInstance(cla);
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> set = new ArrayList<>();
        set.add(Set.class);
        set.add(HashSet.class);
        return set;
    }
}
