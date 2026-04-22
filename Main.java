import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.Scanner;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {

            File adminFile = new File("Files\\Admin.txt");
            Scanner scan = new Scanner(adminFile);
            String username = scan.nextLine();
            String password = scan.nextLine();
            String name = scan.nextLine();

            Files.setFile();

            Files.read();

            // Set the Admin
            Admin.setAdmin(username, password, name);

//            {
//                SpecialCitizen victim1 = new SpecialCitizen("12345678910123", "Youssef Abbass", 18, Citizen.Gender.MALE, "01010202031", "Giza", "Victim is murdered by knife", SpecialCitizen.Relation.Victim);
//                Case c1 = new Case(101, "Theft", victim1);
//                Files.getFiles().MainCasesList.add(c1);
//                Department d1 = new Department(1, "FBI");
//                Files.getFiles().MainDepartmentsList.add(d1);
//                Manager M1 = new Manager("03214524617842", "Amr Maged", 50, Citizen.Gender.MALE, "01106969852", "Giza", "12000",  "Amrmaged", "Amr12345");
//                Files.getFiles().MainManagersList.add(M1);
//                Admin.getAdmin().AssignManager("03214524617842", 1);
//                Officer Off1 = new Officer("30528467215468", "Khattab Mohamed", 25, Citizen.Gender.MALE, "01101540646", "Giza", "8000", "gedoKhattab", "Gedo1234");
//                Files.getFiles().MainOfficersList.add(Off1);
//
//                M1.assignCaseToOfficer("30528467215468", "101");
//                M1.assignOfficerToDepartment("30528467215468");
//                M1.UpdateCaseDetails("101", "12346891582110", "Ahmed Fouad", "30", Citizen.Gender.MALE, "01123456789", "Cairo", "I saw a theft today at the mall.");
//
//                Off1.AddCriminal(101, "68532456985214", "Mariam Mohamed", "21", Citizen.Gender.FEMALE, "01201540646", "Giza", "Cairo");
//                Off1.AddEvidence("110", "Camera Footage", "Video", 101);
//                Off1.AddSuspect(101, "32465894225613", "Shahd Abdallah", "21", Citizen.Gender.FEMALE, "01257641238", "Giza", "Suspect was at the ice cream shop.");
//                Off1.AddWitness(101, "34625971244331", "Salma Aly", "27", Citizen.Gender.FEMALE, "01105246785", "Cairo", "I saw her snitching his bag.", "103");
//                Off1.UpdateCase("101", "Theft", "Youssef Abbass has got theft in the mall in an ice cream shop. ");
//
//                System.out.println(Off1.OfficerCases.toString());
//            }

            stage.initStyle(StageStyle.UNDECORATED);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            BorderPane root = loader.load();
            LoginCon login = loader.getController();
            login.setStage(stage);
            login.setPics();
            login.titleBar();
            login.setPics();

            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            stage.show();
        } catch (Exception e) {
            Files.write();
            e.printStackTrace();
        }
    }

}