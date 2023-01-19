package idea.verlif.mock.data.domain;

import java.util.Date;

/**
 * @author Verlif
 */
public class Person {

    private Long id;

    private String name;

    private Date birthday;

    private Integer age;

    private Person firstChild;

    private Person secondChild;

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

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", age=" + age +
                ", firstChild=" + firstChild +
                ", secondChild=" + secondChild +
                '}';
    }
}
