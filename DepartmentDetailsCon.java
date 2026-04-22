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

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class DepartmentDetailsCon {
    public DepartmentDetailsCon(){}
    private Stage stage;
    private Department department;
    private Manager manager;

    @FXML
    Button minimzeBtn, closeBtn;
    @FXML
    Label Details;
    @FXML
    FlowPane Officers, Cases;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDepartment(int departmentID) {
        for(Department department1: Files.getFiles().MainDepartmentsList){
            if(department1.ID == departmentID){
                this.department = department1;
                break;
            }
        }

    }

    public void setManager(Manager manager) {
        this.manager = manager;
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

    @FXML
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

    public void setDetails(){
        Details.setText("Department  " + department.ID + " - " +department.Name + "\n  Managed By "+manager.getName());
    }

    public void loadofficersscrollpane() {
        Officers.getChildren().clear(); // Clear old items before reloading
        for (Officer off : department.Officers) {
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

    private void navigateToModifyCase(){
            try {
                // Load the next page's FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyCase.fxml"));
                BorderPane root = loader.load();

                // Get the controller of the next page
                ModifyCaseCon modifyCaseCon = loader.getController();
                modifyCaseCon.setStage(stage);
                modifyCaseCon.setManager(manager);
                modifyCaseCon.titleBar();

                // Set up the stage and show the next page
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void loadcaseidscrollpane() {
        Cases.getChildren().clear(); // Clear old items before reloading
        for(Officer officer: department.Officers)
            for (Case C : officer.OfficerCases) {
                Pane pane = new Pane();
                pane.setStyle("-fx-background-color:  #F5F5F5; -fx-border-color:  #6A1017; -fx-border-width:  0 0 2 0;");
                pane.setPrefSize(275, 35);

                Label l1 = new Label("Case ID  : " + C.CaseID + officer.getName());
                l1.setPrefSize(175, 35);
                l1.setLayoutX(5);
                l1.setLayoutY(0);
                l1.setStyle(" -fx-text-alignment: left ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

                Button Modify = getModify();
                pane.getChildren().addAll(l1, Modify);
                Cases.getChildren().add(pane);
            }
    }

    private Button getModify(){
        Button Modify = new Button("Modify");
        Modify.setStyle("-fx-background-color:  #0D2C54; -fx-border-color:  #6A1017; -fx-border-width:  3; -fx-text-alignment: center ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #F5F5F5;");
        Modify.setPrefSize(70, USE_COMPUTED_SIZE);
        Modify.setLayoutX(180);
        Modify.setLayoutY(0);
        Modify.setOnAction(event -> navigateToModifyCase());
        return Modify;
    }
}
