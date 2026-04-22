import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

import java.io.Serial;
import java.io.Serializable;

public class Manager extends Citizen implements Serializable {
    @Serial
    private static final long serialVersionUID = -231079148946252998L;
    private int Salary;
    int DepartmentID;
    private String username;
    private String password;
    private final Files file = Files.getFiles();

    //  Used by Admin for CreateManager();
    public Manager(String SSN, String Name, int Age, Gender gender, String PhoneNumber, String Address, String salary, String username, String password) {
        super(SSN, Name, Age, gender, PhoneNumber, Address);
        setSalary(salary);
        setUsername(username);
        setPassword(password);
    }

    //Used by Officer for setUsername();
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getSalary() {
        return Salary;
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

    // Used in Manager Constructor
    public void setDepartmentID(String departmentID) {
        try {
            int intDepartmentID = Integer.parseInt(departmentID); // string to num

            if (intDepartmentID < 0) {
                throw new IllegalArgumentException("INVALID ID !! Department ID must be positive.");
            }

            this.DepartmentID = intDepartmentID;

        } catch (NumberFormatException e) {
            System.out.println("Error: Department ID must be an integer value.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
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

    // Used in Manager Constructor
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

    // Used in Manager Constructor
    public void setUsername(String username) {
        if (username == null || username.isEmpty())
            throw new IllegalArgumentException(" Username cannot be null or empty.");
        for (Manager user : file.MainManagersList)
            if (user.username.equals(username))
                throw new IllegalArgumentException("Username is already assigned to a manager!!");
        for (Officer user : file.MainOfficersList)
            if (user.getUsername().equals(username))
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

    public boolean assignCaseToOfficer(String SSN, String CaseID) {
        try {
            Officer officer = null;
            Case caseObj = null;

            // Find officer by SSN
            for (Officer officer1 : file.MainOfficersList) {
                if (officer1.getSSN().equals(SSN)) {
                    officer = officer1;
                    break;
                }
            }

            // Find case by CaseID
            for (Case acase : file.MainCasesList) {
                if (acase.CaseID == Integer.parseInt(CaseID)) {
                    caseObj = acase;
                    break;
                }
            }

            // Check if officer or case is not found
            if (officer == null) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", "Officer SSN is wrong.");
                return false;
            }
            if (caseObj == null) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", "Case ID is wrong.");
                return false;
            }

            // Check if the officer is already assigned to the case
            if (officer.OfficerCases.contains(caseObj)) {
                showAlert(Alert.AlertType.INFORMATION, "Case", "Assign Officer", "The officer is already handling this case.");
                return false;  // Don't add the case again
            }

            // Assign the case to the officer and vice versa
            officer.OfficerCases.add(caseObj);
            caseObj.Officers.add(officer);

            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "Case", "Assign Officer", "Case assigned to the officer successfully.");
            return true;
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", e.getMessage());
            return false;
        }
    }

    public boolean assignOfficerToDepartment(String SSN) {
        // Remove officer from their current department, if any
        for (Department department : file.MainDepartmentsList) {
            department.Officers.removeIf(officer -> officer.getSSN().equals(SSN));
        }

        // Find the target department
        for (Department departmentObj : file.MainDepartmentsList) {
            if (departmentObj.ID == this.DepartmentID) {
                // Find the officer to assign
                for (Officer officer : file.MainOfficersList) {
                    if (officer.getSSN().equals(SSN)) {
                        // Check if officer is already in the target department
                        if (officer.department_ID == departmentObj.ID) {
                            System.out.println("Officer found and is already assigned to this dep. Officer " + officer.getName());
                            showAlert(Alert.AlertType.INFORMATION, "Department", "Assign Officer", "The officer is already assigned to this department.");
                            return false;
                        }
                        System.out.println("Officer found and is not assigned to this dep. Officer " + officer.getName());
                        // Assign officer to the new department
                        officer.department_ID = departmentObj.ID;
                        departmentObj.Officers.add(officer);
                        showAlert(Alert.AlertType.INFORMATION, "Department", "Assign Officer", "Officer assigned to the department successfully.");
                        return true;
                    }
                    System.out.println("Not the targeted Officer.");
                }
                System.out.println("Officer not found.");
                // If officer not found in the main list
                showAlert(Alert.AlertType.ERROR, "Department", "Assign Officer", "Officer not found.");
                return false;
            }
        }

        // If target department not found
        showAlert(Alert.AlertType.ERROR, "Department", "Assign Officer", "Target department not found.");
        return false;
    }

    public boolean UpdateCaseDetails(String CaseID, String SSN, String Name, String Age, Gender gender, String PhoneNumber, String Address, String description) {
        try {
            SpecialCitizen newComplainant = new SpecialCitizen(SSN, Name, Integer.parseInt(Age), gender, PhoneNumber, Address, description, SpecialCitizen.Relation.Complainant);
            for (Case acase : file.MainCasesList) {
                if (acase.CaseID == Integer.parseInt(CaseID)) {
                    acase.complainant = newComplainant;
                    file.MainComplainantsList.add(newComplainant);
                    showAlert(Alert.AlertType.INFORMATION, "Complainant", "Add Complainant", "Complainant added to the case successfully.");

                }
            }
            return true;
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", e.getMessage());
            return false;
        }
    }

    public boolean RemoveOfficer(String SSN, String CaseID) {
        try {
            Officer officer = null;
            Case caseObj = null;

            // Find the officer by SSN
            for (Officer officer1 : file.MainOfficersList) {
                if (officer1.getSSN().equals(SSN)) {
                    officer = officer1;
                    break;  // Stop the loop once the officer is found
                }
            }

            // Find the case by CaseID
            for (Case acase : file.MainCasesList) {
                if (acase.CaseID == Integer.parseInt(CaseID)) {
                    caseObj = acase;
                    break;  // Stop the loop once the case is found
                }
            }

            // Check if the officer and case were found
            if (officer == null) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", "Officer SSN not found.");
                return false;
            }

            if (caseObj == null) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", "Case ID not found.");
                return false;
            }

            // Remove the officer from the case
            if (officer.OfficerCases.contains(caseObj)) {
                officer.OfficerCases.remove(caseObj);
                caseObj.Officers.remove(officer);

                if (!caseObj.Officers.contains(officer)) {
                    showAlert(Alert.AlertType.INFORMATION, "Case", "Remove Officer", "Officer removed from the case successfully.");
                    return true;
                } else {
                    showAlert(Alert.AlertType.INFORMATION, "Case", "Remove Officer", "The officer was not removed from the case.");
                    return false;
                }
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Case", "Remove Officer", "Officer is not assigned to this case.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Case ID", "Case ID must be a number.");
            return false;
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Unexpected Error", "Error", "An unexpected error occurred: " + e.getMessage());
            return false;
        }
    }
}
