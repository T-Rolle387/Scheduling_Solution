package Utility;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ButtonNav {

    public static void navigateTo(Stage stage, Event event, Parent scene, String selectedLocation) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(ButtonNav.class.getResource(selectedLocation));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
