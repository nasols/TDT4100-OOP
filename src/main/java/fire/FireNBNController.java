package fire;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FireNBNController {
    @FXML
    private TextField username;

    private Manager manager = new Manager();

    @FXML
    private void handleLogin() {
        //manager.login(username);
        
    }
}
