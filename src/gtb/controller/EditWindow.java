package gtb.controller;

import gtb.Main;
import gtb.model.Graph;
import gtb.model.GraphElement;
import gtb.model.GraphElementData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

/**
 * Created by angela on 6/2/16.
 */
public class EditWindow extends Stage {
    private GraphElementData elementData;

    @FXML
    public Button okButton;
    @FXML
    public Button cancelButton;
    @FXML
    public ColorPicker colorPicker;
    @FXML
    public TextField textField;

    @FXML
    public void initialize() {
        okButton.setOnAction(this::setElement);
        cancelButton.setOnAction(event -> ((Node) event.getSource()).getScene().getWindow().hide());
    }

    private void setElement(ActionEvent event) {
        elementData.setLabel(textField.getText());
        elementData.setColor(colorPicker.getValue());
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    public void setElement(GraphElementData elementData) {
        this.elementData = elementData;
        colorPicker.setValue(elementData.getColor());
        textField.setText(elementData.getLabel());
    }

    public EditWindow() {

    }

    public EditWindow(Window owner, GraphElementData data) {
        final FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/layout/edit.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        this.setTitle("Edit element");
        this.initModality(Modality.WINDOW_MODAL);
        loader.<EditWindow>getController().setElement(data);
        this.setScene(new Scene(root, 300, 150));
        this.initOwner(owner);
    }
}
