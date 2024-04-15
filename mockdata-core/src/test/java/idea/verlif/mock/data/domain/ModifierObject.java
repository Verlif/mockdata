package idea.verlif.mock.data.domain;

public class ModifierObject {

    private Integer priInt;

    public Integer pubInt;

    protected Double proDou;

    private static String priStaStr;

    public final Boolean pubFinBoo = true;

    public Integer getPriInt() {
        return priInt;
    }

    public void setPriInt(Integer priInt) {
        this.priInt = priInt;
    }

    public Integer getPubInt() {
        return pubInt;
    }

    public void setPubInt(Integer pubInt) {
        this.pubInt = pubInt;
    }

    public Double getProDou() {
        return proDou;
    }

    public void setProDou(Double proDou) {
        this.proDou = proDou;
    }

    public static String getPriStaStr() {
        return priStaStr;
    }

    public static void setPriStaStr(String priStaStr) {
        ModifierObject.priStaStr = priStaStr;
    }

    public Boolean getPubFinBoo() {
        return pubFinBoo;
    }
}
