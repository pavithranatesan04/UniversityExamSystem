
package university;
import java.util.*;
public class Student {
    private final String studentId;
    private String name;
    private Map<String, Course> registrations = new HashMap<>();
    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }
    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public Map<String, Course> getRegistrations() { return registrations; }
    public void registerCourse(Course c) {
        registrations.put(c.getCourseCode(), c);
        c.enrollStudent(this);
    }
    @Override
    public String toString() {
        return studentId + " - " + name;
    }
}

