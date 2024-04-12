package idea.verlif.mock.data.pool.random;

import idea.verlif.mock.data.pool.DataRandom;
import idea.verlif.mock.data.pool.util.RandomUtil;

/**
 * 国外常见名随机器
 */
public class EnglishSecondNameRandom implements DataRandom<String> {

    private static final EnglishSecondNameRandom RANDOM;
    static {
        RANDOM = new EnglishSecondNameRandom();
    }

    public static EnglishSecondNameRandom getInstance() {
        return RANDOM;
    }

    private static final String[] LAST = new String[]{
            "Michael", "James", "John", "Robert", "William",
            "David", "Richard", "Charles", "Joseph", "Thomas",
            "Christopher", "Daniel", "Paul", "Mark", "Donald",
            "George", "Kenneth", "Steven", "Edward", "Brian",
            "Ronald", "Anthony", "Kevin", "Jason", "Matthew",
            "Gary", "Timothy", "Jose", "Andrew", "Adam",
            "Joshua", "Ryan", "Jacob", "Patrick", "Nicholas",
            "Ethan", "Christopher", "Alexander", "Samuel"
    };

    @Override
    public String next() {
        return RandomUtil.next(LAST);
    }
}
