package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("College Football Information Finder");
        primaryStage.setScene(new Scene(root, 1050, 550));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Controller.closeConnection());
        Controller.setupDatabaseConnection();
        launch(args);
    }
}
