package idea.verlif.mock.data.random;

import idea.verlif.mock.data.util.RandomUtil;

/**
 * 国外常见姓氏随机器
 */
public class EnglishFirstNameRandom implements DataRandom<String> {

    private static final EnglishFirstNameRandom RANDOM;
    static {
        RANDOM = new EnglishFirstNameRandom();
    }

    public static EnglishFirstNameRandom getInstance() {
        return RANDOM;
    }

    public static final String[] FIRST = new String[]{
            "Smith", "Johnson", "Williams", "Brown", "Jones",
            "Miller", "Davis", "Wilson", "Anderson", "Taylor",
            "Thomas", "Jackson", "White", "Harris", "Martin",
            "Thompson", "Garcia", "Martinez", "Robinson", "Clark",
            "Rodriguez", "Lewis", "Lee", "Walker", "Hall",
            "Allen", "Young", "King", "Wright", "Nelson"
    };

    @Override
    public String next() {
        return RandomUtil.next(FIRST);
    }
}
