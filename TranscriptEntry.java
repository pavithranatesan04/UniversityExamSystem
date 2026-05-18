package university;

public class TranscriptEntry {
    private Course course;
    private double totalPercent;
    private Grade grade;

    public TranscriptEntry(Course course, double totalPercent, Grade grade) {
        this.course = course;
        this.totalPercent = totalPercent;
        this.grade = grade;
    }

    public Course getCourse() { return course; }
    public double getTotalPercent() { return totalPercent; }
    public Grade getGrade() { return grade; }
}
