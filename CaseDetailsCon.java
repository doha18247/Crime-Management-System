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
import java.util.StringJoiner;

public class CaseDetailsCon {
    public CaseDetailsCon() {
    }

    private Officer officer;
    private Case detailedCase;
    private Stage stage;
    @FXML
    Button minimzeBtn, closeBtn, back;
    @FXML
    Label CaseID, Type, Date, Update, Officers, Witness, Suspects, Criminals, Comp, Victim, Evidences, Description;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setOfficer(Officer officer) {
        this.officer = officer;
    }

    public void setCase(Case detailedCase) {
        this.detailedCase = detailedCase;
        updateUI();
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

    public void updateUI() {
        if (detailedCase != null) {
            CaseID.setText("Case ID: " + detailedCase.CaseID);
            Type.setText("Case Type: " + detailedCase.CaseType);
            Date.setText("Crime Date: " + detailedCase.getStartDate());
            Update.setText("Last Update Date: " + detailedCase.getLastUpdateDate());

            StringJoiner officers = new StringJoiner(", ");
            for (Officer off : detailedCase.Officers) {
                officers.add(off.getName());
            }
            Officers.setText("Officers: " + officers);

            StringJoiner witness = new StringJoiner(", ");
            for (Witness w : detailedCase.Witnesses) {
                witness.add(w.getName());
            }
            Witness.setText("Witnesses: " + witness);

            StringJoiner suspects = new StringJoiner(", ");
            for (SpecialCitizen sus : detailedCase.Suspects) {
                suspects.add(sus.getName());
            }
            Suspects.setText("Suspects: " + suspects);

            StringJoiner criminals = new StringJoiner(", ");
            for (Criminal crim : detailedCase.Criminals) {
                criminals.add(crim.getName());
            }
            Criminals.setText("Criminals: " + criminals);

            if (detailedCase.complainant != null) {
                Comp.setText("Complainant: " + detailedCase.complainant.getName());
            }else{
                Comp.setText("Complainant: null");
            }

            Victim.setText("Victim: " + detailedCase.Victim.getName());

            StringJoiner evidence = new StringJoiner(", ");
            for (Evidence e : detailedCase.Evidences) {
                evidence.add(e.getType());
            }
            Evidences.setText("Evidences: " + evidence);

            Description.setText("Description: " + detailedCase.Description);
        }
    }

    public void Back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CasesPage.fxml"));
        BorderPane root = loader.load();
        Scene scene = new Scene(root);
        // Use the same stage or create a new one
        if (stage == null) {
            stage = new Stage();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        CasesPageCon casesPageCon = loader.getController();
        casesPageCon.setStage(stage);
        casesPageCon.setOfficer(officer);
        casesPageCon.titleBar();
        casesPageCon.loadscrollpane();
        stage.setTitle("Crime Management System");
        stage.setScene(scene);
        stage.show();
    }
}
