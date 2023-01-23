package idea.verlif.mock.data.domain;

import idea.verlif.mock.data.domain.test.A;

/**
 * @author Verlif
 */
public class B {

    private A a;

    private String name;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "B{" +
                "a=" + a +
                ", name='" + name + '\'' +
                '}';
    }
}
