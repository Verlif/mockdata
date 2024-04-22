package idea.verlif.mock.data.creator;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.config.SizeCreator;
import idea.verlif.mock.data.config.creator.StaticSizeCreator;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.reflection.domain.ClassGrc;
import idea.verlif.reflection.util.ReflectUtil;

import java.lang.reflect.Type;
import java.util.List;

public abstract class GenericDataCreator<T> implements DataCreator<T> {

    protected SizeCreator sizeCreator;
    protected Class<?> target;

    public GenericDataCreator() {
    }

    public GenericDataCreator(int size) {
        this.sizeCreator = new StaticSizeCreator(size);
    }

    public GenericDataCreator(SizeCreator sizeCreator) {
        this.sizeCreator = sizeCreator;
    }

    @Override
    public T mock(MockSrc src, MockDataCreator.Creator creator) {
        this.target = collectTarget(src);
        if (this.sizeCreator == null) {
            this.sizeCreator = creator.getMockConfig().getArraySizeCreator();
        }
        return mock(target, getRealGenerics(src.getClassGrc()), creator);
    }

    protected int getSize(MockDataCreator.Creator creator) {
        return sizeCreator.getSize(this.target);
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
