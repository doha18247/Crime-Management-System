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

import java.io.IOException;

public class AssignOfficertoCaseCon {
    public AssignOfficertoCaseCon() {
    }

    Manager manager;
    private Stage stage;
    @FXML
    private Button closeBtn, minimzeBtn;
    @FXML
    private TextField SSN, CaseId;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
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

    public void Assign(ActionEvent actionEvent) throws IOException {
        boolean result = this.manager.assignCaseToOfficer(SSN.getText(), CaseId.getText());
        if(result)
            Back(actionEvent);
    }
}
