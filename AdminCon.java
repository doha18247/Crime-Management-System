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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class AdminCon {
    public AdminCon() {
    }

    static Files file = Files.getFiles();
    private Stage stage;
    private final StringProperty helloLabel = new SimpleStringProperty("Hello " + Admin.getName());
    @FXML
    Button closeBtn, minimzeBtn, officers, managers, cases, departments;
    @FXML
    Label HelloLabel;
    @FXML
    private FlowPane flow;

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

    public void navigateCreateOfficer(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateOfficer.fxml"));
            BorderPane root = loader.load();

            CreateOfficerCon createOfficerCon = loader.getController();
            createOfficerCon.setStage(stage);
            createOfficerCon.titleBar();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void navigateCreateCase(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateCase.fxml"));
            BorderPane root = loader.load();

            CreateCaseCon createCaseCon = loader.getController();
            createCaseCon.setStage(stage);
            createCaseCon.titleBar();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void navigateCreateManager(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateManager.fxml"));
            BorderPane root = loader.load();

            CreateManagerCon createManagerCon = loader.getController();
            createManagerCon.setStage(stage);
            createManagerCon.titleBar();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void navigateCreateDep(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateDep.fxml"));
            BorderPane root = loader.load();

            CreateDepCon createDepCon = loader.getController();
            createDepCon.setStage(stage);
            createDepCon.titleBar();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void navigateToEditOfficer(Officer officer, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditOfficer.fxml"));
            BorderPane root = loader.load();

            EditOfficerCon editOfficerCon = loader.getController();
            editOfficerCon.setStage(stage);
            editOfficerCon.setOfficer(officer);
            editOfficerCon.setData(officer);
            editOfficerCon.titleBar();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadofficersscrollpane() {
        managers.setStyle("-fx-background-color:  #F5F5F5; -fx-text-fill: #0D2C54;-fx-border-color:  #6A1017; -fx-border-width: 2;");
        cases.setStyle("-fx-background-color:  #F5F5F5; -fx-text-fill: #0D2C54;-fx-border-color:  #6A1017; -fx-border-width: 2;");
        departments.setStyle("-fx-background-color:  #F5F5F5; -fx-text-fill: #0D2C54;-fx-border-color:  #6A1017; -fx-border-width: 2;");
        officers.setStyle("-fx-background-color:  #0D2C54; -fx-text-fill: #F5F5F5;-fx-border-color:  #6A1017; -fx-border-width: 2;");
        flow.getChildren().clear(); // Clear old items before reloading
        for (Officer off : file.MainOfficersList) {
            Pane pane = new Pane();
            GridPane labels = new GridPane(445, 40);
            labels.setHgap(4);
            labels.setGridLinesVisible(true);
            pane.setStyle("-fx-background-color:  #F5F5F5; -fx-border-color:  #6A1017; -fx-border-width:  0 0 1 0;");
            pane.setPrefSize(585, 40);

            Label l1 = new Label(off.getName());
            l1.setPrefSize(167, 40);
            l1.setLayoutX(5);
            l1.setLayoutY(0);
            l1.setStyle(" -fx-text-alignment: left ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

            Label l2 = new Label("SSN: " + off.getSSN());
            l2.setPrefSize(150, 40);
            l2.setLayoutX(174);
            l2.setLayoutY(0);
            l2.setStyle(" -fx-text-alignment: left ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

            Label l3 = new Label("Department " + off.department_ID);
            l3.setPrefSize(119, 40);
            l3.setLayoutX(330);
            l3.setLayoutY(0);
            l3.setStyle(" -fx-text-alignment: left ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

            Button delete = deleteOfficer(off);
            Button edit = editOfficer(off);

            labels.add(l1, 0, 0);
            labels.add(l2, 1, 0);
            labels.add(l3, 2, 0);

            pane.getChildren().addAll(labels, edit, delete);
            flow.getChildren().add(pane);
        }
    }

    private @NotNull Button editOfficer(Officer officer) {
        Button edit = new Button("Modify");
        edit.setStyle("-fx-background-color:  #0D2C54; -fx-border-color:  #6A1017; -fx-border-width:  3; -fx-text-alignment: center ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #F5F5F5;");
        edit.setPrefSize(70, USE_COMPUTED_SIZE);
        edit.setLayoutX(444);
        edit.setLayoutY(0);

        // Set the action to navigate to the next page and pass the selected case
        edit.setOnAction(event -> navigateToEditOfficer(officer, event));
        return edit;
    }

    private @NotNull Button deleteOfficer(Officer officer) {
        Button delete = new Button("Delete");
        delete.setStyle("-fx-background-color:  #0D2C54; -fx-border-color:  #6A1017; -fx-border-width:  3; -fx-text-alignment: center ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #F5F5F5;");
        delete.setPrefSize(70, USE_COMPUTED_SIZE);
        delete.setLayoutX(511);
        delete.setLayoutY(0);
        // Set the action to navigate to the next page and pass the selected case
        delete.setOnAction(event -> {
            Admin.getAdmin().DeleteOfficer(officer.getSSN());
            loadofficersscrollpane();
        });
        return delete;
    }

    public void navigateToEditManager(Manager manager, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditManager.fxml"));
            BorderPane root = loader.load();

            EditManagerCon editManagerCon = loader.getController();
            editManagerCon.setStage(stage);
            editManagerCon.setData(manager);
            editManagerCon.titleBar();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToAssignManager(Department department, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AssignManager.fxml"));
            BorderPane root = loader.load();

            AssignManagerCon assignManagerCon = loader.getController();
            assignManagerCon.setStage(stage);
            assignManagerCon.setDepartment(department);
            assignManagerCon.titleBar();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadmanagersscrollpane() {
        officers.setStyle("-fx-background-color:  #F5F5F5; -fx-text-fill: #0D2C54;-fx-border-color:  #6A1017; -fx-border-width: 2;");
        cases.setStyle("-fx-background-color:  #F5F5F5; -fx-text-fill: #0D2C54;-fx-border-color:  #6A1017; -fx-border-width: 2;");
        departments.setStyle("-fx-background-color:  #F5F5F5; -fx-text-fill: #0D2C54;-fx-border-color:  #6A1017; -fx-border-width: 2;");
        managers.setStyle("-fx-background-color:  #0D2C54; -fx-text-fill: #F5F5F5;-fx-border-color:  #6A1017; -fx-border-width: 2;");
        flow.getChildren().clear(); // Clear old items before reloading
        for (Manager manager : file.MainManagersList) {
            Pane pane = new Pane();
            GridPane labels = new GridPane(445, 40);
            labels.setHgap(4);
            labels.setGridLinesVisible(true);
            pane.setStyle("-fx-background-color:  #F5F5F5; -fx-border-color:  #6A1017; -fx-border-width:  0 0 2 0;");
            pane.setPrefSize(585, 40);

            Label l1 = new Label(manager.getName());
            l1.setPrefSize(167, 40);
            l1.setLayoutX(5);
            l1.setLayoutY(0);
            l1.setStyle(" -fx-text-alignment: left ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

            Label l2 = new Label("SSN: " + manager.getSSN());
            l2.setPrefSize(150, 40);
            l2.setLayoutX(174);
            l2.setLayoutY(0);
            l2.setStyle(" -fx-text-alignment: left ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

            Label l3 = new Label("Department " + manager.DepartmentID);
            l3.setPrefSize(119, 40);
            l3.setLayoutX(330);
            l3.setLayoutY(0);
            l3.setStyle(" -fx-text-alignment: left ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

            Button delete = deleteManager(manager);
            Button edit = editManager(manager);

            labels.add(l1, 0, 0);
            labels.add(l2, 1, 0);
            labels.add(l3, 2, 0);

            pane.getChildren().addAll(labels, delete, edit);
            flow.getChildren().add(pane);
        }
    }

    private @NotNull Button editManager(Manager manager) {
        Button edit = new Button("Modify");
        edit.setStyle("-fx-background-color:  #0D2C54; -fx-border-color:  #6A1017; -fx-border-width:  3; -fx-text-alignment: center ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #F5F5F5;");
        edit.setPrefSize(70, USE_COMPUTED_SIZE);
        edit.setLayoutX(444);
        edit.setLayoutY(0);

        // Set the action to navigate to the next page and pass the selected case
        edit.setOnAction(event -> navigateToEditManager(manager, event));
        return edit;
    }

    private @NotNull Button deleteManager(Manager manager) {
        Button delete = new Button("Delete");
        delete.setStyle("-fx-background-color:  #0D2C54; -fx-border-color:  #6A1017; -fx-border-width:  3; -fx-text-alignment: center ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #F5F5F5;");
        delete.setPrefSize(70, USE_COMPUTED_SIZE);
        delete.setLayoutX(511);
        delete.setLayoutY(0);
        // Set the action to navigate to the next page and pass the selected case
        delete.setOnAction(event -> {
            Admin.getAdmin().DeleteManager(manager.getName());
            loadmanagersscrollpane();
        });
        return delete;
    }

    public void loaddepartmentsscrollpane() {
        officers.setStyle("-fx-background-color:  #F5F5F5; -fx-text-fill: #0D2C54;-fx-border-color:  #6A1017; -fx-border-width: 2;");
        managers.setStyle("-fx-background-color:  #F5F5F5; -fx-text-fill: #0D2C54;-fx-border-color:  #6A1017; -fx-border-width: 2;");
        cases.setStyle("-fx-background-color:  #F5F5F5; -fx-text-fill: #0D2C54;-fx-border-color:  #6A1017; -fx-border-width: 2;");
        departments.setStyle("-fx-background-color:  #0D2C54; -fx-text-fill: #F5F5F5;-fx-border-color:  #6A1017; -fx-border-width: 2;");
        flow.getChildren().clear(); // Clear old items before reloading
        for (Department dep : file.MainDepartmentsList) {
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color:  #F5F5F5; -fx-border-color:  #6A1017; -fx-border-width:  0 0 2 0;");
            pane.setPrefSize(585, 40);

            Label l1 = new Label("Department: " + dep.ID + " - " + dep.Name + " - Manager SSN: " + dep.getManagerID());
            l1.setPrefSize(450, 40);
            l1.setLayoutX(5);
            l1.setLayoutY(0);
            l1.setStyle(" -fx-text-alignment: left ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

            Button Assign = AssignManager(dep);

            pane.getChildren().addAll(l1, Assign);
            flow.getChildren().add(pane);
        }
    }

    private @NotNull Button AssignManager(Department department) {
        Button Assign = new Button("Assign");
        Assign.setStyle("-fx-background-color:  #0D2C54; -fx-border-color:  #6A1017; -fx-border-width:  3; -fx-text-alignment: center ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #F5F5F5;");
        Assign.setPrefSize(70, USE_COMPUTED_SIZE);
        Assign.setLayoutX(500);
        Assign.setLayoutY(0);

        // Set the action to navigate to the next page and pass the selected case
        Assign.setOnAction(event -> navigateToAssignManager(department, event));
        return Assign;
    }

    public void loadcaseidscrollpane() {
        officers.setStyle("-fx-background-color:  #F5F5F5; -fx-text-fill: #0D2C54;-fx-border-color:  #6A1017; -fx-border-width: 2;");
        managers.setStyle("-fx-background-color:  #F5F5F5; -fx-text-fill: #0D2C54;-fx-border-color:  #6A1017; -fx-border-width: 2;");
        departments.setStyle("-fx-background-color:  #F5F5F5; -fx-text-fill: #0D2C54;-fx-border-color:  #6A1017; -fx-border-width: 2;");
        cases.setStyle("-fx-background-color:  #0D2C54; -fx-text-fill: #F5F5F5;-fx-border-color:  #6A1017; -fx-border-width: 2;");
        flow.getChildren().clear(); // Clear old items before reloading
        for (Case C : file.MainCasesList) {
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color:  #F5F5F5; -fx-border-color:  #6A1017; -fx-border-width:  0 0 2 0;");
            pane.setPrefSize(585, 40);

            Label l1 = new Label("Case: " + C.CaseID + " - " + C.CaseType + " - " + C.Victim.getName());
            l1.setPrefSize(580, 40);
            l1.setLayoutX(5);
            l1.setLayoutY(0);
            l1.setStyle(" -fx-text-alignment: left ; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0d2c54;");

            pane.getChildren().add(l1);
            flow.getChildren().add(pane);
        }
    }

}
