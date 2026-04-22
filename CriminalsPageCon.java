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

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class CriminalsPageCon {
    public CriminalsPageCon() {
    }

    Officer officer;
    private Stage stage;
    @FXML
    Button minimzeBtn, closeBtn, back;
    @FXML
    TextField search;
    @FXML
    FlowPane criminals;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setOfficer(Officer officer) {
        this.officer = officer;
    }

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
        officerController.setStage(stage);
        officerController.titleBar();
        officerController.setOfficer(officer);
        officerController.setHelloLabel();
        officerController.DisplayData();
        stage.setTitle("Crime Management System");
        stage.setScene(scene);
        stage.show();
    }

    public void criminaldetails(Criminal criminal, ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CriminalDetails.fxml"));
            BorderPane root = loader.load();
            Scene scene = new Scene(root);
            // Use the same stage or create a new one
            if (stage == null) {
                stage = new Stage();
            }
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            CriminalDetailsCon criminalDetailsCon = loader.getController();
            criminalDetailsCon.setStage(stage);
            criminalDetailsCon.setOfficer(officer);
            criminalDetailsCon.setCriminal(criminal);
            criminalDetailsCon.titleBar();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadscrollpane() {
        int i = 1;
        for (Criminal criminal : Files.getFiles().MainCriminalsList) {
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color:  #F5F5F5; -fx-border-color:  #6A1017; -fx-border-width:  3;");
            pane.setPrefSize(240, 200);
            Button details = getButton(criminal);
            Label title = new Label("Criminal " + i++);
            title.setPrefSize(240, 27);
            title.setLayoutX(5);
            title.setLayoutY(1);
            title.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");
            title.setContentDisplay(ContentDisplay.CENTER);

            Label l1 = new Label("SSN: " + criminal.getSSN());
            l1.setPrefSize(240, 27);
            l1.setLayoutX(5);
            l1.setLayoutY(28);
            l1.setStyle(" -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

            Label l2 = new Label("Name: " + criminal.getName());
            l2.setPrefSize(240, 27);
            l2.setLayoutX(5);
            l2.setLayoutY(55);
            l2.setStyle(" -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");
            l2.setPrefSize(240, 27);

            Label l3 = new Label("Phone Number: " + criminal.getPhoneNumber());
            l3.setPrefSize(240, 27);
            l3.setLayoutX(5);
            l3.setLayoutY(82);
            l3.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

            Label l4 = new Label("Address: " + criminal.getAddress());
            l4.setPrefSize(240, 27);
            l4.setLayoutX(5);
            l4.setLayoutY(109);
            l4.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");
            pane.getChildren().addAll(title, l1, l2, l3, l4, details);
            criminals.getChildren().add(pane);
        }
    }

    public void loadscrollpane(String SSN) {
        int i = 1;
        for (Criminal criminal : Files.getFiles().MainCriminalsList) {
            criminals.getChildren().clear(); // Clear old items before reloading
            if (SSN.isEmpty() || criminal.getSSN().equals(SSN)) {
                Pane pane = new Pane();
                pane.setStyle("-fx-background-color:  #F5F5F5; -fx-border-color:  #6A1017; -fx-border-width:  3;");
                pane.setPrefSize(240, 200);
                Button details = getButton(criminal);
                Label title = new Label("Criminal " + i++);
                title.setPrefSize(240, 27);
                title.setLayoutX(5);
                title.setLayoutY(1);
                title.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");
                title.setContentDisplay(ContentDisplay.CENTER);

                Label l1 = new Label("SSN: " + criminal.getSSN());
                l1.setPrefSize(240, 27);
                l1.setLayoutX(5);
                l1.setLayoutY(28);
                l1.setStyle(" -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

                Label l2 = new Label("Name: " + criminal.getName());
                l2.setPrefSize(240, 27);
                l2.setLayoutX(5);
                l2.setLayoutY(55);
                l2.setStyle(" -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");
                l2.setPrefSize(240, 27);

                Label l3 = new Label("Phone Number: " + criminal.getPhoneNumber());
                l3.setPrefSize(240, 27);
                l3.setLayoutX(5);
                l3.setLayoutY(82);
                l3.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

                Label l4 = new Label("Address: " + criminal.getAddress());
                l4.setPrefSize(240, 27);
                l4.setLayoutX(5);
                l4.setLayoutY(109);
                l4.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");
                pane.getChildren().addAll(title, l1, l2, l3, l4, details);
                criminals.getChildren().add(pane);
            }
        }
    }

    public void search(ActionEvent event) {
        loadscrollpane(search.getText());
    }

    private @NotNull Button getButton(Criminal criminal) {
        Button details = new Button("Details");
        details.setStyle("-fx-background-color:  #0D2C54; -fx-border-color:  #6A1017; -fx-border-width:  3; -fx-text-alignment: center ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #F5F5F5;");
        details.setPrefSize(100, USE_COMPUTED_SIZE);
        details.setLayoutX(133);
        details.setLayoutY(158);
        details.setOnAction(event -> {
            try {
                criminaldetails(criminal, event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return details;
    }


}
