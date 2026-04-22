import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EvidenceDetailsCon {
    public EvidenceDetailsCon() {
    }

    private Officer officer;
    private Evidence evidence;
    private Stage stage;

    @FXML
    Button minimzeBtn, closeBtn, back, DescriptionBtn, Save;
    @FXML
    private Label ID, Type, collectedBy, Date, CaseID, Description;
    @FXML
    private TextArea DescriptionTxt;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setOfficer(Officer officer) {
        this.officer = officer;
    }

    public void setEvidence(Evidence evidence) {
        this.evidence = evidence;
        updateUI();
    }

    @FXML
    public void titleBar() {
        if (stage != null) {
            DescriptionTxt.setVisible(false);
            Save.setVisible(false);
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

    private void updateUI() {
        if (evidence != null) {
            ID.setText("Evidence ID: " + evidence.getID());
            Type.setText("Evidence Type: " + evidence.getType());
            collectedBy.setText("Collected By Officer: " + evidence.CollectedBy);
            Date.setText("Collection Date: " + evidence.getCollectionDate());
            CaseID.setText("Case ID: " + evidence.getCaseID());
            Description.setText("Description: " + evidence.Description);
        }
    }

    public void Back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EvidencePage.fxml"));
        BorderPane root = loader.load();
        Scene scene = new Scene(root);
        // Use the same stage or create a new one
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

    public void initialize() {
        DescriptionTxt.setVisible(true);  // Show TextArea
        Save.setVisible(true);  // Show Save Button

        Save.setOnAction(event -> {
            this.evidence.Description = DescriptionTxt.getText();
            updateUI();
            DescriptionTxt.setVisible(false);  // Hide TextArea
            Save.setVisible(false);  // Hide Save Button
        });
    }
}
