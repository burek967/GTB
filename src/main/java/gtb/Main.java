package gtb;

import gtb.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/main.fxml"));
        Parent root = loader.load();
        final Controller controller = loader.getController();
        primaryStage.setTitle("GTB");
        primaryStage.setScene(new Scene(root, Math.min(1280, Screen.getPrimary().getBounds().getWidth()), Math.min(800, Screen.getPrimary().getBounds().getHeight())));
        controller.setStage(primaryStage);
        primaryStage.getIcons().setAll(new Image("icons/icon.png"));
        primaryStage.show();
    }
}
