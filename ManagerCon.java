import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerCon {
    public ManagerCon() {
    }

    Manager manager;
    private Stage stage;
    private final StringProperty helloLabel = new SimpleStringProperty();
    @FXML
    Button closeBtn, minimzeBtn, DepDetails;
    @FXML
    Label HelloLabel;
    @FXML
    private FlowPane Officers, Cases;

    public void setStage(Stage stage) {
        this.stage = stage;
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

    public void setManager(Manager manager) {
        this.manager = manager;
        helloLabel.set("Hello Manager " + manager.getName());
    }

    public void setHelloLabel() {
        HelloLabel.textProperty().bind(helloLabel);
    }

    public void logout(ActionEvent event) throws IOException {
        // Save Data
        Files.write();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        BorderPane root = loader.load();
        LoginCon loginCon = loader.getController();
        loginCon.setStage(stage);
        loginCon.setPics();
        loginCon.titleBar();

        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        stage.show();
    }

    @FXML
    private void navigateToCasePage(ActionEvent event) {
        try {
            // Load the next page's FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AssignOfficertoCase.fxml"));
            BorderPane root = loader.load();

            // Get the controller of the next page
            AssignOfficertoCaseCon assignOfficertoCaseCon = loader.getController();
            assignOfficertoCaseCon.setStage(stage);
            assignOfficertoCaseCon.setManager(manager);
            assignOfficertoCaseCon.titleBar();

            // Set up the stage and show the next page
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToDepPage(ActionEvent actionEvent) {
            try {
                // Load the next page's FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AssignOfficertoDep.fxml"));
                BorderPane root = loader.load();

                // Get the controller of the next page
                AssignOfficertoDepCon assignOfficertoDepCon = loader.getController();
                assignOfficertoDepCon.setStage(stage);
                assignOfficertoDepCon.setManager(manager);
                assignOfficertoDepCon.titleBar();

                // Set up the stage and show the next page
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void navigateToUpdateCase(ActionEvent actionEvent) {

        {
            try {
                // Load the next page's FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddComplainant.fxml"));
                BorderPane root = loader.load();

                // Get the controller of the next page
                AddComplainantCon addComplainantCon = loader.getController();
                addComplainantCon.setStage(stage);
                addComplainantCon.setManager(manager);
                addComplainantCon.titleBar();

                // Set up the stage and show the next page
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void navigateToDepDetails(){
        try {
            // Load the next page's FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DepartmentDetails.fxml"));
            BorderPane root = loader.load();

            // Get the controller of the next page
            DepartmentDetailsCon departmentDetailsCon = loader.getController();
            departmentDetailsCon.setStage(stage);
            departmentDetailsCon.setDepartment(manager.DepartmentID);
            departmentDetailsCon.setManager(manager);
            departmentDetailsCon.setDetails();
            departmentDetailsCon.loadcaseidscrollpane();
            departmentDetailsCon.loadofficersscrollpane();
            departmentDetailsCon.titleBar();

            // Set up the stage and show the next page
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadofficersscrollpane() {
        Officers.getChildren().clear(); // Clear old items before reloading
        for (Officer off : Files.getFiles().MainOfficersList) {
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color:  #F5F5F5; -fx-border-color:  #6A1017; -fx-border-width:  0 0 2 0;");
            pane.setPrefSize(267, 35);

            Label l1 = new Label(off.getName() + "  :  " + off.getSSN());
            l1.setPrefSize(267, 35);
            l1.setLayoutX(5);
            l1.setLayoutY(0);
            l1.setStyle(" -fx-text-alignment: left ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

            pane.getChildren().add(l1);
            Officers.getChildren().add(pane);
        }
    }

    public void loadcaseidscrollpane() {
        Cases.getChildren().clear(); // Clear old items before reloading
        for (Case C : Files.getFiles().MainCasesList) {
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color:  #F5F5F5; -fx-border-color:  #6A1017; -fx-border-width:  0 0 2 0;");
            pane.setPrefSize(275, 35);

            Label l1 = new Label("Case ID  : " + C.CaseID);
            l1.setPrefSize(275, 35);
            l1.setLayoutX(5);
            l1.setLayoutY(0);
            l1.setStyle(" -fx-text-alignment: left ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

            pane.getChildren().add(l1);
            Cases.getChildren().add(pane);
        }
    }

}
