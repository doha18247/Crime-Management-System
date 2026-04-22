import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyCaseCon {
    public ModifyCaseCon() {
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
    }

    public void Remove(ActionEvent actionEvent) throws IOException {
        boolean result = this.manager.RemoveOfficer(SSN.getText(), CaseId.getText());
        if(result)
            Back(actionEvent);
    }
}
