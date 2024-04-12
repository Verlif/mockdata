package idea.verlif.mock.data.creator;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.reflection.domain.ClassGrc;
import idea.verlif.reflection.util.ReflectUtil;

import java.lang.reflect.Type;
import java.util.List;

public abstract class GenericDataFiller<T> implements DataCreator<T> {

    protected Integer size;
    protected Class<?> target;

    public GenericDataFiller() {
    }

    public GenericDataFiller(int size) {
        this.size = size;
    }

    @Override
    public T mock(MockSrc src, MockDataCreator.Creator creator) {
        this.target = collectTarget(src);
        if (this.size == null) {
            this.size = creator.getMockConfig().getArraySize(this.target);
        }
        return mock(target, getRealGenerics(src.getClassGrc()), creator);
    }

    protected abstract Class<?> collectTarget(MockSrc src);

    protected abstract T mock(Class<?> target, ClassGrc[] generics, MockDataCreator.Creator creator);

    protected ClassGrc[] getRealGenerics(ClassGrc thisGrc) {
        Type type;
        while (thisGrc.getGenericsInfos().length == 0) {
            type = target.getGenericSuperclass();
            if (target == List.class) {
                break;
            }
            target = target.getSuperclass();
            thisGrc = ReflectUtil.getClassGrc(type);
        }
        return thisGrc.getGenericsInfos();
    }

}
