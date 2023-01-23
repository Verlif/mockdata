package idea.verlif.mock.data.domain.test;

import idea.verlif.mock.data.domain.B;

/**
 * @author Verlif
 */
public class A {

    private B b;

    private String name;

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "A{" +
                "b=" + b +
                ", name='" + name + '\'' +
                '}';
    }
}
