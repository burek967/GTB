package gtb.model;

import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Created by angela on 4/20/16.
 */
public abstract class GraphElementData implements Serializable{
    private int id;
    private transient Color color, textColor;
    private String label;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract Color getDefaultColor();

    public abstract String getDefaultLabel();

    public abstract Color getDefaultTextColor();

    public Color getColor() {
        if(color == null)
            return getDefaultColor();
        return color;
    }

    public Color getTextColor() {
        if(textColor == null)
            return getDefaultTextColor();
        return textColor;
    }

    public void setTextColor(Color color) {
        textColor = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getLabel() {
        if(label == null)
            return getDefaultLabel();
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
