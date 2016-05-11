package gtb.controller;

import gtb.controller.mouse.MouseModes;
import gtb.file_support.GraphExport;
import gtb.file_support.GraphImport;
import gtb.model.Graph;
import gtb.model.GraphElement;
import gtb.model.operations.ActionsManager;
import gtb.model.operations.RemoveElementAction;
import gtb.view.GraphRenderer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;

public class Controller {

    private Stage stage;
    private GraphRenderer renderer;
    private Graph graph;
    private MouseModes mode = MouseModes.MOVE;
    private ActionsManager actionsManager;

    @FXML
    private Canvas canvas;
    @FXML
    private RadioMenuItem showDebugInfo;
    @FXML
    private MenuBar menuBar;

    public void initialize(){
        graph = new Graph();
        renderer = new GraphRenderer(canvas, graph);
        renderer.redraw();
        actionsManager = new ActionsManager(graph);
    }

    public void setStage(Stage s){
        stage = s;
        stage.getScene().widthProperty().addListener((observable, oldValue, newValue) -> {
            resizeCanvas(newValue.doubleValue()-55,stage.getScene().heightProperty().doubleValue()-menuBar.getHeight());
        });
        stage.getScene().heightProperty().addListener((observable, oldValue, newValue) -> {
            resizeCanvas(stage.getScene().widthProperty().doubleValue()-55,newValue.doubleValue()-menuBar.getHeight());
        });
    }

    public void resizeCanvas(double w, double h){
        canvas.setHeight(h);
        canvas.setWidth(w);
        renderer.redraw();
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

    public void onMoveButton() {
        mode = MouseModes.MOVE;
    }

    public void onNewVertexButton() {
        mode = MouseModes.ADD_VERTEX;
    }

    public void onNewDirectedEdgeButton() {
        mode = MouseModes.ADD_DIRECTED_EDGE;
    }

    public void onNewUndirectedEdgeButton() {
        mode = MouseModes.ADD_UNDIRECTED_EDGE;
    }

    public void onDeleteButton() {
        GraphElement e = renderer.getSelectedElement();
        if(e == null) return;
        renderer.selectElement(null);
        e.commitSeppuku(graph);
        actionsManager.addOperation(new RemoveElementAction(e, graph));
        renderer.redraw();
    }

    public void toggleDebugInfo() {
        renderer.setDebugInfo(showDebugInfo.isSelected());
    }

    public void mouseOnCanvas(){
        stage.getScene().setCursor(mode.getCursor());
    }

    public void mouseOutCanvas(){
        stage.getScene().setCursor(Cursor.DEFAULT);
    }

    public void mouseOnCanvasDrag(MouseEvent event) {
        mode.getHandlers().onDrag(event, renderer, graph);
    }

    public void mouseOnCanvasPressed(MouseEvent event) {
        mode.getHandlers().onPress(event, renderer, graph);
    }

    public void mouseOnCanvasReleased(MouseEvent event) {
        mode.getHandlers().onRelease(event, renderer, graph);
    }

    public void mouseOnScroll(ScrollEvent event) {
        mode.getHandlers().onScroll(event, renderer);
    }

    public void onKeyPressed(KeyEvent event) {
        if(event.getCode().equals(KeyCode.F2)){
            showDebugInfo.setSelected(!showDebugInfo.isSelected());
            toggleDebugInfo();
        }
    }

    public void exportGraph() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Export Graph to File");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files","*.txt"),
                new FileChooser.ExtensionFilter("All Files","*.*")
        );
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File selected = chooser.showSaveDialog(stage);
        if(selected == null)
            return;
        try {
            GraphExport.graphExport(graph,selected.getAbsolutePath());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Export error");
            alert.setHeaderText("Something went wrong!");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }

    public void importGraph() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Import Graph from File");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files","*.txt"),
                new FileChooser.ExtensionFilter("All Files","*.*")
        );
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File selected = chooser.showOpenDialog(stage);
        if(selected == null)
            return;
        Graph G;
        try {
            G = GraphImport.graphImport(selected.getAbsolutePath());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Import error");
            alert.setHeaderText("Something went wrong!");
            alert.setContentText(e.toString());
            alert.showAndWait();
            return;
        }
        graph = G;
        renderer = new GraphRenderer(canvas, G);
        renderer.redraw();
    }
}
