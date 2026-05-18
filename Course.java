
package university;

import java.util.*;
public class Course {
    private final String courseCode;
    private String title;
    private int credits;
    private List<Assessment> assessments = new ArrayList<>();
    private Set<Student> enrolled = new HashSet<>();

    public Course(String courseCode, String title, int credits) {
        this.courseCode = courseCode;
        this.title = title;
        this.credits = credits;
    }

    public String getCourseCode() { return courseCode; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public List<Assessment> getAssessments() { return assessments; }
    public Set<Student> getEnrolled() { return enrolled; }

    public void enrollStudent(Student s) { enrolled.add(s); }
    public void addAssessment(Assessment a) { assessments.add(a); }

    @Override
    public String toString() {
        return courseCode + " - " + title + " (" + credits + " cr)";
    }
}

