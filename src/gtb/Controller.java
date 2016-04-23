package gtb;

import gtb.view.GraphRenderer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.*;

public class Controller {

    private Stage stage;
    private GraphRenderer renderer;

    @FXML
    private Canvas canvas;

    public void initialize(){
        renderer = new GraphRenderer(canvas);
        renderer.redraw(null);
    }

    public void setStage(Stage s){
        stage = s;
        stage.getScene().widthProperty().addListener((observable, oldValue, newValue) -> {
            resizeCanvas(newValue.doubleValue()-55,stage.getScene().heightProperty().doubleValue()-25);
        });
        stage.getScene().heightProperty().addListener((observable, oldValue, newValue) -> {
            resizeCanvas(stage.getScene().widthProperty().doubleValue()-55,newValue.doubleValue()-25);
        });
    }

    public void resizeCanvas(double w, double h){
        canvas.setHeight(h);
        canvas.setWidth(w);
        renderer.redraw(null);
    }

    public void closeEventHandler(){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Do you really want to quit?");
        a.setTitle("Are you sure?");
        if(a.showAndWait().get() == ButtonType.OK){
            // cleanup
            stage.close();
        }
    }

    public void showAboutWindow(ActionEvent event) {
        Dialog d = new Dialog();
        d.getDialogPane().getButtonTypes().add(ButtonType.OK);
        d.initStyle(StageStyle.DECORATED);
        d.setTitle("GTB");
        d.setHeaderText("Graph Toolbox");
        d.setContentText(" Litwo! Ojczyzno moja! ty jesteś jak zdrowie;\n" +
                "Ile cię trzeba cenić, ten tylko się dowie\n" +
                "Kto cię stracił. Dziś piękność twą w całéj ozdobie\n" +
                "Widzę i opisuję, bo tęsknię po tobie.\n" +
                "\n" +
                "Panno święta, co jasnéj bronisz Częstochowy\n" +
                "I w Ostréj świecisz Bramie! Ty, co gród zamkowy\n" +
                "\n" +
                "Nowogródzki ochraniasz z jego wiernym ludem!\n" +
                "Jak mnie dziecko do zdrowia powróciłaś cudem,\n" +
                "(Gdy od płaczącéj matki, pod Twoję opiekę\n" +
                "Ofiarowany, martwą podniosłem powiekę;\n" +
                "I zaraz mogłem pieszo, do Twych świątyń progu\n" +
                "Iść za wrócone życie podziękować Bogu;)\n" +
                "Tak nas powrócisz cudem na Ojczyzny łono.\n" +
                "Tymczasem przenoś moję duszę utęsknioną\n" +
                "Do tych pagórków leśnych, do tych łąk zielonych,\n" +
                "Szeroko nad błękitnym Niemnem rosciągnionych;\n" +
                "Do tych pól malowanych zbożem rozmaitém,\n" +
                "Wyzłacanych pszenicą, posrebrzanych żytem;\n" +
                "Gdzie bursztynowy świerzop, gryka jak śnieg biała,\n" +
                "Gdzie panieńskim rumieńcem dzięcielina pała,\n" +
                "A wszystko przepasane jakby wstęgą, miedzą\n" +
                "Zieloną, na niéj zrzadka ciche grusze siedzą.");
        d.showAndWait();
    }
}
