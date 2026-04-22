import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class OfficerCon {
    public OfficerCon() {
    }

    Officer officer;
    private Stage stage;
    private final StringProperty helloLabel = new SimpleStringProperty();

    @FXML
    Button closeBtn, minimzeBtn;
    @FXML
    Label HelloLabel, Name, SSN, Salary, Dep, Phone, Address, Age, Gender;
    @FXML
    Button EvidenceBtn, CaseBtn, CriminalBtn;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void titleBar() {
        if (stage != null) {
            // Add minimize and close buttons
            minimzeBtn.setOnAction(e -> stage.setIconified(true));
            closeBtn.setOnAction(e -> {
                try {
                    Files.write();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                stage.close();
            });
        }
    }

    public void setOfficer(Officer officer) {
        this.officer = officer;
        helloLabel.set("Hello Officer " + officer.getName());
    }

    public void setHelloLabel() {
        HelloLabel.textProperty().bind(helloLabel);
    }

    public void DisplayData(){
        Name.setText("Officer "+ this.officer.getName());
        SSN.setText("SSN: "+ this.officer.getSSN());
        Salary.setText("Salary: "+ this.officer.getSalary() + " EGP");
        Dep.setText("Department: "+ this.officer.department_ID);
        Age.setText("Age: "+this.officer.getAge());
        Address.setText("Address: "+this.officer.getAddress());
        Phone.setText("Phone Number: "+this.officer.getPhoneNumber());
        Gender.setText("Gender: "+ this.officer.getGender());
    }

    public void logout(ActionEvent event) throws IOException {
        // Save Data
        Files.write();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        BorderPane root = loader.load();
        LoginCon loginCon = loader.getController();
        loginCon.setStage(stage);
        loginCon.setPics();
        loginCon.titleBar();

        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        stage.show();
    }

    @FXML
    private void loadcriminalspage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CriminalsPage.fxml"));
        BorderPane root = loader.load();
        Scene scene = new Scene(root);
        if (stage == null) {
            stage = new Stage();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        CriminalsPageCon criminalsPageCon = loader.getController();
        criminalsPageCon.setStage(stage);
        criminalsPageCon.setOfficer(officer);
        criminalsPageCon.titleBar();
        criminalsPageCon.loadscrollpane();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void loadcasespage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CasesPage.fxml"));
        BorderPane root = loader.load();
        Scene scene = new Scene(root);
        if (stage == null) {
            stage = new Stage();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        CasesPageCon casesPageCon = loader.getController();
        casesPageCon.setStage(stage);
        casesPageCon.setOfficer(officer);
        casesPageCon.titleBar();
        casesPageCon.loadscrollpane();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void loadevidencespage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EvidencePage.fxml"));
        BorderPane root = loader.load();
        Scene scene = new Scene(root);
        if (stage == null) {
            stage = new Stage();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        EvidencePageCon evidencePageCon = loader.getController();
        evidencePageCon.setStage(stage);
        evidencePageCon.setOfficer(officer);
        evidencePageCon.titleBar();
        evidencePageCon.loadscrollpane();
        stage.setScene(scene);
        stage.show();
    }
}




