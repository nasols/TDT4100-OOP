package fire;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.Node;


public class FireNBNController {

    private Stage stage;
    private Scene scene;
    private Manager manager;

    //Login scene
    @FXML
    private TextField username;


    //Main scene
    @FXML
    private TextField title;
    @FXML
    private TextArea description;
    @FXML
    private Label infoLabel;
    @FXML
    private ListView<String> rentalPlaceList = new ListView<>();

    private List<String> rentalList = new ArrayList<>(); //temp


    @FXML
    public void initialize() {
        this.manager = new Manager();
        updateRentalPlaceList(); //manager.getRentalList
        //updateInfoLabel();
    }

    @FXML
    private void handleLogin(ActionEvent e) throws IOException {
        manager.login(username.getText());
        updateRentalPlaceList();

        Parent root = FXMLLoader.load(getClass().getResource("FireNbN.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        updateRentalPlaceList();

    }


    @FXML
    private void handleLogout(ActionEvent e) throws IOException {
        updateRentalPlaceList2();
        Parent root = FXMLLoader.load(getClass().getResource("FireLogin.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void handleAddPlace() {
        //  TODO: Metode i Manager som validerer input for å leie sted
        if (true) {
            //  TODO: Manager.newRentalPlace skal legge til currentUser automatisk
            manager.newRentalPlace(title.getText(), description.getText(), "2022-09-09", "2022-10-10");
        }
        //else {
        //    info.setText("Feil ved oppretting av utleiested");
        //}
    }

    private void updateInfoLabel() {
        infoLabel.setText("Logget inn som " + manager.getCurrentUsername());
    }


    private void updateRentalPlaceList() {
        rentalList.add("Test1 fwfwe \n 1");
        rentalList.add("Test2");
        rentalList.add("Test3");
        rentalList.add("Test4");
        rentalList.add("Test5");
        rentalList.add("Test6");
        rentalList.add("Test7");
        rentalList.add("Test8");
        rentalList.add("Test9");
        rentalList.add("Test10");
        rentalPlaceList.getItems().setAll(rentalList);
    }
    private void updateRentalPlaceList2() {
        rentalList.add("Test1 fwfwe \n 11111111111111111111111111111111111111");
        rentalList.add("Test2");
        rentalList.add("Test3");
        rentalList.add("Test4");
        rentalList.add("Test5");
        rentalList.add("Test6");
        rentalList.add("Test7");
        rentalList.add("Test8");
        rentalList.add("Test9");
        rentalList.add("Test10");
        rentalPlaceList.getItems().setAll(rentalList);
    }
}
