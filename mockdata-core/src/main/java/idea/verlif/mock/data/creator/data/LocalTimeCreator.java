package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.mock.data.util.RandomUtil;

import java.time.LocalTime;

/**
 * @author Verlif
 */
public class LocalTimeCreator implements DataCreator<LocalTime> {

    public LocalTimeCreator() {
    }

    @Override
    public LocalTime mock(MockSrc src, MockDataCreator.Creator creator) {
        return LocalTime.of(RandomUtil.nextInt(24), RandomUtil.nextInt(60));
    }
}
