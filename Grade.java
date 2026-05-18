
package university;

public class Grade {
    private String letter;
    private double gradePoint;

    public Grade(String letter, double gradePoint) {
        this.letter = letter;
        this.gradePoint = gradePoint;
    }

    public String getLetter() { return letter; }
    public double getGradePoint() { return gradePoint; }

    @Override
    public String toString() {
        return letter + " (GP=" + gradePoint + ")";
    }
}
