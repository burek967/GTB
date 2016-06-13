package gtb.controller;

import gtb.Main;
import gtb.io.GraphImport;
import gtb.model.Graph;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;

public class ImportWindow extends Stage {

    private static Graph G;

    @FXML
    public ToggleGroup importType;
    @FXML
    public ToggleGroup graphType;
    @FXML
    public RadioButton file;
    @FXML
    public RadioButton clipbrd;
    @FXML
    public RadioButton directed;
    @FXML
    public RadioButton undirected;
    @FXML
    public Button importButton;
    @FXML
    public Button cancelButton;
    @FXML
    public TextArea clipbrdText;
    @FXML
    public Button fileButton;
    @FXML
    public TextField fileText;

    @FXML
    public void initialize() {
        clipbrdText.setText(Clipboard.getSystemClipboard().getString());
        importType.selectedToggleProperty().addListener(observable -> {
            if (importType.getSelectedToggle() == clipbrd) {
                clipbrdText.setDisable(false);
                fileText.setDisable(true);
                fileButton.setDisable(true);
            } else {
                clipbrdText.setDisable(true);
                fileText.setDisable(false);
                fileButton.setDisable(false);
            }
        });
        importButton.setOnAction(this::importGraph);
        cancelButton.setOnAction(event -> ((Node) event.getSource()).getScene().getWindow().hide()); // thanks SO
        fileButton.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Import Graph from File");
            chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
            );
            chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            File selected = chooser.showOpenDialog(ImportWindow.this);
            if (selected != null)
                fileText.setText(selected.getAbsolutePath());
        });
    }

    public ImportWindow() {
    }

    public ImportWindow(Window owner) {
        this.getIcons().setAll(new Image("icons/icon.png"));
        G = null;
        final FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/layout/import.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        this.setTitle("Import Graph");
        this.initModality(Modality.WINDOW_MODAL);
        loader.setController(this);
        this.setScene(new Scene(root, 350, 450));
        this.initOwner(owner);
    }

    private void importGraph(ActionEvent event) {
        Reader x;
        if (file.isSelected()) {
            try {
                x = new FileReader(fileText.getText());
            } catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No such file found:");
                alert.setContentText(fileText.getText());
                alert.showAndWait();
                return;
            }
        } else
            x = new StringReader(clipbrdText.getText());
        try {
            G = GraphImport.graphImport(x, directed.isSelected());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Given input couldn't be parsed as valid graph.");
            e.printStackTrace();
            alert.showAndWait();
            return;
        }
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    public Graph getGraph() {
        return G;
    }
}
