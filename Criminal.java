import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Criminal extends Citizen implements Serializable {
    @Serial
    private static final long serialVersionUID = -231079148946252998L;
    private String CurrentLocation;
    ArrayList<Case> CrimesCommitted = new ArrayList<>();
    Files file = Files.getFiles();

    enum Danger {
        High, Moderate, Low
    }

    Danger Level;

    public Criminal(String SSN, String Name, int Age, Gender gender, String PhoneNumber, String Address, String CurrentLocation, int count) {
        super(SSN, Name, Age, gender, PhoneNumber, Address);
        this.CurrentLocation = CurrentLocation;
        this.Level = CalcDangerLevel(count);
    }

    public Criminal(String SSN, String Name, int Age, Gender gender, String PhoneNumber, String Address, Files file) {
        super(SSN, Name, Age, gender, PhoneNumber, Address);
        this.file = file;
    }

    public void AddCrime(Case c) {
        CrimesCommitted.add(c);
    }

    public String getCurrentLocation() {
        return CurrentLocation;
    }

    public Danger CalcDangerLevel(int CrimesCommitted) {
        if (CrimesCommitted > 5 && CrimesCommitted < 10) return Danger.Moderate;
        else if (CrimesCommitted > 10) return Danger.High;
        else return Danger.Low;
    }
}
