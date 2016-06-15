package gtb.controller;

import gtb.Main;
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
    public ColorPicker elementColorPicker;
    @FXML
    public ColorPicker textColorPicker;
    @FXML
    public TextField textField;

    @FXML
    public void initialize() {
        okButton.setOnAction(this::setElement);
        cancelButton.setOnAction(event -> ((Node) event.getSource()).getScene().getWindow().hide());
    }

    private void setElement(ActionEvent event) {
        elementData.setLabel(textField.getText());
        elementData.setColor(elementColorPicker.getValue());
        elementData.setTextColor(textColorPicker.getValue());
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    public void setElement(GraphElementData elementData) {
        this.elementData = elementData;
        elementColorPicker.setValue(elementData.getColor());
        textColorPicker.setValue(elementData.getTextColor());
        textField.setText(elementData.getLabel());
    }

    public EditWindow() {

    }

    public EditWindow(Window owner, GraphElementData data) {
        final FXMLLoader loader = new FXMLLoader(Main.class.getClassLoader().getResource("layouts/edit.fxml"));
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
        this.setScene(new Scene(root, 300, 200));
        this.initOwner(owner);
    }
}
