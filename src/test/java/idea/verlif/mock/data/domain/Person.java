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
