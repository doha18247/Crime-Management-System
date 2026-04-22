import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Department implements Serializable {
    @Serial
    private static final long serialVersionUID = -231079148946252998L;
    int ID;
    String Name;
    private final LocalDate Activation;
    private String ManagerID;
    ArrayList<Officer> Officers = new ArrayList<>();

    //    Used by Admin CreateDepartment();
    public Department(int ID, String name) {
        this.ID = ID;
        Name = name;
        Activation= LocalDate.now();
    }

    public String getManagerID() {
        return ManagerID;
    }

    public void setManagerID(String ManagerID) {
        this.ManagerID = ManagerID;
    }
}
