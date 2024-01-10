import java.util.ArrayList;
import java.util.List;

public class Student extends User{
    
    String studentId;
    String id;

    public Student(String usernamee, String password, String studentId, String id) {
        super(usernamee, password);
        this.studentId = studentId;
        this.id = id;
    }

    List<Grade> grades = new ArrayList<>();   //<type> grades - name 

    @Override
    public UserType getUserType() {
        return UserType.STUDENT;
    }
    
}
