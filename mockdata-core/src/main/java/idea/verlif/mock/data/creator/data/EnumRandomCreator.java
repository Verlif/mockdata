package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.mock.data.util.RandomUtil;

/**
 * @author Verlif
 */
public class EnumRandomCreator implements DataCreator<Enum<?>> {

    public EnumRandomCreator() {
    }

    @Override
    public Enum<?> mock(MockSrc src, MockDataCreator.Creator creator) {
        Class<Enum<?>> type = (Class<Enum<?>>) src.getClassGrc().getTarget();
        Enum<?>[] enums = type.getEnumConstants();
        if (enums.length == 0) {
            return null;
        } else {
            return RandomUtil.next(enums);
        }
    }

}
