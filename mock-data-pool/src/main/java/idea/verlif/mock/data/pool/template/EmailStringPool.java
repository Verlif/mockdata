package idea.verlif.mock.data.pool.template;

import idea.verlif.mock.data.pool.SimplePool;
import idea.verlif.mock.data.pool.random.EnglishFirstNameRandom;
import idea.verlif.mock.data.pool.util.RandomUtil;
import idea.verlif.reflection.domain.ClassGrc;

/**
 * 电子邮箱池
 */
public class EmailStringPool implements SimplePool {

    private static final String EMAIL_SUFFIX = ".com";
    private final String[] servers;
    private final EnglishFirstNameRandom englishFirstNameRandom;

    public EmailStringPool() {
        this(new String[]{"mock.com", "fake.com", "virtual.com"});
    }

    public EmailStringPool(String[] servers) {
        this.servers = servers;
        this.englishFirstNameRandom = EnglishFirstNameRandom.getInstance();
    }

    public String mock() {
        int length = RandomUtil.range(4, 16);
        StringBuilder stb = new StringBuilder();
        stb.append(englishFirstNameRandom.next().toLowerCase());
        String server = RandomUtil.next(servers);
        stb.append("@").append(server);
        if (!server.endsWith(EMAIL_SUFFIX)) {
            stb.append(EMAIL_SUFFIX);
        }
        return stb.toString();
    }

    @Override
    public Object fetch(ClassGrc classGrc, String key) {
        return mock();
    }
}
