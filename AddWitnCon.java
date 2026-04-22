import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AddWitnCon {
    public AddWitnCon() {
    }

    Officer officer;
    Case aCase;
    Stage stage;

    @FXML
    Button back, AddBtn, minBtn, clsBtn;
    @FXML
    private TextField SSN, Age, Name, Phone, EviID, Address;
    @FXML
    private TextArea Testimony;
    @FXML
    RadioButton male, female;
    @FXML
    private ToggleGroup Gender;

    public void setOfficer(Officer officer) {
        this.officer = officer;
    }

    public void setCase(Case aCase) {
        this.aCase = aCase;
    }

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

    public void Back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CaseUpdate.fxml"));
        BorderPane root = loader.load();
        Scene scene = new Scene(root);
        // Use the same stage or create a new one
        if (stage == null) {
            stage = new Stage();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        CaseUpdateCon caseUpdateCon = loader.getController();
        caseUpdateCon.setStage(stage);
        caseUpdateCon.setOfficer(officer);
        caseUpdateCon.setCase(aCase);
        caseUpdateCon.titleBar();
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

    public void Add(ActionEvent e) throws IOException {
        boolean result = officer.AddWitness(aCase.CaseID, SSN.getText(), Name.getText(), Age.getText(), handleSubmit(), Phone.getText(), Address.getText(), Testimony.getText(), EviID.getText());
        System.out.println(aCase.Criminals.toString());
        if (result) Back(e);
    }
}