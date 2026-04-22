import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EditManagerCon {
    public EditManagerCon() {
    }
    Stage stage;

    @FXML
    Button back, ModifyBtn, minBtn, clsBtn;
    @FXML
    private TextField SSN, Age, Name, Phone, Address, departmentID, salary, username;
    @FXML
    private PasswordField password;
    @FXML
    RadioButton male, female;
    @FXML
    private ToggleGroup Gender;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void titleBar() {
        if (stage != null) {
            // Add minimize and close buttons
            minBtn.setOnAction(e -> stage.setIconified(true));
            clsBtn.setOnAction(e -> {
                try {
                    Files.write();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                stage.close();
            });
        }
    }

    public void setData(Manager manager) {
        male.setUserData(Citizen.Gender.MALE);
        female.setUserData(Citizen.Gender.FEMALE);
        SSN.setText(manager.getSSN());
        Age.setText(String.valueOf(manager.getAge()));
        Name.setText(manager.getName());
        Phone.setText(manager.getPhoneNumber());
        Address.setText(manager.getAddress());
        departmentID.setText(String.valueOf(manager.DepartmentID));
        salary.setText(String.valueOf(manager.getSalary()));
        username.setText(manager.getUsername());
        password.setText(manager.getPassword());
        for (Toggle toggle : Gender.getToggles()) {
            if (toggle.getUserData().equals(manager.getGender())) {
                toggle.setSelected(true);
                break;
            }
        }
    }

    public void Back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHomePage.fxml"));
        BorderPane root = loader.load();
        Scene scene = new Scene(root);

        // Use the same stage or create a new one
        if (stage == null) {
            stage = new Stage();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AdminCon adminCon = loader.getController();
        adminCon.setStage(stage);
        adminCon.loadmanagersscrollpane();
        adminCon.titleBar();
        adminCon.setHelloLabel();
        stage.setTitle("Crime Management System");
        stage.setScene(scene);
        stage.show();
    }

    private Citizen.Gender handleSubmit() {
        male.setUserData(Citizen.Gender.MALE);
        female.setUserData(Citizen.Gender.FEMALE);
        Toggle selectedToggle = Gender.getSelectedToggle();
        if (selectedToggle != null) {
            RadioButton selectedRadioButton = (RadioButton) selectedToggle;
            return (Citizen.Gender) selectedRadioButton.getUserData();
        } else {
            return null;
        }
    }

    public void Modify(ActionEvent e) throws IOException {
        boolean validpassowrd = Manager.checkPassword(password.getText());
        if (validpassowrd) {
            Admin.getAdmin().DeleteManager(Name.getText());
            boolean result = Admin.getAdmin().CreateManager(SSN.getText(), Name.getText(), Age.getText(), handleSubmit(), Phone.getText(), Address.getText(), salary.getText(), username.getText(), password.getText());
            Admin.getAdmin().AssignManager(SSN.getText(), Integer.parseInt(departmentID.getText()));
            if (result) Back(e);

        }
    }
}