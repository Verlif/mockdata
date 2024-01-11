package idea.verlif.mock.data.domain;

import java.util.Date;

public class Person {

    private String name;

    private String nickname;

    private int age;

    private int nominalAge;

    private double weight;

    private double height;

    private Date birthday;

    private FRUIT favouriteFruit;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNominalAge() {
        return nominalAge;
    }

    public void setNominalAge(int nominalAge) {
        this.nominalAge = nominalAge;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public FRUIT getFavouriteFruit() {
        return favouriteFruit;
    }

    public void setFavouriteFruit(FRUIT favouriteFruit) {
        this.favouriteFruit = favouriteFruit;
    }

    public enum FRUIT {
        APPLE("苹果"),
        ORANGE("橙子"),
        BANANA("香蕉"),
        ;

        private final String name;

        FRUIT(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
