package fire;

import java.io.IOException;
import java.util.NoSuchElementException;

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
    private ISaveHandler saveHandler = new SaveHandler();

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
        try {
            manager.login(username.getText());
            updateRentalPlaceList();
            updateBookingList();
            updateInfoLabel("Logget inn som " + manager.getCurrentUsername());
            toggleVisible();
            username.clear(); 
        }
        catch (IllegalArgumentException e) {
            showErrorMessage(e.getMessage());
        }
        
    }


    @FXML
    private void handleLogout() {
        manager.logout();
        updateInfoLabel("Velkommen!");
        toggleVisible();
        clearPlaceForm();
    }


    @FXML
    private void handleAddPlace() {
        try {
            manager.newRentalPlace(title.getText(), description.getText(), avaliableDateStart.getValue().toString(), avaliableDateEnd.getValue().toString());
            updateInfoLabel("\"" + title.getText() + "\" ble lagt til!");
            clearPlaceForm();
        }
        catch (IllegalArgumentException e) {
            showErrorMessage(e.getMessage());
        }
        catch (NullPointerException e) {
            showErrorMessage("Alle feltene er ikke fylt ut");
        }
    }

    @FXML
    private void handleRentPlace() {
        try {
            int selectedIndex = rentalPlaceList.getSelectionModel().getSelectedIndex();
            manager.rentPlace(rentStart.getValue().toString(), rentEnd.getValue().toString(), selectedIndex);
            updateRentalPlaceList();
            updateBookingList();
            updateInfoLabel("Bookingen ble lagt til!");
            clearRentForm();
        }
        catch (IllegalArgumentException e) {
            showErrorMessage(e.getMessage());
        }
        catch (NullPointerException e) {
            showErrorMessage("Dato for leie er ikke oppgitt");
        }
        
        
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

    private void clearPlaceForm() {
        title.clear();
        description.clear();
        avaliableDateEnd.getEditor().clear();
        avaliableDateStart.getEditor().clear();
    }

    private void clearRentForm() {
        rentStart.getEditor().clear();
        rentEnd.getEditor().clear();
    }

    private void showErrorMessage(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("En feil har oppstått");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }


    private void updateRentalPlaceList() {
        rentalPlaceList.getItems().setAll(manager.getRentalStringList());
    }

    private void updateBookingList() {
        bookingList.getItems().setAll(manager.getBookingStringList());
    }

    public void exit() {
        try {
            System.out.println("Lagrer...");
            saveHandler.writeReciept(manager);
        }
        catch (IOException e) {
            System.out.println(e);
            showErrorMessage("Feil ved lagring av data");
        }
        catch (NoSuchElementException e) {
            System.out.println(e);
            //Trenge me denne?
        }
    }
}
