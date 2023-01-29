package idea.verlif.mock.data.domain.test;

import idea.verlif.mock.data.domain.Pet;

public class Dog extends Pet {

    public Dog(String name) {
        super(name);
    }

    public Dog(Integer age) {
        super(age + "岁的Dog");
    }

    public Dog() {}
}
