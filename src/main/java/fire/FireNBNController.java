package fire;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;


public class FireNBNController {

    private Manager manager;
    private List<String> rentalList = new ArrayList<>(); //temp

    @FXML
    private TextField username, title, daysRenting;
    @FXML
    private TextArea description;
    @FXML
    private Label infoLabel;
    @FXML
    private ListView<String> rentalPlaceList;
    @FXML
    private VBox mainBox, newRentalBox;
    @FXML
    private HBox loginBox;
    @FXML
    private Button logoutButton;
    @FXML
    private DatePicker avaliableDateStart, avaliableDateEnd, rentStart;


    @FXML
    public void initialize() {
        this.manager = new Manager();
        initVisible();
    }

    @FXML
    private void handleLogin() {
        manager.login(username.getText());
        updateRentalPlaceList();
        updateInfoLabel("Logget inn som " + manager.getCurrentUsername());
        toggleVisible();
    }


    @FXML
    private void handleLogout() {
        updateInfoLabel("Velkommen!");
        toggleVisible();
    }


    @FXML
    private void handleAddPlace() {
        if(title.getText() == "") {
            showErrorMessage("Feil verdier ved oppretting av nytt sted");
        }
        else {
            //manager.newRentalPlace(title.getText(), description.getText(), avaliableDateStart.getValue().toString(), avaliableDateEnd.getValue().toString());
            rentalList.add(title.getText());
        }
        
        
        //  TODO: Metode i Manager som validerer input for å leie sted
        //if (true) {
            //  TODO: Manager.newRentalPlace skal legge til currentUser automatisk
        //    manager.newRentalPlace(title.getText(), description.getText(), "2022-09-09", "2022-10-10");
        //}
        //else {
        //    info.setText("Feil ved oppretting av utleiested");
        //}
    }

    @FXML
    private void handleRentPlace() {
        String selectedPlace = rentalPlaceList.getSelectionModel().getSelectedItem();
        //manager.rentPlace(rentStart.getValue().toString(), Integer.parseInt(daysRenting.getText()), selectedPlace);
        updateRentalPlaceList();
        
    }

    private void initVisible() {
        mainBox.managedProperty().bind(mainBox.visibleProperty());
        newRentalBox.managedProperty().bind(newRentalBox.visibleProperty());
        loginBox.managedProperty().bind(loginBox.visibleProperty());
        logoutButton.managedProperty().bind(logoutButton.visibleProperty());
        mainBox.setVisible(false);
        newRentalBox.setVisible(false);
        logoutButton.setVisible(false);
    }


    private void toggleVisible() {
        mainBox.setVisible(!mainBox.isVisible());
        newRentalBox.setVisible(!newRentalBox.isVisible());
        loginBox.setVisible(!loginBox.isVisible());
        logoutButton.setVisible(!logoutButton.isVisible());
    }



    private void updateInfoLabel(String info) {
        infoLabel.setText(info);
    }

    private void showErrorMessage(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("En feil har oppstått");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }


    private void updateRentalPlaceList() {
        rentalPlaceList.getItems().setAll(rentalList); //manager.getRentalList
    }
}
