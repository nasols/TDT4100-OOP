package fire;

import java.io.IOException;

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


public class FireNbNController {

    private Stage stage;
    private Scene scene;

    //Login scene
    @FXML
    private TextField username;


    //Main scene
    @FXML
    private TextField title;
    @FXML
    private TextArea description;
    @FXML
    private Label info;
    @FXML
    private ListView<String> rentalPlaceList = new ListView<>();


    @FXML
    private void handleLogin(ActionEvent e) throws IOException {
        Manager.login(username.getText());

        //  TODO: En metode i manager som returnerer en liste med strenger som beskriver utleiestedet
        for (int i = 0; i < Manager.rentalPlaces.size() + 3; i++) {
            rentalPlaceList.getItems().add("test");
        }

        Parent root = FXMLLoader.load(getClass().getResource("FireNbN.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        

    }


    @FXML
    private void handleLogout(ActionEvent e) throws IOException {
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
            Manager.newRentalPlace(title.getText(), description.getText(), "2022-09-09", "2022-10-10");
        }
        //else {
        //    info.setText("Feil ved oppretting av utleiested");
        //}
    }
}
