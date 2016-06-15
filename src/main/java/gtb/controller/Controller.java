package gtb.controller;

import gtb.controller.events.GTBActionEvent;
import gtb.controller.events.GTBSelectEvent;
import gtb.controller.mouse.MouseModes;
import gtb.controller.mouse.NewEdgeMode;
import gtb.io.GraphExport;
import gtb.model.Graph;
import gtb.model.GraphElement;
import gtb.model.Vertex;
import gtb.model.graph_layout.ForceDrivenLayout;
import gtb.model.operations.ActionsManager;
import gtb.model.operations.RemoveElementAction;
import gtb.view.GraphRenderer;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Controller {

    private Stage stage;
    private GraphRenderer renderer;
    private Graph graph;
    private MouseModes mode = MouseModes.MOVE;
    private ActionsManager actionsManager;
    private CanvasContextMenu canvasContextMenu;
    private Map<MouseModes, Button> buttons;

    @FXML
    public Button MButton;
    @FXML
    public Button VButton;
    @FXML
    public Button DEButton;
    @FXML
    public Button UEButton;
    @FXML
    public Button EButton;
    @FXML
    private Canvas canvas;
    @FXML
    private RadioMenuItem showDebugInfo;
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem undoButton;
    @FXML
    private MenuItem redoButton;
    @FXML
    private MenuItem deleteButton;

    public void initialize() {
        graph = new Graph();
        renderer = new GraphRenderer(canvas, graph);
        renderer.redraw();
        canvasContextMenu = new CanvasContextMenu();
        ((NewEdgeMode) MouseModes.ADD_UNDIRECTED_EDGE.getHandlers()).setController(this);
        ((NewEdgeMode) MouseModes.ADD_DIRECTED_EDGE.getHandlers()).setController(this);
        buttons = new HashMap<>();
        buttons.put(MouseModes.MOVE,MButton);
        buttons.put(MouseModes.ADD_DIRECTED_EDGE,DEButton);
        buttons.put(MouseModes.ADD_UNDIRECTED_EDGE,UEButton);
        buttons.put(MouseModes.ADD_VERTEX,VButton);
        buttons.put(MouseModes.EDIT,EButton);
    }

    public void setStage(Stage s) {
        stage = s;
        actionsManager = new ActionsManager(graph, undoButton, redoButton, stage.getScene());
        undoButton.setOnAction(event -> {
            actionsManager.undo();
            renderer.redraw();
        });
        redoButton.setOnAction(event -> {
            actionsManager.redo();
            renderer.redraw();
        });
        stage.setOnCloseRequest(event -> {
            closeEventHandler();
            event.consume();
        });
        stage.getScene().widthProperty().addListener((observable, oldValue, newValue) -> {
            resizeCanvas(newValue.doubleValue() - 55, stage.getScene().heightProperty().doubleValue() - menuBar.getHeight());
        });
        stage.getScene().heightProperty().addListener((observable, oldValue, newValue) -> {
            resizeCanvas(stage.getScene().widthProperty().doubleValue() - 55, newValue.doubleValue() - menuBar.getHeight());
        });
        stage.getScene().addEventHandler(GTBActionEvent.ACTION_FIRED, this::updateUndoRedo);
        stage.getScene().addEventFilter(GTBActionEvent.ACTION_REDO, this::updateUndoRedo);
        stage.getScene().addEventFilter(GTBActionEvent.ACTION_UNDO, this::updateUndoRedo);
        stage.getScene().addEventFilter(GTBSelectEvent.SELECT, event -> {
            deleteButton.setDisable(false);
            canvasContextMenu.setDeleteDisable(false);
        });
        stage.getScene().addEventFilter(GTBSelectEvent.DESELECT, event -> {
            deleteButton.setDisable(true);
            canvasContextMenu.setDeleteDisable(true);
        });
        canvas.setOnContextMenuRequested(canvasContextMenu::show);
    }

    public void setMode(MouseModes m){
        mode = m;
        buttons.get(m).requestFocus();
    }

    private void updateUndoRedo(GTBActionEvent event) {
        undoButton.setDisable(!actionsManager.canUndo());
        redoButton.setDisable(!actionsManager.canRedo());
        canvasContextMenu.setUndoDisable(!actionsManager.canUndo());
        canvasContextMenu.setRedoDisable(!actionsManager.canRedo());
    }

    private void resizeCanvas(double w, double h) {
        canvas.setHeight(h);
        canvas.setWidth(w);
        renderer.redraw();
    }

    public void closeEventHandler() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Do you really want to quit?");
        a.setTitle("Are you sure?");
        if (a.showAndWait().get() == ButtonType.OK) {
            // cleanup
            stage.close();
        }
    }

    public void showAboutWindow() {
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

    public void removeSelectedElement() {
        GraphElement e = renderer.getSelectedElement();
        if (e == null) return;
        renderer.selectElement(null);
        actionsManager.addOperation(new RemoveElementAction(e, e.removeYourself(graph)));
        mode.getHandlers().onElementRemoved(e);
        renderer.redraw();
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

    public void onEditButton() {
        mode = MouseModes.EDIT;
    }

    public void toggleDebugInfo() {
        renderer.setDebugInfo(showDebugInfo.isSelected());
    }

    public void mouseOnCanvas() {
        stage.getScene().setCursor(mode.getCursor());
    }

    public void mouseOutCanvas() {
        stage.getScene().setCursor(Cursor.DEFAULT);
    }

    public void mouseOnCanvasDrag(MouseEvent event) {
        mode.getHandlers().onDrag(event, renderer, graph);
    }

    public void mouseOnCanvasPressed(MouseEvent event) {
        actionsManager.addOperation(mode.getHandlers().onPress(event, renderer, graph));
    }

    public void mouseOnCanvasReleased(MouseEvent event) {
        actionsManager.addOperation(mode.getHandlers().onRelease(event, renderer, graph));
    }

    public void mouseOnScroll(ScrollEvent event) {
        mode.getHandlers().onScroll(event, renderer);
    }

    public void onKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.F2)) {
            showDebugInfo.setSelected(!showDebugInfo.isSelected());
            toggleDebugInfo();
        }
    }

    public void exportGraph() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Export Graph to File");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File selected = chooser.showSaveDialog(stage);
        if (selected == null)
            return;
        try {
            GraphExport.graphExport(graph, new PrintWriter(selected.getAbsolutePath(),"UTF-8"));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Export error");
            alert.setHeaderText("Something went wrong!");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }

    public void exportTikZ() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Export Graph to TikZ");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.tex"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File selected = chooser.showSaveDialog(stage);
        if (selected == null)
            return;
        try {
            GraphExport.tikzExport(renderer, new PrintWriter(selected.getAbsolutePath(),"UTF-8"));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Export error");
            alert.setHeaderText("Something went wrong!");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }

    public void importGraph() {
        ImportWindow win = new ImportWindow(stage.getScene().getWindow());
        win.showAndWait();
        Graph G = win.getGraph();
        if(G != null) {
            graph = G;
            renderer = new GraphRenderer(canvas, G);
            ForceDrivenLayout layout = new ForceDrivenLayout();
            layout.layoutGraph(G);
            renderer.fitToScreen();
            renderer.redraw();
            actionsManager.reset(G);
        }
    }

    /**
     * unused, but contains some awesome code
     *
    public void importGraphFromClipboard() {
        Dialog d = new Dialog();
        d.setTitle("Import Graph from Clipboard");
        d.setResizable(true);

        TextArea textArea = new TextArea(Clipboard.getSystemClipboard().getString());
        textArea.setEditable(true);
        textArea.setWrapText(false);
        textArea.setPrefSize(400, 400);
        textArea.setPadding(Insets.EMPTY);

        ButtonType imp = new ButtonType("Import", ButtonBar.ButtonData.OK_DONE);
        ButtonType can = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        d.getDialogPane().setContent(textArea);
        d.getDialogPane().getButtonTypes().setAll(imp, can);

        if (d.showAndWait().get() == imp) {
            Graph G;
            try {
                G = GraphImport.graphImport(new StringReader(textArea.getText()));
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
            actionsManager.reset(G);
            ForceDrivenLayout layout = new ForceDrivenLayout();
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20),
                    event -> {layout.layoutGraph(G); renderer.redraw();}));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }
    */

    public void openGraph(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Import Graph from File");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("GTB Graph Files", "*.gtb"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File selected = chooser.showOpenDialog(stage);
        if (selected == null)
            return;
        Graph G;
        try {
            G = (Graph) new ObjectInputStream(new FileInputStream(selected)).readObject();
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
        actionsManager.reset(G);
    }

    public void saveGraph(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Export Graph to File");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("GTB Graph Files", "*.gtb"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File selected = chooser.showSaveDialog(stage);
        if (selected == null)
            return;
        try {
            ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(selected));
            obj.writeObject(graph);
            obj.flush();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Export error");
            alert.setHeaderText("Something went wrong!");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }

    private class CanvasContextMenu extends ContextMenu {

        private MenuItem undoButton = new MenuItem("Undo");
        private MenuItem redoButton = new MenuItem("Redo");
        private MenuItem delete = new MenuItem("Delete");
        private Menu add = new Menu("Add");
        private MenuItem vertex = new MenuItem("Vertex");
        private MenuItem undirectedEdge = new MenuItem("Undirected Edge");
        private MenuItem directedEdge = new MenuItem("Directed Edge");
        private double x, y;

        CanvasContextMenu() {
            this.getItems().addAll(undoButton, redoButton, new SeparatorMenuItem(), add, delete);
            add.getItems().addAll(vertex, undirectedEdge, directedEdge);
            undoButton.setDisable(true);
            redoButton.setDisable(true);
            vertex.setOnAction(event -> {
                MouseModes old = mode;
                mode = MouseModes.ADD_VERTEX;
                MouseEvent.fireEvent(canvas, new MouseEvent(MouseEvent.MOUSE_RELEASED, x, y, 0, 0, MouseButton.PRIMARY, 1, false, false, false, false, false, false, false, false, false, false, null));
                mode = old;
            });
            delete.setOnAction(event -> removeSelectedElement());
            undoButton.setOnAction(event -> {
                actionsManager.undo();
                renderer.redraw();
            });
            redoButton.setOnAction(event -> {
                actionsManager.redo();
                renderer.redraw();
            });
            undirectedEdge.setOnAction(event -> {
                if(!(renderer.getSelectedElement() instanceof Vertex)) {
                    event.consume();
                    return;
                }
                ((NewEdgeMode) MouseModes.ADD_UNDIRECTED_EDGE.getHandlers()).setOldMode(mode);
                ((NewEdgeMode) MouseModes.ADD_UNDIRECTED_EDGE.getHandlers()).setVertex((Vertex) renderer.getSelectedElement());
                setMode(MouseModes.ADD_UNDIRECTED_EDGE);
            });
            directedEdge.setOnAction(event -> {
                if(!(renderer.getSelectedElement() instanceof Vertex)) {
                    event.consume();
                    return;
                }
                ((NewEdgeMode) MouseModes.ADD_DIRECTED_EDGE.getHandlers()).setOldMode(mode);
                ((NewEdgeMode) MouseModes.ADD_DIRECTED_EDGE.getHandlers()).setVertex((Vertex) renderer.getSelectedElement());
                setMode(MouseModes.ADD_DIRECTED_EDGE);
            });
            setAutoHide(true);
        }

        void show(ContextMenuEvent event) {
            this.x = event.getSceneX();
            this.y = event.getSceneY();
            show(stage.getScene().getWindow(), event.getScreenX(), event.getScreenY());
            renderer.selectElement((float) (event.getX()), (float) (event.getY()));
            renderer.redraw();
        }

        void setUndoDisable(boolean b) {
            undoButton.setDisable(b);
        }

        void setRedoDisable(boolean b) {
            redoButton.setDisable(b);
        }

        void setDeleteDisable(boolean b) {
            delete.setDisable(b);
        }

    }

}
