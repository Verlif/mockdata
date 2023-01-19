package idea.verlif.mock.data.domain;

import idea.verlif.mock.data.util.NamingUtil;

import java.lang.reflect.Field;

/**
 * @author Verlif
 */
public class MockField {

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
        this.key = NamingUtil.getKeyName(field);
    }

    public Field getField() {
        return field;
    }

    public String getKey() {
        return key;
    }
}
