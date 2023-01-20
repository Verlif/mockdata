package idea.verlif.mock.data.domain;

public class Student extends Person {

    public Student() {
        super();
    }

    public Student(String name) {
        super(name);
    }

    private Double score;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
