# 代码演示

以下代码使用的Person类：

```java
package idea.verlif.mock.data.domain;

import java.util.Date;
import java.util.Map;

/**
 * @author Verlif
 */
public class Person {
    private final Integer finalInt = 123;
    protected Integer protectInt = 456;
    public Integer publicInt = 789;
    private static Integer staticInt = 0;
    private Long id;
    private String name;
    private Date birthday;
    private Integer age;
    private IEnum iEnum;
    private Person firstChild;
    private Person secondChild;
    private Map<String, Integer> personMap;
    
    public Person() {}
    
    public Person(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public IEnum getiEnum() {
        return iEnum;
    }

    public void setiEnum(IEnum iEnum) {
        this.iEnum = iEnum;
    }

    public Person getFirstChild() {
        return firstChild;
    }

    public void setFirstChild(Person firstChild) {
        this.firstChild = firstChild;
    }

    public Person getSecondChild() {
        return secondChild;
    }

    public void setSecondChild(Person secondChild) {
        this.secondChild = secondChild;
    }

    public Integer getFinalInt() {
        return finalInt;
    }

    public Integer getProtectInt() {
        return protectInt;
    }

    public void setProtectInt(Integer protectInt) {
        this.protectInt = protectInt;
    }

    public Integer getPublicInt() {
        return publicInt;
    }

    public void setPublicInt(Integer publicInt) {
        this.publicInt = publicInt;
    }

    public static Integer getStaticInt() {
        return staticInt;
    }

    public static void setStaticInt(Integer staticInt) {
        Person.staticInt = staticInt;
    }

    public Map<String, Integer> getPersonMap() {
        return personMap;
    }

    public void setPersonMap(Map<String, Integer> personMap) {
        this.personMap = personMap;
    }

    @Override
    public String toString() {
        return "Person{" +
                "finalInt=" + finalInt +
                ", protectInt=" + protectInt +
                ", publicInt=" + publicInt +
                ", staticInt=" + staticInt +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", age=" + age +
                ", iEnum=" + iEnum +
                ", firstChild=" + firstChild +
                ", secondChild=" + secondChild +
                ", personMap=" + personMap +
                '}';
    }
}
```

## 创建构造器

```java
// 创建数据构造器
MockDataCreator creator = new MockDataCreator();
// 使用基础数据包
creator.useBaseData();
// 使用拓展包
creator.useExtendData();
// 获取构造器的当前配置
MockDataConfig config = creator.getConfig();
// 也可以通过新建配置的方式进行配置切换
// MockDataConfig config = new MockDataConfig();
// creator.setConfig(config);
// 添加需要级联构造的类，若不添加，则只会构建Person实例，而不会对其内部属性进行构建
config.addCascadeCreateKey(Person.class);
```

## 使用默认构造器进行构造

### 代码

```java
// 构造测试
for (int i = 0; i < 10; i++) {
    System.out.println(creator.mock(Person.class));
}
```

输出结果：

