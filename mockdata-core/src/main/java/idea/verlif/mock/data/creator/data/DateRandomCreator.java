package idea.verlif.mock.data.creator.data;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.creator.DataCreator;
import idea.verlif.mock.data.domain.MockSrc;
import idea.verlif.mock.data.util.RandomUtil;

import java.util.Date;

public class DateRandomCreator implements DataCreator<Date> {

    private final long start;

    private final long end;

    public DateRandomCreator() {
        this(null, null);
    }

    public DateRandomCreator(Date start, Date end) {
        if (start == null) {
            this.start = 0;
        } else {
            this.start = start.getTime();
        }
        if (end == null) {
            this.end = new Date().getTime() + 31536000000L;
        } else {
            this.end = end.getTime();
        }
    }

    @Override
    public Date mock(MockSrc src, MockDataCreator.Creator creator) {
        long offset = end - start;
        long ran = (long) (RandomUtil.nextDouble() * offset + start);
        return new Date(ran);
    }

}
