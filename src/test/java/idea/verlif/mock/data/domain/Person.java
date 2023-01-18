package idea.verlif.mock.data.domain;

import idea.verlif.mock.data.annotation.MockData;

import java.util.Date;

/**
 * @author Verlif
 */
public class Person {

    private Long id;

    @MockData
    private String name;

    private Date birthday;

    private Person mother;

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

    public Person getMother() {
        return mother;
    }

    public void setMother(Person mother) {
        this.mother = mother;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", mother=" + mother +
                '}';
    }
}