```text
Person{finalInt=123, protectInt=-1537190024, publicInt=1297946797, staticInt=0, id=6506374247516049407, name='@xM{', birthday=Tue Apr 14 11:10:06 CST 2009, age=1551835002, iEnum=hello, firstChild=Person{finalInt=123, protectInt=-407538349, publicInt=2099388274, staticInt=0, id=2121863675078680575, name='myY', birthday=Mon Apr 17 11:15:07 CDT 1989, age=-1936875662, iEnum=world, firstChild=null, secondChild=Person{finalInt=123, protectInt=68210937, publicInt=-1082617833, staticInt=0, id=-584956961895443457, name='_W', birthday=Fri Mar 05 15:40:07 CST 1999, age=-1034915446, iEnum=hello, firstChild=null, secondChild=null, personMap={=168522043, }]=2022094207, u=-2006117496, yo=-934393121, ATZ:AAm]=-214088338}}, personMap={=-2038104722, ]D-W_@=-868023826, qQk=1420905458, @*DOT}n@S=-1973000300}}, secondChild=null, personMap={Re.M$=-982873932, crhaBfj=-729314459, jS=-514358281, kFebLRU=-1875320465, M=162543407}}
Person{finalInt=123, protectInt=-2138292871, publicInt=591866834, staticInt=0, id=2360160602792235007, name='DnYY', birthday=Wed Feb 19 21:39:09 CST 1975, age=1905772255, iEnum=world, firstChild=Person{finalInt=123, protectInt=-238987656, publicInt=105594521, staticInt=0, id=6350819392421423103, name='DS:', birthday=Sun Nov 14 10:00:29 CST 1971, age=478953784, iEnum=world, firstChild=null, secondChild=Person{finalInt=123, protectInt=503196964, publicInt=-1385727908, staticInt=0, id=8550294381139647487, name='cE%Lqr!@', birthday=Sun Jul 11 02:13:06 CST 1976, age=363307169, iEnum=hello, firstChild=null, secondChild=null, personMap={{(PSo+K=191097772, bkJY=1366096337, Nvv-=1730565371, cG#nD]VJ=-1095973061, KO.i=1979881500}}, personMap={}*[i=691754158, MeScRI=-2049915817, xz$Yys)=1651510731, z.N=-1627731366, CZkJwCE=-560450736}}, secondChild=null, personMap={i!hIlDfJ=-461440183, amlXnt=984743040, +r)Dz=204759060, qjS=-1139228185, skW=166540744}}
Person{finalInt=123, protectInt=827814877, publicInt=-938117454, staticInt=0, id=-3580061589942242305, name='U)Az', birthday=Thu Dec 15 08:49:14 CST 2005, age=1658846518, iEnum=hello, firstChild=Person{finalInt=123, protectInt=-295738311, publicInt=349614269, staticInt=0, id=5051390454865972223, name='F', birthday=Tue Jul 02 20:35:18 CST 2013, age=-188402970, iEnum=hello, firstChild=null, secondChild=Person{finalInt=123, protectInt=238453224, publicInt=93215554, staticInt=0, id=-6509145745505723393, name=')qKf', birthday=Sat Jul 29 13:10:06 CST 2006, age=-1504999864, iEnum=world, firstChild=null, secondChild=null, personMap={NT%JPIsef=-1772360466, *D=-658619212, :=55721008, jh@ftQ@q=-1565195435, .$PU%$g*_=925280853}}, personMap={=548247752, fF=-54087787, ad+v=251709159, jWH=-101313809, &zCoc=404806078}}, secondChild=null, personMap={=-881396384, Xk=182462295, *]=1388672516, T&JS%=755820126, [=-103311250}}
Person{finalInt=123, protectInt=-1284825761, publicInt=-1992129867, staticInt=0, id=-2813315124991932417, name='S}xK[', birthday=Mon Jan 08 06:05:46 CST 1973, age=-1910949931, iEnum=world, firstChild=Person{finalInt=123, protectInt=1776440043, publicInt=-1509932194, staticInt=0, id=-1971408079969648641, name=']bffO)', birthday=Sat Feb 05 17:45:56 CST 2022, age=-1478596746, iEnum=world, firstChild=null, secondChild=Person{finalInt=123, protectInt=-883773603, publicInt=-652638079, staticInt=0, id=-6995340001629336577, name='_Y}ABC', birthday=Mon Jun 07 01:10:20 CST 2010, age=467920679, iEnum=hello, firstChild=null, secondChild=null, personMap={=-1145840599, ZK#MMzJ*=901137915, -Lq=-130092427, BXOC=-257534724, *X[=1436747633}}, personMap={=-776363840, xrF]HC=1506802717, *jlcV&=1591355842, :eCOr*L=878358543, +j&lGBa=-990217796}}, secondChild=null, personMap={=-630035631, A!K:zs=-641520251, wZ@=1182482260, ]#DE@jYTx=1171292707, HU}RCpFqj=-1166193084}}
Person{finalInt=123, protectInt=1061221117, publicInt=-936087319, staticInt=0, id=-6032716223460228097, name='kTna', birthday=Sat Mar 17 02:02:54 CST 1973, age=-1774169474, iEnum=hello, firstChild=Person{finalInt=123, protectInt=2127531424, publicInt=-970591013, staticInt=0, id=-5774813016030679041, name='([bXB', birthday=Sun Jul 29 00:28:33 CST 2012, age=-913946377, iEnum=world, firstChild=null, secondChild=Person{finalInt=123, protectInt=1537113798, publicInt=2075703849, staticInt=0, id=-2464643821190621185, name='Q#rSp', birthday=Sun Jun 03 21:19:04 CST 1984, age=1874476164, iEnum=world, firstChild=null, secondChild=null, personMap={PAXD$dR=2044492455, nOq=808611130, UQIdcS%=1805558032, xm=-1605416748, !d&mb=-1540487687}}, personMap={e=1385144928, ]@G%pP*Fo=-663290597, LEnaHmSt=1790980014, xYsm=2030303121, [{Ablcj=887199267}}, secondChild=null, personMap={=430045364, p+Ejkl}J=244011858, .mYU.(=-1759862114, mgI=1515695629}}
Person{finalInt=123, protectInt=1648664392, publicInt=955947296, staticInt=0, id=-898296470104547329, name='SrB%+#HWp', birthday=Tue Feb 22 03:39:20 CST 1983, age=256477990, iEnum=world, firstChild=Person{finalInt=123, protectInt=-705458091, publicInt=707657543, staticInt=0, id=-4576736240783951873, name='Aj[!pn.', birthday=Fri Jun 23 02:25:32 CST 1978, age=-2011288371, iEnum=world, firstChild=null, secondChild=Person{finalInt=123, protectInt=1758998002, publicInt=-1517349436, staticInt=0, id=88351997917044735, name='phy_wCZ*[', birthday=Mon Aug 28 05:28:18 CST 1972, age=-927050624, iEnum=world, firstChild=null, secondChild=null, personMap={b):=-369341731, i{N.efs=-2049981142, -r=1864443988, XCDg=-62327607, eW-s=1434554017}}, personMap={P=-777174322, YXL@{MNB=320670322, V&ar*j-=2062688553, JRRF=1041789059, RZu]pM}k*=-1789718133}}, secondChild=null, personMap={MRaR=1352470130, u{oyI@*=2002180357, (K@*Htv&=-1194783, bxCLycA=-1220148485, }a=-81113775}}
Person{finalInt=123, protectInt=2029539313, publicInt=103932149, staticInt=0, id=8087711537004534783, name='', birthday=Mon Sep 05 06:18:39 CST 2016, age=1613197091, iEnum=world, firstChild=Person{finalInt=123, protectInt=-1133910509, publicInt=-1495731760, staticInt=0, id=-5711500246501179393, name='rqE_{L)OY', birthday=Wed Jul 01 14:11:00 CST 1992, age=-961562311, iEnum=world, firstChild=null, secondChild=Person{finalInt=123, protectInt=1106678750, publicInt=-499797961, staticInt=0, id=3076436640057639935, name='', birthday=Mon Dec 18 19:08:17 CST 2017, age=539728872, iEnum=hello, firstChild=null, secondChild=null, personMap={A!+%gmo#H=-918350157, =-2135350484, uOH:uoU=1978535803, [GT=1336336808, -=-3582921}}, personMap={@ogZ*]=-1837163107, W=-1343850158, bNiP&=-1810469305, r.@A=-645445456, aGb+()=1650274951}}, secondChild=null, personMap={YXG-AXy{=-1310975588, B=-2109711744, g*EM[zAkV=-1135016027, }NBs*BMJ=822354444, sI)w@Ji=937432702}}
Person{finalInt=123, protectInt=1372664715, publicInt=635645763, staticInt=0, id=3425361110391135231, name='.!}:w', birthday=Fri Feb 19 19:12:17 CST 1982, age=837513122, iEnum=hello, firstChild=Person{finalInt=123, protectInt=-382026349, publicInt=-1163138843, staticInt=0, id=2654356570843301887, name='s', birthday=Thu Jan 17 08:03:42 CST 1991, age=1051144864, iEnum=world, firstChild=null, secondChild=Person{finalInt=123, protectInt=621924963, publicInt=1317882885, staticInt=0, id=216499630576119807, name='', birthday=Tue Jul 11 13:16:40 CDT 1989, age=713294770, iEnum=hello, firstChild=null, secondChild=null, personMap={@d&]dQe%=2109877978, A}t=-296586190, sstEt=1767903204, upXgvHab=-841060306, XOE=-1515634007}}, personMap={@jY#zyS!=1617982274, -IXsh[%zs=-213565538, *DF[h=1168855187, RZsky=1839801799, [=-1467244462}}, secondChild=null, personMap={vQEHJzzVo=424489421, )RH(G=-878710046, tdh=-605627966, yCqsdk(px=1178102978, AL-T}NM=-118109985}}
Person{finalInt=123, protectInt=564172487, publicInt=-2138433497, staticInt=0, id=-8828339066579656705, name='hf', birthday=Tue Nov 19 08:22:47 CST 1985, age=1660125932, iEnum=world, firstChild=Person{finalInt=123, protectInt=223530063, publicInt=276413290, staticInt=0, id=6613908822894448639, name='G-]q', birthday=Sun Jun 10 22:49:17 CST 1979, age=-577410752, iEnum=hello, firstChild=null, secondChild=Person{finalInt=123, protectInt=-1465765758, publicInt=26145097, staticInt=0, id=-8970132413383707649, name='Ktp', birthday=Tue Jul 26 09:51:13 CST 2016, age=-846642928, iEnum=hello, firstChild=null, secondChild=null, personMap={_Yp=1551763966, G{L=2086821554, j=1286506142, Lue$=870119309, cOh=-893232572}}, personMap={[@TXZ=-2010697220, f=917357222, zDRnp}=-1924448391, w]c=-2122359891, -=-1338876038}}, secondChild=null, personMap={=929784666, c=-1491958196, Sa=430295903, imobK=596074830, t_RXfR=-1706400230}}
Person{finalInt=123, protectInt=1390754934, publicInt=-286532000, staticInt=0, id=-978458841705627649, name='%!g', birthday=Mon Nov 01 19:03:22 CST 2021, age=-1853380459, iEnum=world, firstChild=Person{finalInt=123, protectInt=2111725533, publicInt=-1991685797, staticInt=0, id=8983835791441404927, name='', birthday=Wed Mar 28 09:06:07 CST 2012, age=-521693761, iEnum=hello, firstChild=null, secondChild=Person{finalInt=123, protectInt=725355841, publicInt=124951305, staticInt=0, id=4137862725667552255, name='LTZnbY', birthday=Wed Sep 07 21:17:33 CST 2005, age=-380037073, iEnum=hello, firstChild=null, secondChild=null, personMap={iy=181591232, =1184226519, Wywe=1414231938, z=-1581307793, H_}#*=-731668271}}, personMap={a=1719037342, vq]=-978636459, X+E)DX=-33127, jejRj#=1616499644, EC(X=8363512}}, secondChild=null, personMap={.R=1802430695, WKNp_=154007029, wdav=1200043696, IC=60915219, i!ns.r(n=611643676}}
```

