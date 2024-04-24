package idea.verlif.mock.data.virtualpool.template;

import idea.verlif.mock.data.util.RandomUtil;
import idea.verlif.mock.data.virtualpool.SimplePool;
import idea.verlif.reflection.domain.ClassGrc;

/**
 * IP地址池
 */
public class IpStringPool implements SimplePool {

    private TYPE type = TYPE.IPV4;

    public void setType(TYPE type) {
        this.type = type;
    }

    public String mock() {
        StringBuilder stb = new StringBuilder();
        if (type == TYPE.IPV4) {
            for (int i = 0; i < 4; i++) {
                stb.append(RandomUtil.nextInt(255)).append(".");
            }
            return stb.substring(0, stb.length() - 1);
        } else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++) {
                    stb.append(Integer.toHexString(RandomUtil.nextInt(16)));
                }
                stb.append(":");
            }
            return stb.substring(0, stb.length() - 1);
        }
    }

    @Override
    public Object fetch(ClassGrc classGrc, String key) {
        return mock();
    }

    @Override
    public Class<?> type() {
        return String.class;
    }

    public enum TYPE {
        IPV4, IPV6
    }
}
