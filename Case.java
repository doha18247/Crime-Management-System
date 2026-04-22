import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Case implements Serializable {
    @Serial
    private static final long serialVersionUID = -231079148946252998L;
    int CaseID;
    String Description;
    private final LocalDate StartDate;
    private LocalDate LastUpdateDate;
    String CaseType;
    ArrayList<Officer> Officers = new ArrayList<>();
    ArrayList<Criminal> Criminals = new ArrayList<>();
    ArrayList<Witness> Witnesses = new ArrayList<>();
    ArrayList<SpecialCitizen> Suspects = new ArrayList<>();
    ArrayList<Evidence> Evidences = new ArrayList<>();
    SpecialCitizen complainant;
    SpecialCitizen Victim;

    //Used by Admin for CreateCase();
    public Case(int caseID, String caseType, SpecialCitizen victim) {
        CaseID = caseID;
        StartDate = LocalDate.now();
        LastUpdateDate= LocalDate.now();
        CaseType = caseType;
        Victim = victim;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public LocalDate getLastUpdateDate() {
        return LastUpdateDate;
    }

    public void setLastUpdateDate(LocalDate lastUpdateDate) {
        LastUpdateDate = lastUpdateDate;
    }
}
