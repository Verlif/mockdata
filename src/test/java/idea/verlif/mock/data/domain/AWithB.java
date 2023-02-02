package idea.verlif.mock.data.domain;

import java.util.Date;

/**
 * @author Verlif
 */
public class AWithB {

    private BWithA b;

    private String name;

    public BWithA getB() {
        return b;
    }

    public void setB(BWithA b) {
        this.b = b;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date now() {
        return new Date();
    }

    @Override
    public String toString() {
        return "A{" +
                "b=" + b +
                ", name='" + name + '\'' +
                '}';
    }
}
