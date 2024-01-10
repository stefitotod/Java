public class Grade {
    
    private String subject;
    private int semester;
    private double grade;

    public Grade(String subject, int semester, double grade) {
        this.subject = subject;
        this.semester = semester;
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public int getSemester() {
        return semester;
    }

    public double getGrade() {
        return grade;
    }
    
}
