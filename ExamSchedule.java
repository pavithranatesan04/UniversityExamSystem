
package university;
import java.time.LocalDate;
public class ExamSchedule {
    private Course course;
    private Assessment assessment;
    private LocalDate date;
    public ExamSchedule(Course course, Assessment assessment, LocalDate date) {
        this.course = course;
        this.assessment = assessment;
        this.date = date;
    }

    public Course getCourse() { return course; }
    public Assessment getAssessment() { return assessment; }
    public LocalDate getDate() { return date; }
    @Override
    public String toString() {
        return course.getCourseCode() + " - " + assessment.getName() + " on " + date;
    }
}
