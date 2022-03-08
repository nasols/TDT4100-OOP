package fire;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Node;

public class FireNbNController {

    private Stage stage;
    private Scene scene;

    private int i;

    @FXML
    private Label test;

    @FXML
    private TextField username;

    @FXML
    private void handleLogin(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FireNbN.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        this.i = 69;
    }

    @FXML
    private void handleLogout(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FireLogin.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        this.i = 420;
    }


    @FXML
    private void screamName() {
        System.out.println(this.i);
        this.i++;
        //System.out.println(manager.currentUser.username);
    }
}
