import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class UserThread implements Runnable {

    Socket socket;

    public UserThread(Socket socket) {
        this.socket = socket;
    }

    @Override 
    public void run() {
        try (
            PrintWriter sender = new PrintWriter(socket.getOutputStream(), true);
            Scanner recevier = new Scanner(socket.getInputStream());
        ) {
            sender.println("Enter username: ");
            String message = recevier.nextLine();
            sender.println("Enter password: ");
            String password = recevier.nextLine();

            User user = Server.getUser(message, password);

            if(user != null) {
                System.out.println("Logged successfully");
            }
            else {
                System.out.println("Unsuccesfully login");
                return;
            }

            switch(user.getUserType()) {
                case STUDENT -> studentMenu(sender, recevier, (Student)user);
                case TEACHER -> teacherMenu(sender, recevier);
                case ADMIN -> adminMenu(sender, recevier);
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }       
    }

    public void teacherMenu(PrintWriter sender, Scanner receiver) {
        sender.println("Enter student ID to write grade: ");
        String StudentID = receiver.nextLine();
        Student student = null;
        for(User user: Server.users) {
            if(user.getUserType() == UserType.STUDENT && ((Student)user).studentId.equals(StudentID)) {
                student = (Student)user;
                break;
            }
        }
        if(student == null) {
            return;
        }
        sender.println("Enter subject: ");
        String subject = receiver.nextLine();
        sender.println("Enter semester: ");
        int semester = receiver.nextInt();
        sender.println("Enter grade");
        double value = receiver.nextDouble();
        student.grades.add(new Grade(subject, semester, value));
        sender.println("END");
    }

    public void studentMenu(PrintWriter sender, Scanner recevier, Student student) {
        student.grades.sort((grade1, grade2) -> {
            if (grade1.getSemester() == grade2.getSemester()) {
                return grade1.getSubject().compareTo(grade2.getSubject());              
            }
            return Integer.compare(grade1.getSemester(), grade2.getSemester());
        });
        for(Grade grade: student.grades) {
            sender.print("semester " + grade.getSemester() + "\t" + grade.getSubject() + "\t" + grade.getGrade() + ";");
        }
        sender.println("END");
    }

    public void adminMenu(PrintWriter sender, Scanner receiver) {
        String message = ("Welcome to admin menu. What user do you want to create: 1 - Student, 2 - Teacher, 3 - Admin"); 
        sender.println(message);
        String type = receiver.nextLine();
        if(!type.equals("1") && !type.equals("2") && !type.equals("3")) {
            return;
        }
        sender.println("Enter username: ");
        String username = receiver.nextLine();
        sender.println("Enter password: ");
        String password = receiver.nextLine();
        if(!password.matches(".{5,}")) {
            return;
        }

        switch(type) {
            case "1":
                sender.println("Enter studentID: ");
                String studentID = receiver.nextLine();
                if(!studentID.matches("[0-9]{9}")) {
                    return;
                }
                sender.println("Enter ID: ");
                String id = receiver.nextLine();
                if(!id.matches("[0-9]{10}")) { 
                    return;
                }
                Server.users.add(new Student(username, password, id, studentID));
                break;
            
            case "2":
                sender.println("Enter email: ");
                String email = receiver.nextLine();
                if(!email.matches("[a-z]+@tu-sofia.bg")) {
                    return;
                }
                Server.users.add(new Teacher(username, password, email));
                break;
            
            case "3":
                Server.users.add(new Admin(username, password));
                break;
        }
        sender.println("END");
    }    
}
