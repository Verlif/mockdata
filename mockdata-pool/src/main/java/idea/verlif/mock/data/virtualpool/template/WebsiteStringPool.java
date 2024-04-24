package idea.verlif.mock.data.virtualpool.template;

import idea.verlif.mock.data.random.EnglishSecondNameRandom;
import idea.verlif.mock.data.util.RandomUtil;
import idea.verlif.mock.data.virtualpool.SimplePool;
import idea.verlif.reflection.domain.ClassGrc;

/**
 * 网站地址池
 */
public class WebsiteStringPool implements SimplePool {

    private static final String PREFIX = "";
    private static final char SPLIT = '.';
    private static final String[] SUFFIX = new String[]{"com", "cn", ".io"};
    private final String prefix;
    private final String[] suffix;
    private final EnglishSecondNameRandom englishSecondNameRandom;

    public WebsiteStringPool() {
        this(PREFIX, SUFFIX);
    }

    public WebsiteStringPool(String prefix) {
        this(prefix, SUFFIX);
    }

    public WebsiteStringPool(String[] suffix) {
        this(PREFIX, suffix);
    }

    public WebsiteStringPool(String prefix, String[] suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.englishSecondNameRandom = EnglishSecondNameRandom.getInstance();
    }

    public String mock() {
        StringBuilder stb = new StringBuilder();
        stb.append(englishSecondNameRandom.next().toLowerCase()).append(SPLIT);
        if (RandomUtil.nextBoolean()) {
            stb.insert(0, SPLIT);
            int length = RandomUtil.range(2, 6);
            for (int i = 0; i < length; i++) {
                stb.insert(0, (char) RandomUtil.range('a', 'z'));
            }
        }
        stb.setLength(stb.length() - 1);
        String suffix = RandomUtil.next(this.suffix);
        if (suffix != null && !suffix.isEmpty() && suffix.charAt(0) != SPLIT) {
            stb.append(SPLIT);
        }
        stb.append(suffix).insert(0, prefix);
        return stb.toString();
    }

    @Override
    public Object fetch(ClassGrc classGrc, String key) {
        return mock();
    }

    @Override
    public Class<?> type() {
        return String.class;
    }
}
