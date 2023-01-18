package idea.verlif.mock.data.domain;

import idea.verlif.mock.data.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Verlif
 */
public class MockObject {

    private final Class<?> target;
    private final List<MockField> fields;

    public MockObject(Class<?> cla) {
        this.target = cla;
        List<Field> allFields = ReflectUtil.getAllFields(cla);
        this.fields = new ArrayList<>(allFields.size());

        for (Field field : allFields) {
            fields.add(new MockField(field));
        }
    }

}
