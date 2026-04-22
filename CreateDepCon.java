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

public class CreateDepCon {
    public CreateDepCon() {
    }

    Stage stage;

    @FXML
    Button back, CreateBtn, minimizeBtn, closeBtn;
    @FXML
    private TextField Name, ID;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void titleBar() {
        if (stage != null) {
            // Add minimize and close buttons
            minimizeBtn.setOnAction(e -> stage.setIconified(true));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHomePage.fxml"));
        BorderPane root = loader.load();
        Scene scene = new Scene(root);
        // Use the same stage or create a new one
        if (stage == null) {
            stage = new Stage();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AdminCon adminCon = loader.getController();
        adminCon.setStage(stage);
        adminCon.loaddepartmentsscrollpane();
        adminCon.titleBar();
        stage.setTitle("Crime Management System");
        stage.setScene(scene);
        stage.show();
    }

    public void Create(ActionEvent e) throws IOException {
        boolean result = Admin.getAdmin().CreateDepartment(ID.getText(), Name.getText());
        if (result) Back(e);
    }
}