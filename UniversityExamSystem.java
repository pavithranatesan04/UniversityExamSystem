package university;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UniversityExamsSystem {

    private static Scanner sc = new Scanner(System.in);
    private Map<String, Course> courses = new HashMap<>();
    private Map<String, Student> students = new HashMap<>();
    private List<ExamSchedule> schedules = new ArrayList<>();
    private List<MarkEntry> marks = new ArrayList<>();

    // ---------------- HELPER METHODS ----------------
    private String readNonEmpty(String prompt) {
        String s;
        while (true) {
            System.out.print(prompt);
            s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Input cannot be empty!");
        }
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v >= min && v <= max) return v;
            } catch (Exception ignored) {}
            System.out.println("Enter integer between " + min + " and " + max);
        }
    }

    private double readDouble(String prompt, double min, double max) {
        while (true) {
            try {
                System.out.print(prompt);
                double v = Double.parseDouble(sc.nextLine().trim());
                if (v >= min && v <= max) return v;
            } catch (Exception ignored) {}
            System.out.println("Enter number between " + min + " and " + max);
        }
    }

    // ---------------- MENU ACTIONS ----------------
    private void addCourse() {
        String code = readNonEmpty("Course Code: ");
        if (courses.containsKey(code)) { System.out.println("Exists!"); return; }
        String title = readNonEmpty("Course Title: ");
        int credits = readInt("Credits (1-10): ", 1, 10);
        Course c = new Course(code, title, credits);
        courses.put(code, c);
        System.out.println("Added course: " + c);
    }

    private void addStudent() {
        String id = readNonEmpty("Student ID: ");
        if (students.containsKey(id)) { System.out.println("Exists!"); return; }
        String name = readNonEmpty("Student Name: ");
        Student s = new Student(id, name);
        students.put(id, s);
        System.out.println("Added student: " + s);
    }

    private void registerCourse() {
        if (courses.isEmpty()) { System.out.println("No courses."); return; }
        String sid = readNonEmpty("Student ID: ");
        Student s = students.get(sid);
        if (s == null) { System.out.println("Student not found."); return; }
        courses.values().forEach(System.out::println);
        String code = readNonEmpty("Course Code: ");
        Course c = courses.get(code);
        if (c == null) { System.out.println("Course not found."); return; }
        s.registerCourse(c);
        System.out.println("Registered " + s.getName() + " in " + c.getTitle());
    }

    private void createAssessment() {
        if (courses.isEmpty()) { System.out.println("No courses."); return; }
        String code = readNonEmpty("Course Code: ");
        Course c = courses.get(code);
        if (c == null) { System.out.println("Not found."); return; }
        String id = readNonEmpty("Assessment ID: ");
        String name = readNonEmpty("Assessment Name: ");
        double weight = readDouble("Weight (0-100): ", 0, 100);
        double max = readDouble("Max Marks: ", 1, 1000);
        double totalWeight = weight;
        for (Assessment a : c.getAssessments()) totalWeight += a.getWeight();
        if (totalWeight > 100) { System.out.println("Exceeds 100%!"); return; }
        c.addAssessment(new Assessment(id, name, weight, max));
        System.out.println("Added assessment.");
    }

    private void scheduleExam() {
        if (courses.isEmpty()) { System.out.println("No courses."); return; }
        String code = readNonEmpty("Course Code: ");
        Course c = courses.get(code);
        if (c == null) { System.out.println("Not found."); return; }
        c.getAssessments().forEach(System.out::println);
        String aid = readNonEmpty("Assessment ID: ");
        Assessment a = c.getAssessments().stream().filter(x -> x.getId().equals(aid)).findFirst().orElse(null);
        if (a == null) { System.out.println("No such assessment."); return; }
        System.out.print("Date (yyyy-MM-dd): ");
        try {
            LocalDate d = LocalDate.parse(sc.nextLine().trim(), DateTimeFormatter.ISO_LOCAL_DATE);
            schedules.add(new ExamSchedule(c, a, d));
            System.out.println("Scheduled " + a.getName() + " on " + d);
        } catch (Exception e) {
            System.out.println("Invalid date.");
        }
    }

    private void recordMarks() {
        String sid = readNonEmpty("Student ID: ");
        Student s = students.get(sid);
        if (s == null) { System.out.println("No student."); return; }
        s.getRegistrations().values().forEach(System.out::println);
        String code = readNonEmpty("Course Code: ");
        Course c = s.getRegistrations().get(code);
        if (c == null) { System.out.println("Not registered!"); return; }
        c.getAssessments().forEach(System.out::println);
        String aid = readNonEmpty("Assessment ID: ");
        Assessment a = c.getAssessments().stream().filter(x -> x.getId().equals(aid)).findFirst().orElse(null);
        if (a == null) { System.out.println("Not found."); return; }
        double m = readDouble("Marks (0 - " + a.getMaxMarks() + "): ", 0, a.getMaxMarks());
        System.out.print("Resit? (y/n): ");
        boolean resit = sc.nextLine().trim().equalsIgnoreCase("y");
        marks.add(new MarkEntry(s, c, a, m, resit));
        System.out.println("Marks recorded.");
    }

    private double getCoursePercent(Course c, Student s) {
        double total = 0;
        for (Assessment a : c.getAssessments()) {
            Optional<MarkEntry> me = marks.stream()
                    .filter(m -> m.getStudent() == s && m.getCourse() == c && m.getAssessment() == a)
                    .max(Comparator.comparing(MarkEntry::isResit));
            if (me.isPresent()) {
                total += (me.get().getMarksObtained() / a.getMaxMarks()) * 100 * (a.getWeight() / 100);
            }
        }
        return total;
    }

    private void publishGrades() {
        for (Course c : courses.values()) {
            System.out.println("\nGrade Sheet for " + c);
            System.out.printf("%-10s %-20s %-10s %-8s%n", "StuID","Name","Percent","Grade");
            for (Student s : c.getEnrolled()) {
                double pct = getCoursePercent(c, s);
                Grade g = GradePolicy.gradeFromPercentage(pct);
                System.out.printf("%-10s %-20s %-9.2f %-8s%n", s.getStudentId(), s.getName(), pct, g.getLetter());
            }
        }
    }

    private void transcript() {
        String sid = readNonEmpty("Student ID: ");
        Student s = students.get(sid);
        if (s == null) { System.out.println("Not found."); return; }
        Transcript t = new Transcript(s);
        for (Course c : s.getRegistrations().values()) {
            double pct = getCoursePercent(c, s);
            Grade g = GradePolicy.gradeFromPercentage(pct);
            t.addEntry(new TranscriptEntry(c, pct, g));
        }
        t.printTranscript();
    }

    // ---------------- MAIN LOOP ----------------
    private void menu() {
        System.out.println("\n===== University Exams & Grading System =====");
        System.out.println("1. Add Course");
        System.out.println("2. Add Student");
        System.out.println("3. Register Student to Course");
        System.out.println("4. Create Assessment");
        System.out.println("5. Schedule Exam");
        System.out.println("6. Record Marks");
        System.out.println("7. Publish Grades");
        System.out.println("8. Generate Transcript");
        System.out.println("0. Exit");
    }

    public void run() {
        boolean loop = true;
        while (loop) {
            menu();
            int ch = readInt("Choose: ", 0, 8);
            switch (ch) {
                case 1 -> addCourse();
                case 2 -> addStudent();
                case 3 -> registerCourse();
                case 4 -> createAssessment();
                case 5 -> scheduleExam();
                case 6 -> recordMarks();
                case 7 -> publishGrades();
                case 8 -> transcript();
                case 0 -> { System.out.println("Goodbye!"); loop = false; }
            }
        }
    }

    public static void main(String[] args) {
        new UniversityExamsSystem().run();
    }
}
