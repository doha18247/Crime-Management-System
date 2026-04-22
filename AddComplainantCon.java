import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AddComplainantCon {
    public AddComplainantCon() {
    }

    Stage stage;
    Manager manager;

    @FXML
    Button back, Update, minBtn, clsBtn;
    @FXML
    private TextField ID, SSN, Name, Age, Phone, Address;
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

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void Back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerHomePage.fxml"));
        BorderPane root = loader.load();
        Scene scene = new Scene(root);


        // Use the same stage or create a new one
        if (stage == null) {
            stage = new Stage();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ManagerCon managerController = loader.getController();
        managerController.setStage(stage);
        managerController.titleBar();
        managerController.setManager(manager);
        managerController.setHelloLabel();
        managerController.loadcaseidscrollpane();
        managerController.loadofficersscrollpane();
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
        boolean result = manager.UpdateCaseDetails(ID.getText(), SSN.getText(), Name.getText(), Age.getText(), handleSubmit(), Phone.getText(), Address.getText(), Description.getText());
        if (result) Back(e);
    }
}