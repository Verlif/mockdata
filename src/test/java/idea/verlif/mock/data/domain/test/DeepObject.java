package idea.verlif.mock.data.domain.test;

import com.alibaba.fastjson2.JSONObject;
import idea.verlif.mock.data.domain.Person;

public class DeepObject {

    private int si;

    private Integer sI;

    private byte sb;

    private Byte sB;

    private boolean sbo;

    private Boolean sBo;

    private short ss;

    private Short sS;

    private long sl;

    private Long sL;

    private float sf;

    private Float sF;

    private double sd;

    private Double sD;

    private char sc;

    private Character sC;

    private String stringT;

    private DeepObject[] deepObject;

    public DeepObject[] getDeepObject() {
        return deepObject;
    }

    public void setDeepObject(DeepObject[] deepObject) {
        this.deepObject = deepObject;
    }

    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    private int[] sia;

    private Integer[] sIa;

    private byte[] sba;

    private Byte[] sBa;

    private boolean[] sboa;

    private Boolean[] sBoa;

    private short[] ssa;

    private Short[] sSa;

    private long[] sla;

    private Long[] sLa;

    private float[] sfa;

    private Float[] sFa;

    private double[] sda;

    private Double[] sDa;

    private char[] sca;

    private Character[] sCa;

    private String[] stringTa;

    public int getSi() {
        return si;
    }

    public void setSi(int si) {
        this.si = si;
    }

    public Integer getsI() {
        return sI;
    }

    public void setsI(Integer sI) {
        this.sI = sI;
    }

    public byte getSb() {
        return sb;
    }

    public void setSb(byte sb) {
        this.sb = sb;
    }

    public Byte getsB() {
        return sB;
    }

    public void setsB(Byte sB) {
        this.sB = sB;
    }

    public boolean isSbo() {
        return sbo;
    }

    public void setSbo(boolean sbo) {
        this.sbo = sbo;
    }

    public Boolean getsBo() {
        return sBo;
    }

    public void setsBo(Boolean sBo) {
        this.sBo = sBo;
    }

    public short getSs() {
        return ss;
    }

    public void setSs(short ss) {
        this.ss = ss;
    }

    public Short getsS() {
        return sS;
    }

    public void setsS(Short sS) {
        this.sS = sS;
    }

    public long getSl() {
        return sl;
    }

    public void setSl(long sl) {
        this.sl = sl;
    }

    public Long getsL() {
        return sL;
    }

    public void setsL(Long sL) {
        this.sL = sL;
    }

    public float getSf() {
        return sf;
    }

    public void setSf(float sf) {
        this.sf = sf;
    }

    public Float getsF() {
        return sF;
    }

    public void setsF(Float sF) {
        this.sF = sF;
    }

    public double getSd() {
        return sd;
    }

    public void setSd(double sd) {
        this.sd = sd;
    }

    public Double getsD() {
        return sD;
    }

    public void setsD(Double sD) {
        this.sD = sD;
    }

    public char getSc() {
        return sc;
    }

    public void setSc(char sc) {
        this.sc = sc;
    }

    public Character getsC() {
        return sC;
    }

    public void setsC(Character sC) {
        this.sC = sC;
    }

    public String getStringT() {
        return stringT;
    }

    public void setStringT(String stringT) {
        this.stringT = stringT;
    }

    public int[] getSia() {
        return sia;
    }

    public void setSia(int[] sia) {
        this.sia = sia;
    }

    public Integer[] getsIa() {
        return sIa;
    }

    public void setsIa(Integer[] sIa) {
        this.sIa = sIa;
    }

    public byte[] getSba() {
        return sba;
    }

    public void setSba(byte[] sba) {
        this.sba = sba;
    }

    public Byte[] getsBa() {
        return sBa;
    }

    public void setsBa(Byte[] sBa) {
        this.sBa = sBa;
    }

    public boolean[] getSboa() {
        return sboa;
    }

    public void setSboa(boolean[] sboa) {
        this.sboa = sboa;
    }

    public Boolean[] getsBoa() {
        return sBoa;
    }

    public void setsBoa(Boolean[] sBoa) {
        this.sBoa = sBoa;
    }

    public short[] getSsa() {
        return ssa;
    }

    public void setSsa(short[] ssa) {
        this.ssa = ssa;
    }

    public Short[] getsSa() {
        return sSa;
    }

    public void setsSa(Short[] sSa) {
        this.sSa = sSa;
    }

    public long[] getSla() {
        return sla;
    }

    public void setSla(long[] sla) {
        this.sla = sla;
    }

    public Long[] getsLa() {
        return sLa;
    }

    public void setsLa(Long[] sLa) {
        this.sLa = sLa;
    }

    public float[] getSfa() {
        return sfa;
    }

    public void setSfa(float[] sfa) {
        this.sfa = sfa;
    }

    public Float[] getsFa() {
        return sFa;
    }

    public void setsFa(Float[] sFa) {
        this.sFa = sFa;
    }

    public double[] getSda() {
        return sda;
    }

    public void setSda(double[] sda) {
        this.sda = sda;
    }

    public Double[] getsDa() {
        return sDa;
    }

    public void setsDa(Double[] sDa) {
        this.sDa = sDa;
    }

    public char[] getSca() {
        return sca;
    }

    public void setSca(char[] sca) {
        this.sca = sca;
    }

    public Character[] getsCa() {
        return sCa;
    }

    public void setsCa(Character[] sCa) {
        this.sCa = sCa;
    }

    public String[] getStringTa() {
        return stringTa;
    }

    public void setStringTa(String[] stringTa) {
        this.stringTa = stringTa;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
