import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginCon {
    public LoginCon() {
    }

    private Stage stage;

    @FXML
    private Button minimzeBtn, closeBtn;
    @FXML
    ImageView FCIS, MOI;
    @FXML
    private PasswordField password;
    @FXML
    private TextField username;

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

    public void setPics() {
        Image fcis = new Image("./Images/FCISRemoved.png");
        Image moi = new Image("./Images/MOI.png");

        FCIS.setImage(fcis);
        MOI.setImage(moi);
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.initStyle(StageStyle.UTILITY); // Optional: makes the alert window minimalistic
        alert.showAndWait(); // Show the alert and wait for the user to acknowledge
    }

    public void Authenticate(ActionEvent event) throws IOException {
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();
        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Login Error", "Missing Credentials", "Please enter both username and password.");
            return;
        }
        FXMLLoader loader;
        boolean isAuthenticated = false;
        // Check Officer List
        for (Officer user : Files.getFiles().MainOfficersList) {
            if (enteredUsername.equals(user.getUsername()) && enteredPassword.equals(user.getPassword())) {
                loader = new FXMLLoader(getClass().getResource("OfficerHomePage.fxml"));
                loadOfficerPage(loader, event, user);
                return;
            }
        }
        // Check Manager List if not authenticated yet
        for (Manager user : Files.getFiles().MainManagersList) {
            if (enteredUsername.equals(user.getUsername()) && enteredPassword.equals(user.getPassword())) {
                loader = new FXMLLoader(getClass().getResource("ManagerHomePage.fxml"));
                loadManagerPage(loader, event, user);
                return;
            }
        }
        // Check Admin credentials if not authenticated yet
        if (enteredUsername.equals(Admin.getUsername()) && enteredPassword.equals(Admin.getPassword())) {
            loader = new FXMLLoader(getClass().getResource("AdminHomePage.fxml"));
            loadAdminPage(loader, event);
            return;
        }
        showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid Credentials", "The username or password you entered is incorrect. Please try again.");
    }

    private void loadManagerPage(FXMLLoader loader, ActionEvent event, Manager manager) throws IOException {
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

        stage.setScene(scene);
        stage.show();
    }

    private void loadOfficerPage(FXMLLoader loader, ActionEvent event, Officer officer) throws IOException {
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

    private void loadAdminPage(FXMLLoader loader, ActionEvent event) throws IOException {
        BorderPane root = loader.load();
        Scene scene = new Scene(root);

        // Use the same stage or create a new one
        if (stage == null) {
            stage = new Stage();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AdminCon adminCon = loader.getController();
        adminCon.setStage(stage);
        adminCon.loadofficersscrollpane();
        adminCon.titleBar();
        adminCon.setHelloLabel();
        stage.setTitle("Crime Management System");
        stage.setScene(scene);
        stage.show();
    }
}
