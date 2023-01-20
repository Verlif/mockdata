package idea.verlif.mock.data.domain;

import com.alibaba.fastjson2.JSONObject;

import java.util.Date;
import java.util.List;
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

    private Map<String, Long> personMap;

    private String[][] strings;

    private List<A> aList;

    private PersonInner inner;

    public Person() {
        this(null);
    }

    public Person(String name) {
        this.name = name;
        this.birthday = new Date();
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

    public Map<String, Long> getPersonMap() {
        return personMap;
    }

    public void setPersonMap(Map<String, Long> personMap) {
        this.personMap = personMap;
    }

    public String[][] getStrings() {
        return strings;
    }

    public void setStrings(String[][] strings) {
        this.strings = strings;
    }

    public List<A> getAList() {
        return aList;
    }

    public void setAList(List<A> aList) {
        this.aList = aList;
    }

    public List<A> getaList() {
        return aList;
    }

    public void setaList(List<A> aList) {
        this.aList = aList;
    }

    public PersonInner getInner() {
        return inner;
    }

    public void setInner(PersonInner inner) {
        this.inner = inner;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public static class PersonInner {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
