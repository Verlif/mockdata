package idea.verlif.mock.data.pool.random;

import idea.verlif.mock.data.pool.DataRandom;
import idea.verlif.mock.data.pool.util.RandomUtil;

/**
 * 中文常见姓氏随机器
 */
public class ChineseFirstNameRandom implements DataRandom<String> {

    private static final ChineseFirstNameRandom RANDOM;
    static {
        RANDOM = new ChineseFirstNameRandom();
    }

    public static ChineseFirstNameRandom getInstance() {
        return RANDOM;
    }

    private static final String[] FIRST = new String[]{
            "李", "王", "张", "刘", "陈", "杨", "赵", "黄", "周", "吴",
            "徐", "孙", "胡", "朱", "高", "林", "何", "郭", "马", "罗",
            "梁", "宋", "郑", "谢", "韩", "唐", "冯", "于", "董", "萧",
            "程", "曹", "袁", "邓", "许", "傅", "沈", "曾", "彭", "吕",
            "苏", "卢", "蒋", "蔡", "贾", "丁", "魏", "薛", "叶", "阎",
            "余", "潘", "杜", "戴", "夏", "钟", "汪", "田", "任", "姜",
            "范", "方", "石", "姚", "谭", "廖", "邹", "熊", "金", "陆",
            "郝", "孔", "白", "崔", "康", "毛", "邱", "秦", "江", "史",
            "顾", "侯", "邵", "孟", "龙", "万", "段", "雷", "钱", "汤",
            "尹", "黎", "易", "常", "武", "乔", "贺", "赖", "龚", "文",
            "欧阳", "上官", "司马", "东方", "独孤", "皇甫",
            "南宫", "夏侯", "诸葛", "尉迟", "公孙",
    };

    @Override
    public String next() {
        return RandomUtil.next(FIRST);
    }
}
