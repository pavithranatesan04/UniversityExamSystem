
package university;

public class MarkEntry {
    private Student student;
    private Course course;
    private Assessment assessment;
    private double marksObtained;
    private boolean isResit;

    public MarkEntry(Student student, Course course, Assessment assessment, double marksObtained, boolean isResit) {
        this.student = student;
        this.course = course;
        this.assessment = assessment;
        this.marksObtained = marksObtained;
        this.isResit = isResit;
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public Assessment getAssessment() { return assessment; }
    public double getMarksObtained() { return marksObtained; }
    public boolean isResit() { return isResit; }
}

