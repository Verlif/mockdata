package idea.verlif.mock.data.domain;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.annotation.MockData;
import idea.verlif.mock.data.util.NamingUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author Verlif
 */
public class MockField {

    /**
     * 属性的mock注解
     */
    private final MockData mock;

    /**
     * 属性对象
     */
    private final Field field;

    /**
     * 属性的key值
     */
    private final String key;

    public MockField(Field field) {
        this.field = field;
        this.mock = field.getAnnotation(MockData.class);

        if (mock != null) {
            String val = mock.value();
            if (val.length() > 0) {
                key = val;
                return;
            }
        }
        key = NamingUtil.getKeyName(field);
    }

    public MockData getMock() {
        return mock;
    }

    public Field getField() {
        return field;
    }

    public String getKey() {
        return key;
    }
}
