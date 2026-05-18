package university;

import java.util.*;

public class Transcript {
    private Student student;
    private List<TranscriptEntry> entries = new ArrayList<>();

    public Transcript(Student student) {
        this.student = student;
    }

    public void addEntry(TranscriptEntry e) {
        entries.add(e);
    }

    public double computeGPA() {
        double totalPoints = 0.0;
        int totalCredits = 0;
        for (TranscriptEntry e : entries) {
            int cr = e.getCourse().getCredits();
            totalPoints += e.getGrade().getGradePoint() * cr;
            totalCredits += cr;
        }
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }

    public void printTranscript() {
        System.out.println("Transcript for " + student.getName() + " (" + student.getStudentId() + ")");
        System.out.println("----------------------------------------------------");
        System.out.printf("%-10s %-30s %-8s %-6s%n", "Code","Course Title","%","Grade");
        for (TranscriptEntry e : entries) {
            System.out.printf("%-10s %-30s %-7.2f %-6s%n",
                    e.getCourse().getCourseCode(), e.getCourse().getTitle(), e.getTotalPercent(), e.getGrade().getLetter());
        }
        System.out.println("----------------------------------------------------");
        System.out.printf("GPA: %.3f%n", computeGPA());
    }
}
