package idea.verlif.mock.data.domain;

/**
 * @author Verlif
 */
public class BWithA {

    private AWithB a;

    private String name;

    public AWithB getA() {
        return a;
    }

    public void setA(AWithB a) {
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
