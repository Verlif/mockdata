package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Random;

/**
 * @author Verlif
 */
public class EnumRandomCreator implements DataCreator<Enum<?>> {

    private final Random random;

    public EnumRandomCreator() {
        random = new Random();
    }

    @Override
    public Enum<?> mock(Field field, MockDataCreator creator) {
        Class<Enum<?>> type = (Class<Enum<?>>) field.getType();
        Enum<?>[] enums = type.getEnumConstants();
        if (enums.length == 0) {
            return null;
        } else {
            return enums[random.nextInt(enums.length)];
        }
    }

}
