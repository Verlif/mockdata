package idea.verlif.mock.data.pool.template;

import idea.verlif.mock.data.pool.SimplePool;
import idea.verlif.mock.data.pool.util.RandomUtil;
import idea.verlif.reflection.domain.ClassGrc;

/**
 * 手机号码池
 */
public class PhoneNumStringPool implements SimplePool {

    private Operator operator = Operator.DIANXIN;

    public void setType(Operator operator) {
        this.operator = operator;
    }

    public String mock() {
        StringBuilder stb = new StringBuilder();
        stb.append(RandomUtil.next(this.operator.getPrefix()));
        for (int i = 11 - stb.length(); i > 0; i--) {
            stb.append(RandomUtil.next(10));
        }
        return stb.toString();
    }

    @Override
    public Object fetch(ClassGrc classGrc, String key) {
        return mock();
    }

    public enum Operator {
        YIDONG(
                "134", "135", "136", "137", "138", "139", "144", "147", "148", "150",
                "151", "152", "157", "158", "159", "165", "1703", "1705", "1706", "172",
                "178", "182", "183", "184", "187", "188", "195", "197", "198"),
        LIANTONG(
                "130", "131", "132", "140", "145", "146", "155", "156", "166", "167",
                "1704", "1707", "1708", "1709，171", "175", "176", "185", "186", "196"),
        DIANXIN(
                "133", "1349", "141", "149", "153", "162", "1700", "1701", "1702", "173",
                "1740", "177", "180", "181", "189", "190", "191", "193", "199");

        private final String[] prefix;

        Operator(String... prefix) {
            this.prefix = prefix;
        }

        public String[] getPrefix() {
            return prefix;
        }
    }
}
