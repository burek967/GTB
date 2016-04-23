package gtb;

import gtb.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("gtb.fxml"));
        Parent root = loader.load();
        final Controller controller = loader.getController();
        primaryStage.setTitle("GTB");
        primaryStage.setScene(new Scene(root, 1280, 800));
        controller.setStage(primaryStage);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
