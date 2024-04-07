package idea.verlif.mock.data.creator;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.exception.MockDataException;

import java.lang.reflect.Method;

/**
 * 实例构造器
 *
 * 实例创建器，用于对类进行自定义实例化
 */
public interface InstanceCreator<T> {

    default Class<?> matched() {
        Class<? extends InstanceCreator> cla = this.getClass();
        if (cla.getSimpleName().contains("Lambda")) {
            throw new MockDataException("Function 'matched()' cannot support Lambda, please overwrite it.");
        }
        try {
            Method method = cla.getMethod("newInstance", MockDataCreator.class);
            return method.getReturnType();
        } catch (NoSuchMethodException ignored) {
        }
        throw new MockDataException("Cannot match " + cla.getSimpleName() + ", please overwrite function 'matched()'");
    }

    T newInstance(MockDataCreator creator);
}
