import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public final class Admin implements Serializable {

    @Serial
    private static final long serialVersionUID = -231079148946252998L;
    private final String Username;
    private final String Password;
    private final String Name;
    private final Files file = Files.getFiles();

    // Singleton instance
    private static Admin adminInstance;

    // Private constructor
    private Admin(String username, String password, String name) {
        this.Username = username;
        this.Password = password;
        this.Name = name;
    }

    // Static method to set or get the admin instance
    public static void setAdmin(String username, String password, String name) {
        if (adminInstance == null) {
            adminInstance = new Admin(username, password, name );
        } else {
            System.out.println("Admin is already set!");
        }
    }

    // Getter for the Admin instance
    public static Admin getAdmin() {
        if (adminInstance == null) {
            throw new IllegalStateException("Admin has not been set yet!");
        }
        return adminInstance;
    }

    public static String getUsername() {
        if (adminInstance == null) {
            throw new IllegalStateException("Admin has not been set yet!");
        }
        return adminInstance.Username;
    }

    public static String getPassword() {
        if (adminInstance == null) {
            throw new IllegalStateException("Admin has not been set yet!");
        }
        return adminInstance.Password;
    }

    public static String getName() {
        if (adminInstance == null) {
            throw new IllegalStateException("Admin has not been set yet!");
        }
        return adminInstance.Name;
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.initStyle(StageStyle.UTILITY); // Optional: makes the alert window minimalistic
        alert.showAndWait(); // Show the alert and wait for the user to acknowledge
    }

    //Assign Manager
    public void AssignManager(String SSN, int Dep_ID) {
        Manager newManager = null; // The manager being assigned
        Manager oldManager = null; // The manager currently assigned to the department (if any)
        Department newDepartment = null; // The department to which the manager is being assigned
        Department oldDepartment = null; // The department currently managed by the new manager (if any)

        for (Manager manager : file.MainManagersList) {
            if (manager.getSSN().equals(SSN)) {
                newManager = manager;

                for (Department department : file.MainDepartmentsList) {
                    if (department.ID == manager.DepartmentID) {
                        oldDepartment = department;
                        break;
                    }
                }
                break;
            }
        }

        for (Department department : file.MainDepartmentsList) {
            if (department.ID == Dep_ID) {
                newDepartment = department;

                for (Manager manager : file.MainManagersList) {
                    if (manager.DepartmentID == Dep_ID) {
                        oldManager = manager;
                        break;
                    }
                }
                break;
            }
        }
        if (newManager == null || newDepartment == null) {
            showAlert(Alert.AlertType.ERROR, "Manager", "Assign Manager to Department", "Invalid Manager SSN or Department ID");
            return;
        }
        if (oldManager != null) {
            oldManager.setDepartmentID("0");
        }

        if (oldDepartment != null) {
            oldDepartment.setManagerID(null);
        }

        newManager.setDepartmentID(String.valueOf(Dep_ID));
        newDepartment.setManagerID(SSN);

        showAlert(Alert.AlertType.INFORMATION, "Manager", "Assign Manager to Department", "Manager Assigned Successfully");
    }

    //Create Department
    public boolean CreateDepartment(String ID, String Name) {
        try {
            boolean exists = false;
            for (Department department : file.MainDepartmentsList) {
                if (department.ID == Integer.parseInt(ID)) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", "There is another department with same ID.");
                return false;
            } else {
                Department newDepartment = new Department(Integer.parseInt(ID), Name);
                file.MainDepartmentsList.add(newDepartment);
                showAlert(Alert.AlertType.INFORMATION, "Department", "Create Department", "Department Created Successfully");
            }
            return true;
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", e.getMessage());
            return false;
        }
    }

    //Create case
    public boolean CreateCase(String ID, String CaseType, String SSN, String Name, String Age, Citizen.Gender gender, String PhoneNumber, String Address, String Description) {
        try {
            for (Case acase : file.MainCasesList) {
                if (acase.CaseID == Integer.parseInt(ID)) {
                    showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", "There exists an already case with same ID.");
                    return false;
                } else {
                    SpecialCitizen Victim = new SpecialCitizen(SSN, Name, Integer.parseInt(Age), gender, PhoneNumber, Address, Description, SpecialCitizen.Relation.Victim);
                    Case newCase = new Case(Integer.parseInt(ID), CaseType, Victim);
                    if (!file.MainCasesList.contains(newCase)) {
                        file.MainCasesList.add(newCase);
                        showAlert(Alert.AlertType.INFORMATION, "Case", "Create Case", "Case Created Successfully");
                    }
                    return true;
                }
            }
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", e.getMessage());
            return false;
        }
        return false;
    }

    //Create Officer
    public boolean CreateOfficer(String SSN, String Name, String Age, Citizen.Gender gender, String PhoneNumber, String Address, String salary, String username, String password) {
        try {
            boolean thereexists = false;
            for (Manager amanager : file.MainManagersList) {
                if (amanager.getSSN().equals(SSN)) {
                    thereexists = true;
                    break;
                }
            }
            for (Officer officer : file.MainOfficersList) {
                if (officer.getSSN().equals(SSN)) {
                    thereexists = true;
                    break;
                }
            }
            if (thereexists) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", "There is another manager or officer with same SSN.");
                return false;
            } else {
                Officer newOfficer = new Officer(SSN, Name, Integer.parseInt(Age), gender, PhoneNumber, Address, salary, username, password);
                if (!file.MainOfficersList.contains(newOfficer)) {
                    file.MainOfficersList.add(newOfficer);
                    showAlert(Alert.AlertType.INFORMATION, "Officer", "Create Officer", "Officer Created Successfully");
                }
                return true;
            }
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", e.getMessage());
            return false;
        }
    }

    //Create Manager
    public boolean CreateManager(String SSN, String Name, String Age, Citizen.Gender gender, String PhoneNumber, String Address, String salary, String username, String password) {
        try {
            boolean thereexists = false;
            for (Manager amanager : file.MainManagersList) {
                if (amanager.getSSN().equals(SSN)) {
                    thereexists = true;
                    break;
                }
            }
            for (Officer officer : file.MainOfficersList) {
                if (officer.getSSN().equals(SSN)) {
                    thereexists = true;
                    break;
                }
            }
            if (thereexists) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", "There is another manager or officer with same SSN");
                return false;
            } else {
                Manager newManager = new Manager(SSN, Name, Integer.parseInt(Age), gender, PhoneNumber, Address, salary, username, password);
                if (!file.MainManagersList.contains(newManager)) {
                    file.MainManagersList.add(newManager);
                    showAlert(Alert.AlertType.INFORMATION, "Manager", "Create Manager", "Manager Created Successfully");
                }
                return true;
            }

        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", e.getMessage());
            return false;
        }
    }

    //Create Officer
    public boolean EditOfficer(String SSN, String Name, String Age, Citizen.Gender gender, String PhoneNumber, String Address, String salary, ArrayList<Case> cases, int dep,String username, String password) {
        try {
            boolean thereexists = false;
            for (Manager amanager : file.MainManagersList) {
                if (amanager.getSSN().equals(SSN)) {
                    thereexists = true;
                    break;
                }
            }
            for (Officer officer : file.MainOfficersList) {
                if (officer.getSSN().equals(SSN)) {
                    thereexists = true;
                    break;
                }
            }
            if (thereexists) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", "There is another manager or officer with same SSN.");
                return false;
            } else {
                Officer newOfficer = new Officer(SSN, Name, Integer.parseInt(Age), gender, PhoneNumber, Address, salary, cases, dep,username, password);
                if (!file.MainOfficersList.contains(newOfficer)) {
                    file.MainOfficersList.add(newOfficer);
                    showAlert(Alert.AlertType.INFORMATION, "Officer", "Create Officer", "Officer Created Successfully");
                }
                return true;
            }
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid Input", e.getMessage());
            return false;
        }
    }

    //delete officer
    public void DeleteOfficer(String SSN) {
        file.MainOfficersList.removeIf(officer -> officer.getSSN().equals(SSN));
    }

    //delete manager
    public void DeleteManager(String SSN) {
        file.MainManagersList.removeIf(manager -> manager.getSSN().equals(SSN));
    }
}

