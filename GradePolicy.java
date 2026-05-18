package university;

public class GradePolicy {
    public static Grade gradeFromPercentage(double pct) {
        if (pct >= 85) return new Grade("A", 4.0);
        if (pct >= 70) return new Grade("B", 3.0);
        if (pct >= 50) return new Grade("C", 2.0);
        if (pct >= 40) return new Grade("D", 1.0);
        return new Grade("F", 0.0);
    }
}