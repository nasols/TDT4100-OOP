package fire;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FireNBNApp extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FireNBN.fxml"));
        Parent root = loader.load();
        FireNBNController controller = loader.getController();
        Scene scene = new Scene(root);
        stage.setTitle("FireNBN");
        stage.setScene(scene);
        stage.show();

        // Sørger for at data blir lagret når brukeren lukker programmet
        stage.setOnCloseRequest(e -> controller.exit());
    }
}
