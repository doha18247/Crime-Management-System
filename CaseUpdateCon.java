import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class CaseUpdateCon {
    public CaseUpdateCon() {
    }

    private Officer officer;
    private Case detailedCase;
    private Stage stage;

    @FXML
    private TextField CaseType;
    @FXML
    private TextArea Description;
    @FXML
    private Button AddCrim, AddEvi, AddWitn, AddSus, RemoveCrim, RemoveEvi, RemoveWitn, RemoveSus;
    @FXML
    private Button clsBtn, miniBtn;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setOfficer(Officer officer) {
        this.officer = officer;
    }

    public void setCase(Case detailedCase) {
        this.detailedCase = detailedCase;
    }

    public void setCaseDetails() {
        CaseType.setText(detailedCase.CaseType);
        Description.setText(detailedCase.Description);
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
        stage.setScene(scene);
        stage.show();
    }

    public void handleNavigation(ActionEvent event) {
        String fxmlFile = getString(event);

        if (fxmlFile != null) {
            try {
                // Load the next page's FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                BorderPane root = loader.load();

                switch (fxmlFile) {
                    case "AddCrim.fxml" -> {
                        AddCrimCon addCrimCon = loader.getController();
                        addCrimCon.setStage(stage);
                        addCrimCon.setCase(detailedCase);
                        addCrimCon.setOfficer(officer);
                        addCrimCon.titleBar();
                    }
                    case "AddSus.fxml" -> {
                        AddSusCon addSusCon = loader.getController();
                        addSusCon.setStage(stage);
                        addSusCon.setCase(detailedCase);
                        addSusCon.setOfficer(officer);
                        addSusCon.titleBar();
                    }
                    case "AddWitn.fxml" -> {
                        AddWitnCon addWitnCon = loader.getController();
                        addWitnCon.setStage(stage);
                        addWitnCon.setCase(detailedCase);
                        addWitnCon.setOfficer(officer);
                        addWitnCon.titleBar();
                    }
                    case "AddEvi.fxml" -> {
                        AddEviCon addEviCon = loader.getController();
                        addEviCon.setStage(stage);
                        addEviCon.setCase(detailedCase);
                        addEviCon.setOfficer(officer);
                        addEviCon.titleBar();
                    }
                    case "RemoveCrim.fxml" -> {
                        RemoveCrimCon removeCrimCon = loader.getController();
                        removeCrimCon.setStage(stage);
                        removeCrimCon.setCase(detailedCase);
                        removeCrimCon.setOfficer(officer);
                        removeCrimCon.titleBar();
                    }
                    case "RemoveSus.fxml" -> {
                        RemoveSusCon removeSusCon = loader.getController();
                        removeSusCon.setStage(stage);
                        removeSusCon.setCase(detailedCase);
                        removeSusCon.setOfficer(officer);
                        removeSusCon.titleBar();
                    }
                    case "RemoveWitn.fxml" -> {
                        RemoveWitnCon removeWitnCon = loader.getController();
                        removeWitnCon.setStage(stage);
                        removeWitnCon.setCase(detailedCase);
                        removeWitnCon.setOfficer(officer);
                        removeWitnCon.titleBar();
                    }
                    case "RemoveEvi.fxml" -> {
                        RemoveEviCon removeEviCon = loader.getController();
                        removeEviCon.setStage(stage);
                        removeEviCon.setCase(detailedCase);
                        removeEviCon.setOfficer(officer);
                        removeEviCon.titleBar();
                    }
                }
                // Set up the stage and show the next page
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("No FXML associated with this button.");
        }
    }

    private @Nullable String getString(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String fxmlFile = null;

        if (clickedButton == AddCrim) {
            fxmlFile = "AddCrim.fxml";
            return fxmlFile;
        } else if (clickedButton == AddEvi) {
            fxmlFile = "AddEvi.fxml";
            return fxmlFile;
        } else if (clickedButton == AddWitn) {
            fxmlFile = "AddWitn.fxml";
            return fxmlFile;
        } else if (clickedButton == AddSus) {
            fxmlFile = "AddSus.fxml";
            return fxmlFile;
        } else if (clickedButton == RemoveCrim) {
            fxmlFile = "RemoveCrim.fxml";
            return fxmlFile;
        } else if (clickedButton == RemoveEvi) {
            fxmlFile = "RemoveEvi.fxml";
            return fxmlFile;
        } else if (clickedButton == RemoveWitn) {
            fxmlFile = "RemoveWitn.fxml";
            return fxmlFile;
        } else if (clickedButton == RemoveSus) {
            fxmlFile = "RemoveSus.fxml";
            return fxmlFile;
        }
        return fxmlFile;
    }

    public void Update(ActionEvent event) throws IOException {
        officer.UpdateCase(Integer.toString(this.detailedCase.CaseID), CaseType.getText(), Description.getText());
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Case Update");
        infoAlert.setHeaderText("Case Update");
        infoAlert.setContentText("Case Updated successfully.");
        infoAlert.showAndWait();
        Back(event);
    }

}
