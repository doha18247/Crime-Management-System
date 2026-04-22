import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class CasesPageCon {
    public CasesPageCon() {
    }

    Officer officer;
    private Stage stage;
    @FXML
    Button minimzeBtn, closeBtn, back;
    @FXML
    TextField search;
    @FXML
    FlowPane cases;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setOfficer(Officer officer) {
        this.officer = officer;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OfficerHomePage.fxml"));
        BorderPane root = loader.load();
        Scene scene = new Scene(root);

        // Use the same stage or create a new one
        if (stage == null) {
            stage = new Stage();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        OfficerCon officerController = loader.getController();
        officerController.setOfficer(officer);
        officerController.titleBar();
        officerController.setHelloLabel();
        officerController.DisplayData();
        officerController.setStage(stage);
        stage.setScene(scene);
        stage.show();
    }

    private void navigateToDetailsPage(Case detailedCase, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CaseDetails.fxml"));
            BorderPane root = loader.load();

            CaseDetailsCon caseDetailsCon = loader.getController();

            caseDetailsCon.setStage(stage);
            caseDetailsCon.setOfficer(officer);
            caseDetailsCon.setCase(detailedCase);
            caseDetailsCon.titleBar();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToUpdatePage(Case detailedCase, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CaseUpdate.fxml"));
            BorderPane root = loader.load();

            CaseUpdateCon caseUpdateCon = loader.getController();

            caseUpdateCon.setStage(stage);
            caseUpdateCon.setOfficer(officer);
            caseUpdateCon.setCase(detailedCase);
            caseUpdateCon.setCaseDetails();
            caseUpdateCon.titleBar();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadscrollpane() {
        cases.getChildren().clear(); // Clear old items before reloading
        int i = 1;

        for (Case acase : Files.getFiles().MainCasesList) {
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color: #F5F5F5; -fx-border-color: #6A1017; -fx-border-width: 3;");
            pane.setPrefSize(240, 200);

            // Create new components for this pane
            Button details = getDetails(acase);

            Label title = new Label("Case " + i++);
            title.setPrefSize(240, 27);
            title.setLayoutX(5);
            title.setLayoutY(1);
            title.setStyle("-fx-text-alignment: center; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");
            title.setContentDisplay(ContentDisplay.CENTER);

            Label l1 = new Label("Case ID: " + acase.CaseID);
            l1.setPrefSize(240, 27);
            l1.setLayoutX(5);
            l1.setLayoutY(28);
            l1.setStyle("-fx-text-alignment: left; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

            Label l2 = new Label("Case Type: " + acase.CaseType);
            l2.setPrefSize(240, 27);
            l2.setLayoutX(5);
            l2.setLayoutY(55);
            l2.setStyle("-fx-text-alignment: left; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

            StringBuilder suspects = new StringBuilder();
            for (int j = 0; j < acase.Suspects.size(); j++) {
                suspects.append(" - ").append(acase.Suspects.get(j).getName());
            }
            Label l3 = new Label("Suspects: " + suspects);
            l3.setPrefSize(240, 27);
            l3.setLayoutX(5);
            l3.setLayoutY(82);
            l3.setStyle("-fx-text-alignment: left; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

            StringBuilder criminals = new StringBuilder();
            for (int j = 0; j < acase.Criminals.size(); j++) {
                criminals.append(" - ").append(acase.Criminals.get(j).getName());
            }
            Label l4 = new Label("Criminals: " + criminals);
            l4.setPrefSize(240, 27);
            l4.setLayoutX(5);
            l4.setLayoutY(109);
            l4.setStyle("-fx-text-alignment: left; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

            // Add basic components to the pane
            pane.getChildren().addAll(title, l1, l2, l3, l4, details);

            // Check if the case is associated with the officer and add the update button if needed
            for (Case case1 : officer.OfficerCases) {
                if (Objects.equals(case1.CaseID, acase.CaseID)) {
                    Button update = getUpdate(acase);
                    pane.getChildren().add(update);
                    break; // Avoid unnecessary iterations after finding the case
                }
            }

            // Add the pane to the scroll container
            cases.getChildren().add(pane);
        }
    }

    public void loadscrollpane(String ID) {
        cases.getChildren().clear(); // Clear old items before reloading
        int i = 1;
        for (Case acase : Files.getFiles().MainCasesList) {
            if (ID.isEmpty() || acase.CaseID == Integer.parseInt(ID)) {
                Pane pane = new Pane();
                pane.setStyle("-fx-background-color:  #F5F5F5; -fx-border-color:  #6A1017; -fx-border-width:  3;");
                pane.setPrefSize(240, 200);
                Button details = getDetails(acase);

                Label title = new Label("Case " + i++);
                title.setPrefSize(240, 27);
                title.setLayoutX(5);
                title.setLayoutY(1);
                title.setStyle("-fx-text-alignment: center ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");
                title.setContentDisplay(ContentDisplay.CENTER);

                Label l1 = new Label("Case ID: " + acase.CaseID);
                l1.setPrefSize(240, 27);
                l1.setLayoutX(5);
                l1.setLayoutY(28);
                l1.setStyle(" -fx-text-alignment: left ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

                Label l2 = new Label("Case Type: " + acase.CaseType);
                l2.setPrefSize(240, 27);
                l2.setLayoutX(5);
                l2.setLayoutY(55);
                l2.setStyle(" -fx-text-alignment: left ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");
                l2.setPrefSize(250, 27);
                StringBuilder suspects = new StringBuilder();
                for (int j = 0; j < acase.Suspects.size(); j++) {
                    suspects.append(" - ").append(acase.Suspects.get(j).getName());
                }
                Label l3 = new Label("Suspects: " + suspects);
                l3.setPrefSize(240, 27);
                l3.setLayoutX(5);
                l3.setLayoutY(82);
                l3.setStyle("-fx-text-alignment: left ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

                StringBuilder criminals = new StringBuilder();
                for (int j = 0; j < acase.Criminals.size(); j++) {
                    criminals.append(" - ").append(acase.Criminals.get(j).getName());
                }
                Label l4 = new Label("Criminals: " + criminals);
                l4.setPrefSize(240, 27);
                l4.setLayoutX(5);
                l4.setLayoutY(109);
                l4.setStyle("-fx-text-alignment: left ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");
                for (Case case1 : officer.OfficerCases) {
                    if (Objects.equals(case1.CaseID, acase.CaseID)) {
                        Button update = getUpdate(acase);
                        pane.getChildren().addAll(title, l1, l2, l3, l4, details, update);
                    } else {
                        pane.getChildren().addAll(title, l1, l2, l3, l4, details);
                    }
                }
                cases.getChildren().add(pane);
            }
        }
    }

    public void search(ActionEvent event) {
        loadscrollpane(search.getText());
    }

    private @NotNull Button getUpdate(Case acase) {
        Button update = new Button("Update");
        update.setStyle("-fx-background-color:  #0D2C54; -fx-border-color:  #6A1017; -fx-border-width:  3; -fx-text-alignment: center ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #F5F5F5;");
        update.setPrefSize(100, USE_COMPUTED_SIZE);
        update.setLayoutX(12);
        update.setLayoutY(146);
        // Set the action to navigate to the next page and pass the selected case
        update.setOnAction(event -> navigateToUpdatePage(acase, event));
        return update;
    }

    private @NotNull Button getDetails(Case acase) {
        Button details = new Button("Details");
        details.setStyle("-fx-background-color:  #0D2C54; -fx-border-color:  #6A1017; -fx-border-width:  3; -fx-text-alignment: center ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #F5F5F5;");
        details.setPrefSize(100, USE_COMPUTED_SIZE);
        details.setLayoutX(128);
        details.setLayoutY(146);

        // Set the action to navigate to the next page and pass the selected case
        details.setOnAction(event -> navigateToDetailsPage(acase, event));
        return details;
    }
}
