package waite.c482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * @author Dustin Waite
 *
 * javadoc is in waite.c482 folder
 *
 * Main class that consists of Inventory Management System application information
 */
public class Main extends Application {

    /**
     * loads the main menu screen onto the stage
     *
     * @param primaryStage
     * @throws Exception for FXMLLoader
     */

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("MainMenuView.fxml"));
        primaryStage.setTitle("IMS");
        primaryStage.setScene(new Scene(root, 1200, 500));
        primaryStage.show();

    }


    /**
     * launches inventory management application
     *
     * @param args
     */
    public static void main(String[] args) {

        launch();

    }
}