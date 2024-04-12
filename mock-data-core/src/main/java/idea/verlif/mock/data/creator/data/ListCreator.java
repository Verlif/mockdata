package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.GenericDataFiller;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.reflection.domain.ClassGrc;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表构造器
 *
 * @author Verlif
 */
public class ListCreator extends GenericDataFiller<List<?>> {

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(List.class);
        list.add(ArrayList.class);
        return list;
    }

    @Override
    protected Class<?> collectTarget(MockSrc src) {
        Class<?> target = src.getClassGrc().getTarget();
        if (target == List.class) {
            target = ArrayList.class;
        }
        return target;
    }

    @Override
    protected List<?> mock(Class<?> target, ClassGrc[] generics, MockDataCreator.Creator creator) {
        List<Object> list = newInstance(target, creator);
        if (generics.length > 0) {
            for (int i = 0; i < size; i++) {
                Object o = creator.mockClass(generics[0]);
                list.add(o);
            }
        }
        return list;
    }

    public List<Object> newInstance(Class<?> cla, MockDataCreator.Creator creator) {
        return (List<Object>) creator.newInstance(cla);
    }

}
