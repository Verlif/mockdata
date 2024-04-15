package idea.verlif.mock.data.pool.random;

import idea.verlif.mock.data.pool.DataRandom;
import idea.verlif.mock.data.pool.util.RandomUtil;

public class TypeStringRandom implements DataRandom<String> {

    public static final char[] SYMBOLS = new char[]{
            ',', '.', '/', '<', '>', '\'', '?', '\\', ':', '"',
            '[', ']', '{', '}', '|', '`', '!', '@', '#', '$',
            '%', '^', '&', '*', '(', ')', '_', '-', '=', '+',
            '~'
    };

    public static final char[] SYMBOLS_CHINESE = new char[]{
            '，', '。', '《', '》', '？', '：', '“', '”',
            '【', '】', '|', '·', '！', '￥', '…', '（', '）', '—'
    };

    public static final char[] SPACE = new char[]{' '};

    public static final char[] NUMBER = new char[]{
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
    };

    public static final char[] LETTER = new char[]{
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    };

    public static final char[] LETTER_UPPER = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    /**
     * 生成最小长度
     */
    private final int minLength;
    /**
     * 生成最大长度
     */
    private final int maxLength;
    /**
     * 允许的字符集
     */
    private final char[] allowedChars;

    public TypeStringRandom(int length, CharType... types) {
        this(length, length, types);
    }

    public TypeStringRandom(int minLength, int maxLength, CharType... types) {
        this.minLength = minLength;
        this.maxLength = maxLength;
        int total = 0;
        for (CharType type : types) {
            total += type.getChars().length;
        }
        allowedChars = new char[total];
        int point = 0;
        for (CharType type : types) {
            for (char typeChar : type.getChars()) {
                allowedChars[point++] = typeChar;
            }
        }
    }

    @Override
    public String next() {
        StringBuilder stb = new StringBuilder();
        for (int i = 0, length = RandomUtil.range(minLength, maxLength); i < length; i++) {
            stb.append(allowedChars[RandomUtil.next(allowedChars.length)]);
        }
        return stb.toString();
    }

    public enum CharType {
        SYMBOLS(TypeStringRandom.SYMBOLS),
        SYMBOLS_CHINESE(TypeStringRandom.SYMBOLS_CHINESE),
        SPACE(TypeStringRandom.SPACE),
        NUMBER(TypeStringRandom.NUMBER),
        LETTER(TypeStringRandom.LETTER),
        LETTER_UPPER(TypeStringRandom.LETTER_UPPER),
        ;

        private final char[] chars;

        CharType(char[] chars) {
            this.chars = chars;
        }

        public char[] getChars() {
            return chars;
        }
    }

}
