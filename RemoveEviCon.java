import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class RemoveEviCon {
    public RemoveEviCon() {
    }

    Officer officer;
    private Case aCase;
    private Stage stage;

    @FXML
    Button minimzeBtn, closeBtn, back, remove;
    @FXML
    TextField EviID;

    public void setOfficer(Officer officer) {
        this.officer = officer;
    }

    public void setCase(Case aCase) {
        this.aCase = aCase;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText("Evidence doesn't exist.");
        alert.initStyle(StageStyle.UTILITY); // Optional: makes the alert window minimalistic
        alert.showAndWait(); // Show the alert and wait for the user to acknowledge
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

    public void Remove(ActionEvent e) throws IOException {
        boolean result = officer.RemoveEvidence(aCase.CaseID, Integer.parseInt(EviID.getText()));
        if (result) Back(e);
        else {
            showAlert();
        }
    }
}
