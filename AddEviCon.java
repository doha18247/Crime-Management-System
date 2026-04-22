import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AddEviCon {
    public AddEviCon() {}

    Officer officer;
    Case aCase;
    Stage stage;

    @FXML
    Button AddBtn, back, clsBtn, miniBtn;
    @FXML
    private TextArea Description;
    @FXML
    private TextField EvidenceType, ID;

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
            miniBtn.setOnAction(e -> stage.setIconified(true));
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

    public void Add(ActionEvent e) throws IOException {
        boolean result = officer.AddEvidence(ID.getText(), Description.getText(), EvidenceType.getText(), aCase.CaseID);
        System.out.println(aCase.Evidences.toString());
        if (result) Back(e);
    }
}
