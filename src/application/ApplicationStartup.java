
package application;

import java.io.File;
import java.io.PrintWriter;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author patri
 */
public class ApplicationStartup extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainLayout.fxml"));
        Scene scene = new Scene(root, 1025 , 589);

        stage.setScene(scene);
        stage.setOnCloseRequest((WindowEvent e) -> {
            closeWindow(stage, e);
        });
        stage.setResizable(false);
        stage.getIcons().add(new Image("/icon/MumboLogo.png"));
        stage.setTitle("MumboSoft Time Management");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void closeWindow(Stage stage, WindowEvent e) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Avsluta");
        alert.setHeaderText("Är du säker på att du vill avsluta?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            stage.close();
        } else {
            e.consume();
        }
    }

}
