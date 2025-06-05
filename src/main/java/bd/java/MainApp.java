package bd.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application
{
    @Override
    public void start(Stage stage)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("ReservaRadar");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Class: MainApp");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public static void main(String[] args)
    {
        launch();
    }
}