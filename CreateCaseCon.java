import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateCaseCon {
    public CreateCaseCon() {
    }

    Stage stage;

    @FXML
    Button back, CreateBtn, minBtn, clsBtn;
    @FXML
    private TextField ID, CaseType, SSN, Name, Age, Phone, Address;
    @FXML
    TextArea Description;
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
        adminCon.loadcaseidscrollpane();
        adminCon.titleBar();
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

    public void Create(ActionEvent e) throws IOException {
        boolean result = Admin.getAdmin().CreateCase(ID.getText(), CaseType.getText(), SSN.getText(), Name.getText(), Age.getText(), handleSubmit(), Phone.getText(), Address.getText(), Description.getText());
        if (result) Back(e);
    }
}