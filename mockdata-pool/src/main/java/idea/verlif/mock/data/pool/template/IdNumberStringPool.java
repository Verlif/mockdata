package idea.verlif.mock.data.pool.template;

import idea.verlif.mock.data.pool.SimplePool;
import idea.verlif.mock.data.pool.util.RandomUtil;
import idea.verlif.reflection.domain.ClassGrc;

import java.time.LocalDate;

/**
 * 虚拟身份证号池，这里的区域编号是随机生成的，无法与现实库对应上。
 * 需要此业务的开发者可继承并重写{@link #buildAreaCode(StringBuilder)}方法
 */
public class IdNumberStringPool implements SimplePool {

    // 校验码权重
    private static final int[] WEIGHT_FACTORS = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    // 校验码字符集
    private static final char[] CHECK_CODES = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    public String mock() {
        StringBuilder idStb = new StringBuilder();
        buildAreaCode(idStb);
        buildBirthday(idStb);

        // 生成顺序码（后3位）
        for (int i = 0; i < 3; i++) {
            idStb.append(RandomUtil.next(10));
        }

        // 计算校验码（最后一位）
        int sum = 0;
        for (int i = 0; i < idStb.length() - 1; i++) {
            int digit = idStb.charAt(i) - '0';
            sum += digit * WEIGHT_FACTORS[i];
        }
        int index = sum % 11;
        idStb.append(CHECK_CODES[index]);

        return idStb.toString();
    }

    protected void buildAreaCode(StringBuilder stb) {
        for (int i = 0; i < 6; i++) {
            stb.append(RandomUtil.next(10));
        }
    }

    protected void buildBirthday(StringBuilder stb) {
        LocalDate localDate = LocalDate.now()
                .plusYears(-RandomUtil.next(50))
                .plusMonths(-RandomUtil.next(12))
                .plusDays(-31);
        stb.append(localDate.getYear());
        int monthValue = localDate.getMonthValue();
        if (monthValue < 10) {
            stb.append("0");
        }
        stb.append(monthValue);
        int day = localDate.getDayOfMonth();
        if (day < 10) {
            stb.append("0");
        }
        stb.append(day);
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
