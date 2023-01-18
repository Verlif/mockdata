package idea.verlif.mock.data.creator;

import idea.verlif.mock.data.domain.TypeGetter;

/**
 * 实例构造器
 *
 * @author Verlif
 */
public interface InstanceCreator<T> extends TypeGetter<T> {

    T newInstance();
}
