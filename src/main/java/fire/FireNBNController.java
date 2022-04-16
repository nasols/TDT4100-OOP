package fire;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;


public class FireNBNController {

    private Manager manager;

    @FXML
    private TextField username, title;
    @FXML
    private TextArea description;
    @FXML
    private Label infoLabel;
    @FXML
    private ListView<String> rentalPlaceList, bookingList;
    @FXML
    private TabPane mainBox;
    @FXML
    private HBox loginBox;
    @FXML
    private Button logoutButton;
    @FXML
    private DatePicker avaliableDateStart, avaliableDateEnd, rentStart, rentEnd;


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
            manager.newRentalPlace(title.getText(), description.getText(), avaliableDateStart.getValue().toString(), avaliableDateEnd.getValue().toString());
            //rentalList.add(title.getText());
        }
    }

    @FXML
    private void handleRentPlace() {
        int selectedIndex = rentalPlaceList.getSelectionModel().getSelectedIndex();
        manager.rentPlace(rentStart.getValue().toString(), rentEnd.getValue().toString(), selectedIndex);
        updateRentalPlaceList();
        
    }

    private void initVisible() {
        mainBox.managedProperty().bind(mainBox.visibleProperty());
        loginBox.managedProperty().bind(loginBox.visibleProperty());
        logoutButton.managedProperty().bind(logoutButton.visibleProperty());
        mainBox.setVisible(false);
        logoutButton.setVisible(false);
    }


    private void toggleVisible() {
        mainBox.setVisible(!mainBox.isVisible());
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
        System.out.println(manager.toStringList());
        System.out.println(manager.getCurrentUsername());
        rentalPlaceList.getItems().setAll(manager.toStringList());
    }
}
