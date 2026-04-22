import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Evidence implements Serializable {
    @Serial
    private static final long serialVersionUID = -231079148946252998L;
    private final int ID;
    String Description;
    private String Type;
    String CollectedBy;
    private final LocalDate CollectionDate;
    private int CaseID;

    public Evidence(int ID, String description, String type, String collectedBy, int caseID) {
        this.ID = ID;
        Description = description;
        Type = type;
        CollectedBy = collectedBy;
        CollectionDate = LocalDate.now();
        CaseID = caseID;
    }

    public int getID() {
        return ID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public LocalDate getCollectionDate() {
        return CollectionDate;
    }

    public int getCaseID() {
        return CaseID;
    }
}
