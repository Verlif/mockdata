package idea.verlif.mock.data.creator;

import idea.verlif.mock.data.MockDataCreator;

/**
 * 实例构造器
 *
 * @author Verlif
 */
public interface InstanceCreator<T> {

    Class<?> matched();

    T newInstance(MockDataCreator creator);
}
