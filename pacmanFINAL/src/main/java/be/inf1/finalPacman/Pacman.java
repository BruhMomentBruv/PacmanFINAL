/**
 * @author Jessie Baskauf and Ellie Mamantov
 * Sets up the controller, loads the fxml file, and runs the application.
 */
package be.inf1.finalPacman;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Pacman extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        setRoot("PacmanFXML", stage);
    }
    
    private static void setRoot(String fxml, Stage stage)throws IOException {
        FXMLLoader fxmlLoader = loadFXML(fxml);
        Parent root = fxmlLoader.load();
        PacmanController controller = fxmlLoader.getController();
        root.setOnKeyPressed(controller);
        double sceneWidth = controller.getBoardWidth() + 20.0;
        double sceneHeight = controller.getBoardHeight() + 100.0;
        stage.setScene(new Scene(root, sceneWidth, sceneHeight));
        stage.setTitle("PacMan");
        stage.show();
        root.requestFocus();
    }
    
    private static FXMLLoader loadFXML(String fxml) throws IOException {
        return new FXMLLoader(Pacman.class.getResource(fxml + ".fxml"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
