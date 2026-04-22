import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class EditOfficerCon {
    public EditOfficerCon() {}
    ArrayList <Case> cases;
    int department;
    Stage stage;
    @FXML
    Button back, ModifyBtn, minBtn, clsBtn;
    @FXML
    private TextField SSN, Age, Name, Phone, Address, salary, username;
    @FXML
    private PasswordField password;
    @FXML
    RadioButton male, female;
    @FXML
    private ToggleGroup Gender;

    public void setOfficer(Officer officer) {
        this.cases = officer.OfficerCases;
        this.department=officer.department_ID;
    }
    public void setStage(Stage stage) {
        this.stage =stage;
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
        adminCon.loadofficersscrollpane();
        adminCon.titleBar();
        adminCon.setHelloLabel();
        stage.setTitle("Crime Management System");
        stage.setScene(scene);
        stage.show();
    }
    public void setData(Officer officer){
        male.setUserData(Citizen.Gender.MALE);
        female.setUserData(Citizen.Gender.FEMALE);
        SSN.setText(officer.getSSN());
        Age.setText(String.valueOf(officer.getAge()));
        Name.setText(officer.getName());
        Phone.setText(officer.getPhoneNumber());
        Address.setText(officer.getAddress());
        salary.setText(String.valueOf(officer.getSalary()));
        username.setText(officer.getUsername());
        password.setText(officer.getPassword());
        for(Toggle toggle: Gender.getToggles()){
            if(toggle.getUserData().equals(officer.getGender())){
                toggle.setSelected(true);
                break;
            }
        }
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
        boolean validpassowrd = Officer.checkPassword(password.getText());
        if (validpassowrd) {
            Admin.getAdmin().DeleteOfficer(SSN.getText());
            boolean result = Admin.getAdmin().EditOfficer(SSN.getText(), Name.getText(), Age.getText(), handleSubmit(), Phone.getText(), Address.getText(), salary.getText(), cases, department, username.getText(), password.getText());
            if (result)
                Back(e);
        }
    }
}