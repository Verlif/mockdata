package idea.verlif.mock.data.domain;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class MockSrc {

    /**
     * 构建类型
     */
    private final Type type;

    /**
     * 构建源类
     */
    private final Class<?> rawClass;

    /**
     * 构建的目标属性，可能为空
     */
    private final Field field;

    /**
     * 构建的源对象，可能为空
     */
    private final Object oldObj;

    public MockSrc(Class<?> cla) {
        this(cla, null);
    }

    public MockSrc(Type type, Object oldObj) {
        this(type, null, oldObj);
    }

    public MockSrc(Field field, Object oldObj) {
        this(field.getGenericType(), field, oldObj);
    }

    public MockSrc(Type type, Field field, Object oldObj) {
        this.type = type;
        if (oldObj == null) {
            if (type instanceof ParameterizedType) {
                this.rawClass = (Class<?>) ((ParameterizedType) type).getRawType();
            } else {
                this.rawClass = (Class<?>) type;
            }
        } else {
            this.rawClass = oldObj.getClass();
        }
        this.field = field;
        this.oldObj = oldObj;
    }

    public Type getType() {
        return type;
    }

    public Class<?> getRawClass() {
        return rawClass;
    }

    public Field getField() {
        return field;
    }

    public Object getOldObj() {
        return oldObj;
    }
}