## 进行简单的设置后构造

```java
System.out.println("------>>> 使用字典生成name属性");
config.addFieldCreator(Person::getName, new DictDataCreator<>(new String[]{
        "小明", "小红", "小王", "小赵", "小李", "小周", "小强"
}));
System.out.println("------>>> 限制id属性生成范围");
config.addFieldCreator(Person::getId, new LongRandomCreator(0L, 9999L));
System.out.println("------>>> 限制age属性生成范围");
config.addFieldCreator(Person::getAge, new IntegerRandomCreator(0, 200));
System.out.println("------>>> 忽略birthday属性");
config.addIgnoredField(Person::getBirthday);
System.out.println("------>>> 自定义secondChild属性，使得每个Person都可能有secondChild，并且secondChild由使用者进行手动生成");
config.addFieldCreator(Person::getSecondChild, new DataCreator<Person>() {

    private final Random random = new Random();

    @Override
    public Person mock(Field field, MockDataCreator creator) {
        if (random.nextBoolean()) {
            return new Person("啊哈");
        } else {
            return null;
        }
    }
});
// 开始构造
for (int i = 0; i < 10; i++) {
    System.out.println(creator.mock(Person.class));
}
```

输出

```text
Person{finalInt=123, protectInt=-1049517058, publicInt=179091711, staticInt=0, id=3713, name='小红', birthday=null, age=190, iEnum=world, firstChild=Person{finalInt=123, protectInt=-1516249484, publicInt=1165749014, staticInt=0, id=7067, name='小强', birthday=null, age=164, iEnum=world, firstChild=null, secondChild=Person{finalInt=123, protectInt=456, publicInt=789, staticInt=0, id=null, name='啊哈', birthday=null, age=null, iEnum=null, firstChild=null, secondChild=null, personMap=null}, personMap={=64476895, tfwl=1475699210, kVQ.nA=-1665204218, #E@=1308638795}}, secondChild=null, personMap={=-165743232, *Zm*H=1082133846, %ffTYTxlM=-1046508815, t)f+Pq=1778372504, Dol%sqON=-1385604079}}
Person{finalInt=123, protectInt=-1940525060, publicInt=1720541703, staticInt=0, id=7190, name='小周', birthday=null, age=159, iEnum=hello, firstChild=Person{finalInt=123, protectInt=-2121172760, publicInt=1766430110, staticInt=0, id=6904, name='小周', birthday=null, age=55, iEnum=world, firstChild=null, secondChild=Person{finalInt=123, protectInt=456, publicInt=789, staticInt=0, id=null, name='啊哈', birthday=null, age=null, iEnum=null, firstChild=null, secondChild=null, personMap=null}, personMap={=-1563435001, %lZ{B=1944729855, PcG}u]A=-40318197, }n{=-1852421620, [uqSOpLB=-1251472227}}, secondChild=null, personMap={*(DsXcV=-915402414, dK=-1815566442, Nift=-1095038339, M_!dv-=-227486827, j&+D@m=-669300769}}
Person{finalInt=123, protectInt=1569833109, publicInt=1109941568, staticInt=0, id=2248, name='小李', birthday=null, age=138, iEnum=world, firstChild=Person{finalInt=123, protectInt=-907903459, publicInt=-1703922052, staticInt=0, id=1176, name='小周', birthday=null, age=116, iEnum=world, firstChild=null, secondChild=null, personMap={=-24477049, @d-S=514287311, [=1395896505}}, secondChild=null, personMap={Gy-I+#hZ=-1229853541, POYh=225186109, $)TDdcN=-403978258, .=16820354, gAn[kRNX=-4527329}}
Person{finalInt=123, protectInt=1663263644, publicInt=-988782118, staticInt=0, id=1462, name='小王', birthday=null, age=17, iEnum=hello, firstChild=Person{finalInt=123, protectInt=-559669421, publicInt=-532019393, staticInt=0, id=8563, name='小王', birthday=null, age=193, iEnum=world, firstChild=null, secondChild=Person{finalInt=123, protectInt=456, publicInt=789, staticInt=0, id=null, name='啊哈', birthday=null, age=null, iEnum=null, firstChild=null, secondChild=null, personMap=null}, personMap={!%)e#=-416062608, )qb*n[):D=-952876644, @)=-2131599505, eYcbU=-44642280, {ljO=1537963733}}, secondChild=null, personMap={X=-1514154065, $(EEU_=-763038825, @Edpjh.=-1466319837, Wjq=-1186892109, Z#[%gW:P=-711174714}}
Person{finalInt=123, protectInt=-728723070, publicInt=-189954508, staticInt=0, id=5147, name='小周', birthday=null, age=49, iEnum=hello, firstChild=Person{finalInt=123, protectInt=-671200182, publicInt=275422660, staticInt=0, id=9931, name='小红', birthday=null, age=5, iEnum=world, firstChild=null, secondChild=null, personMap={kz@=-1067456768, }S{U_Tn=-1590486936, (=-1383907389, #uY@$cVN=817503023, ]:IP@Y=1841929449}}, secondChild=null, personMap={=900677426, bga=-1899526864, niakg=-895016168, woNX-*=1822013787}}
Person{finalInt=123, protectInt=-1484538572, publicInt=1617167941, staticInt=0, id=1242, name='小强', birthday=null, age=100, iEnum=world, firstChild=Person{finalInt=123, protectInt=-1980175955, publicInt=-1195078321, staticInt=0, id=9243, name='小强', birthday=null, age=12, iEnum=world, firstChild=null, secondChild=Person{finalInt=123, protectInt=456, publicInt=789, staticInt=0, id=null, name='啊哈', birthday=null, age=null, iEnum=null, firstChild=null, secondChild=null, personMap=null}, personMap={FB-=932349613, ten]n*S=1980567755, TyiBRO(h=-2384778, }..WPt=-1210037428, X=-1107261147}}, secondChild=null, personMap={l@=1252272443, jth#ZBc.=-1230812787, X{)i{s=457907041, )m$ua(pn=1439427217, Uc=1101191017}}
Person{finalInt=123, protectInt=-671697690, publicInt=-239204136, staticInt=0, id=973, name='小李', birthday=null, age=125, iEnum=world, firstChild=Person{finalInt=123, protectInt=-1167166102, publicInt=149215149, staticInt=0, id=6537, name='小红', birthday=null, age=37, iEnum=world, firstChild=null, secondChild=null, personMap={}U[u)by=1031037163, l!=-311389235, kfmF=-1070286550, }KRxMjl=1652473706, KOLlDWC=2138631870}}, secondChild=Person{finalInt=123, protectInt=456, publicInt=789, staticInt=0, id=null, name='啊哈', birthday=null, age=null, iEnum=null, firstChild=null, secondChild=null, personMap=null}, personMap={abHuD=775337280, V+ppMAd$=1729243031, S#NwrKH=67461029, n{%quwt=-1393023070, EhL+ZxKt=-1406710929}}
Person{finalInt=123, protectInt=-2130089447, publicInt=987788565, staticInt=0, id=9456, name='小强', birthday=null, age=136, iEnum=world, firstChild=Person{finalInt=123, protectInt=-1446745379, publicInt=1292289651, staticInt=0, id=3454, name='小王', birthday=null, age=7, iEnum=hello, firstChild=null, secondChild=Person{finalInt=123, protectInt=456, publicInt=789, staticInt=0, id=null, name='啊哈', birthday=null, age=null, iEnum=null, firstChild=null, secondChild=null, personMap=null}, personMap={Im$=-1820891040, &#:!R{VM=-1777741694, _@Pdq=-376164036, GxJvFZ=-1957593809, &lrLzLj=-1680280437}}, secondChild=Person{finalInt=123, protectInt=456, publicInt=789, staticInt=0, id=null, name='啊哈', birthday=null, age=null, iEnum=null, firstChild=null, secondChild=null, personMap=null}, personMap={=585706701, T[tw%ytV!=1171000455, I.=-781970105, .herm%=492299219, ]T]YNSkJs=-97927459}}
Person{finalInt=123, protectInt=-603184301, publicInt=1130008210, staticInt=0, id=7088, name='小明', birthday=null, age=154, iEnum=hello, firstChild=Person{finalInt=123, protectInt=-2126145303, publicInt=83546337, staticInt=0, id=3195, name='小强', birthday=null, age=38, iEnum=hello, firstChild=null, secondChild=null, personMap={t{pf)izd=141351717, =620477958, t=-1322117198, %O%w=2048549020, m=-300400296}}, secondChild=Person{finalInt=123, protectInt=456, publicInt=789, staticInt=0, id=null, name='啊哈', birthday=null, age=null, iEnum=null, firstChild=null, secondChild=null, personMap=null}, personMap={=1477137843, gOCbgd.=1792248189, YpKTvSzLI=-1966757869, L]{vX=-1611806660, Q_zt=-239346553}}
Person{finalInt=123, protectInt=1493566731, publicInt=-872415189, staticInt=0, id=6631, name='小李', birthday=null, age=157, iEnum=world, firstChild=Person{finalInt=123, protectInt=-2140374041, publicInt=1041352895, staticInt=0, id=1431, name='小赵', birthday=null, age=170, iEnum=hello, firstChild=null, secondChild=null, personMap={=-897654602, +GL@=142671105, ZpZY(A=-1597712037, C]ToMTL=1128220163, :neDq=-736117029}}, secondChild=null, personMap={=2146141988, i#{]k=1635835238, GOl-OL$V{=1019855685, {EI=-552590981}}
```

## 使用数组

使用数组有两种方式，一种是使用`Person[][].class`，一种是使用`new Person[][]`

```java
// 使用类的方式进行生成，数组的长度与配置中的arraySize有关
Person[][] array1 = creator.mock(Person[][].class);
// 使用对象的方式进行生成，数组的长度与对象的长度有关，也就是 2 * 5。推荐使用对象方式生成
Person[][] array2 = creator.mock(new Person[2][5]);
```

这里演示的是直接mock数组，如果mock的对象或类包含数组，那么这个数组就是以类的方式进行构建。