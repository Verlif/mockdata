package idea.verlif.mock.data.creator;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.domain.TypeGetter;

import java.lang.reflect.Field;

/**
 * @author Verlif
 */
public interface DataCreator<R> extends TypeGetter<R> {

    /**
     * 生成数据
     *
     * @param field   属性对象。当此构造器绑定的类是通过{@link MockDataCreator#mock(Class)}的方式传入时，field为空。<br>
     *                例如此构造器绑定Person对象，而此时通过MockDataCreator.mock(Person.class)方式进入此构造器时，field对象为空。但其内部可能存在的Person则不会为空。<br>
     *                也就是说，只有在{@link MockDataCreator#mock(Class)}传入的类与此构造器绑定类相同时，返回的目标对象在构造时传入的field为空。
     * @param cla     当前需要构建的类。
     * @param creator 当前所使用的数据创建器。
     * @return 生成的数据
     */
    R mock(Class<?> cla, Field field, MockDataCreator.Creator creator);
}
