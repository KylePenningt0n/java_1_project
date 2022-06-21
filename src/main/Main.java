package main;

import controller.MainForm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class C:\Users\Kyle\Desktop\WGU\C482 Directory for my javadocs
 *
 */
public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    /**
     * Primary method of main class. launches the application
     * @param args
     */
    public static void main(String [] args){
        launch(args);
    }
}
