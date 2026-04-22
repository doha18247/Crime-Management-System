import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.StringJoiner;

public class CriminalDetailsCon {
    public CriminalDetailsCon() {
    }

    Officer officer;
    Criminal criminal;
    private Stage stage;
    @FXML
    Button minimzeBtn, closeBtn, back;
    @FXML
    private Label Address, Age, Current, Danger, Gender, Name, Phone, SSN, crimes;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setOfficer(Officer officer) {
        this.officer = officer;
    }

    public void setCriminal(Criminal criminal) {
        this.criminal = criminal;
        updateUI();
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

    private void updateUI() {
        if (criminal != null) {
            SSN.setText("Criminal SSN: " + criminal.getSSN());
            Name.setText("Criminal Name: " + criminal.getName());
            Age.setText("Criminal Age: " + criminal.getAge());
            Gender.setText("Criminal Gender: " + criminal.getGender());
            Phone.setText("Phone Number: " + criminal.getPhoneNumber());
            Danger.setText("Danger Level: " + criminal.Level);
            Address.setText("Address: " + criminal.getAddress());
            Current.setText("Current Location: " + criminal.getCurrentLocation());

            StringJoiner Crimes = new StringJoiner(", ");
            for (Case aCase : criminal.CrimesCommitted) {
                Crimes.add(aCase.CaseType);
            }
            crimes.setText("Crimes Committed: " + Crimes);
        }
    }

    public void Back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CriminalsPage.fxml"));
        BorderPane root = loader.load();
        Scene scene = new Scene(root);
        // Use the same stage or create a new one
        if (stage == null) {
            stage = new Stage();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        CriminalsPageCon criminalsPageCon = loader.getController();
        criminalsPageCon.setStage(stage);
        criminalsPageCon.setOfficer(officer);
        criminalsPageCon.titleBar();
        criminalsPageCon.loadscrollpane();
        stage.setScene(scene);
        stage.show();
    }
}
