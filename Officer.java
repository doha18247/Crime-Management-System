import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Officer extends Citizen implements Serializable {
    @Serial
    private static final long serialVersionUID = -231079148946252998L;
    private int Salary;
    public ArrayList<Case> OfficerCases = new ArrayList<>();
    public int department_ID = 0;
    private String username;
    private String password;
    private final Files file = Files.getFiles();

    //    Used by Admin for CreateOfficer();
    public Officer(String SSN, String Name, int Age, Gender gender, String PhoneNumber, String Address, String salary, String username, String password) {
        super(SSN, Name, Age, gender, PhoneNumber, Address);
        setSalary(salary);
        setUsername(username);
        setPassword(password);
    }

    public Officer(String SSN, String Name, int Age, Gender gender, String PhoneNumber, String Address, String salary, ArrayList<Case> officerCases, int department_ID, String username, String password) {
        super(SSN, Name, Age, gender, PhoneNumber, Address);
        setSalary(salary);
        setUsername(username);
        setPassword(password);
        OfficerCases = officerCases;
        this.department_ID = department_ID;
    }

    // Used in Manager Constructor
    public void setSalary(String Salary) {
        try {

            int IntSalary = Integer.parseInt(Salary); //string to num

            if (IntSalary < 0) {
                throw new IllegalArgumentException("INVALID SALARY!! Salary must be positive.");
            }

            this.Salary = IntSalary;

        } catch (NumberFormatException e) {

            System.out.println("Error: Salary must be an integer value.");
        } catch (IllegalArgumentException e) {  //if salary -veً
            System.out.println("Error: " + e.getMessage());
        }
    }

    public int getSalary() {
        return Salary;
    }

    // Used in Manager Constructor
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static boolean checkPassword(String password) {
        try {
            if (password == null || password.isEmpty()) {
                throw new IllegalArgumentException("Password cannot be null or empty.");
            }

            if (password.length() < 6) {
                throw new IllegalArgumentException("Password must be at least 6 characters long.");
            }

            if (!password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*\\d.*")) {  //.any char ,*repeating
                throw new IllegalArgumentException("Your password is not secure. Please try again");
            }
            return true;
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Password", "Invalid Password", e.getMessage());
            return false;
        }
    }

    public void setPassword(String password) {
        try {
            if (password == null || password.isEmpty()) {
                throw new IllegalArgumentException("Password cannot be null or empty.");
            }

            if (password.length() < 6) {
                throw new IllegalArgumentException("Password must be at least 6 characters long.");
            }

            if (!password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*\\d.*")) {  //.any char ,*repeating
                throw new IllegalArgumentException("Your password is not secure. Please enter [A-Z],[a-z], and [0-9]");
            }

            this.password = password;
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Password", "Invalid Password", e.getMessage());
        }
    }

    public void setUsername(String username) {
        if (username == null || username.isEmpty())
            throw new IllegalArgumentException(" Username cannot be null or empty.");
        for (Manager user : file.MainManagersList)
            if (user.getUsername().equals(username))
                throw new IllegalArgumentException("Username is already assigned to a manager!!");
        for (Officer user : file.MainOfficersList)
            if (user.username.equals(username))
                throw new IllegalArgumentException("Username is already assigned to an officer!!");
        if (username.equals(Admin.getUsername()))
            throw new IllegalArgumentException("Username is already assigned to an admin!!");
        this.username = username;
    }

    private static void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.initStyle(StageStyle.UTILITY); // Optional: makes the alert window minimalistic
        alert.showAndWait(); // Show the alert and wait for the user to acknowledge
    }

    public void UpdateCase(String CaseID, String CaseType, String Description) {
        try {
            int caseIdInt = Integer.parseInt(CaseID);
            // Update the case in OfficerCases
            for (Case officerCase : OfficerCases) {
                if (officerCase.CaseID == caseIdInt) {
                    officerCase.CaseType = CaseType;
                    officerCase.Description = Description;
                    officerCase.setLastUpdateDate(LocalDate.now());
                    break;
                }
            }
            // Update the case in MainCasesList
            for (Case c : file.MainCasesList) {
                if (c.CaseID == caseIdInt) {
                    c.CaseType = CaseType;
                    c.Description = Description;
                    c.setLastUpdateDate(LocalDate.now());
                    System.out.println("Case Updated Successfully.");
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", e.getMessage());
        }
    }

    public boolean AddWitness(int CaseID, String SSN, String Name, String Age, Gender gender, String PhoneNumber, String Address, String testimony, String EvidenceID) {
        try {
            Witness newWitness = new Witness(SSN, Name, Integer.parseInt(Age), gender, PhoneNumber, Address, testimony, CaseID);
            this.AddEvidence(EvidenceID, testimony, "Testimony", CaseID);
            if (!file.MainWitnessesList.contains(newWitness)) {
                file.MainWitnessesList.add(newWitness);
            }
            for (Case c : file.MainCasesList)
                if (c.CaseID == CaseID && !c.Witnesses.contains(newWitness)) {
                    c.Witnesses.add(newWitness);
                    showAlert(Alert.AlertType.INFORMATION, "Witness", "Add Witness", "Witness added successfully to case.");
                }
            return true;
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", "Failed to add witness.\n" + e.getMessage());
            return false;
        }
    }

    public boolean AddSuspect(int CaseID, String SSN, String Name, String Age, Gender gender, String PhoneNumber, String Address, String Description) {
        try {
            SpecialCitizen newSuspect = new SpecialCitizen(SSN, Name, Integer.parseInt(Age), gender, PhoneNumber, Address, Description, SpecialCitizen.Relation.Suspect);
            if (!file.MainSuspectsList.contains(newSuspect)) {
                file.MainSuspectsList.add(newSuspect);
            }
            for (Case c : file.MainCasesList) {
                if (c.CaseID == CaseID && !c.Suspects.contains(newSuspect)) {
                    c.Suspects.add(newSuspect);
                    showAlert(Alert.AlertType.INFORMATION, "Suspect", "Add Suspect", "Suspect added successfully case.");
                }
            }
            return true;
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", "Failed to add suspect.\n" + e.getMessage());
            return false;
        }
    }

    public boolean AddCriminal(int caseID, String ssn, String name, String age, Gender gender, String phoneNumber, String address, String currentLocation) {
        try {
            // Find the case by ID
            Case targetCase = null;
            for (Case c : file.MainCasesList) {
                if (c.CaseID == caseID) {
                    targetCase = c;
                    break;
                }
            }
            if (targetCase == null) {
                showAlert(Alert.AlertType.ERROR, "Case Not Found", "Invalid Case ID",
                        "No case found with the provided Case ID.");
                return false;
            }

            // Check if criminal already exists
            for (Criminal criminal : file.MainCriminalsList) {
                if (criminal.getSSN().equals(ssn)) {
                    criminal.AddCrime(targetCase); // Add case to the criminal's crime history
                    targetCase.Criminals.add(criminal); // Add criminal to the case
                    showAlert(Alert.AlertType.INFORMATION, "Criminal Added",
                            "Existing Criminal Linked", "Criminal linked to the case successfully.");
                    return true;
                }
            }

            // Create a new criminal
            Criminal newCriminal = new Criminal(ssn, name, Integer.parseInt(age), gender,
                    phoneNumber, address, currentLocation, 1);
            newCriminal.AddCrime(targetCase);
            targetCase.Criminals.add(newCriminal);
            file.MainCriminalsList.add(newCriminal);
            showAlert(Alert.AlertType.INFORMATION, "Criminal Created",
                    "New Criminal Added", "New criminal created and added to the case successfully.");
            return true;

        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input",
                    "Failed to add criminal.\n" + e.getMessage());
            return false;
        }
    }


//    public boolean AddCriminal(int CaseID, String SSN, String Name, String Age, Gender gender, String PhoneNumber, String Address, String CurrentLocation) {
//        try {
//            for (Criminal criminal : file.MainCriminalsList) {
//                if (criminal.getSSN().equals(SSN)) {
//                    for (Case c : file.MainCasesList) {
//                        if (c.CaseID == CaseID) {
//                            criminal.AddCrime(c);
//                            c.Criminals.add(criminal);
//                            showAlert(Alert.AlertType.INFORMATION, "Criminal", "Add Criminal", "Criminal added successfully to case.");
//                            return true;
//                        }
//                    }
//                }
//            }
//            Criminal newCriminal = new Criminal(SSN, Name, Integer.parseInt(Age), gender, PhoneNumber, Address, CurrentLocation, 1);
//            for (Case c : file.MainCasesList)
//                if (c.CaseID == CaseID) {
//                    newCriminal.AddCrime(c);
//                    c.Criminals.add(newCriminal);
//                    file.MainCriminalsList.add(newCriminal);
//                    showAlert(Alert.AlertType.INFORMATION, "Criminal", "Add Criminal", "New Criminal created and added successfully to case.");
//                }
//            return true;
//        } catch (IllegalArgumentException e) {
//            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", "Failed to add criminal.\n" + e.getMessage());
//            return false;
//        }
//    }

    public boolean AddEvidence(String ID, String description, String type, int CaseID) {
        try {
            Evidence newEvidence = new Evidence(Integer.parseInt(ID), description, type, this.getName(), CaseID);
            if (!file.MainEvidencesList.contains(newEvidence)) {
                file.MainEvidencesList.add(newEvidence);
            }
            for (Case c : file.MainCasesList) {
                if (c.CaseID == CaseID && !c.Evidences.contains(newEvidence)) {
                    c.Evidences.add(newEvidence);
                    showAlert(Alert.AlertType.INFORMATION, "Evidence", "Add Evidence", "Evidence added successfully to case.");
                }
            }
            return true;
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", "Failed to add evidence.\n" + e.getMessage());
            return false;
        }
    }

    public boolean RemoveWitness(int CaseID, String Name, String EviID) {
        try {
            for (Case c : file.MainCasesList) {
                if (c.CaseID == CaseID) {
                    for (Witness w : c.Witnesses) {
                        c.Witnesses.remove(w);
                    }
                }
            }
            file.MainEvidencesList.removeIf(e -> e.getCaseID() == CaseID && e.getID() == Integer.parseInt(EviID));
            return file.MainWitnessesList.removeIf(list -> Objects.equals(list.getName(), Name));
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", e.getMessage());
            return false;
        }
    }

    public boolean RemoveSuspect(int CaseID, String Name) {
        try {
            for (Case c : file.MainCasesList) {
                if (c.CaseID == CaseID) {
                    for (SpecialCitizen sus : c.Suspects) {
                        c.Suspects.remove(sus);
                    }
                }
            }
            return file.MainWitnessesList.removeIf(list -> Objects.equals(list.getName(), Name));
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", e.getMessage());
            return false;
        }
    }

    public boolean RemoveCriminal(int CaseID, String Name) {
        try {
            for (Case c : file.MainCasesList) {
                if (c.CaseID == CaseID) {
                    for (Criminal criminal : c.Criminals) {
                        c.Criminals.remove(criminal);
                    }
                }
            }
            return file.MainCriminalsList.removeIf(list -> Objects.equals(list.getName(), Name));
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", e.getMessage());
            return false;
        }
    }

    public boolean RemoveEvidence(int ID, int CaseID) {
        try {
            for (Case c : file.MainCasesList) {
                if (c.CaseID == CaseID) {
                    for (Evidence evidence : c.Evidences) {
                        c.Evidences.remove(evidence);
                    }
                }
            }
            return file.MainEvidencesList.removeIf(list -> Objects.equals(list.getID(), ID));
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", e.getMessage());
            return false;
        }
    }
}
