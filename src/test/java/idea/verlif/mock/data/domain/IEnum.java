package idea.verlif.mock.data.domain;

/**
 * @author Verlif
 */
public enum IEnum {
    HELLO("hello"),
    WORLD("world"),
    ;

    private final String str;

    IEnum(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    @Override
    public String toString() {
        return str;
    }
}
