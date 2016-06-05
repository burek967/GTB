package gtb.model;

import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Created by angela on 4/20/16.
 */
public class GraphElementData implements Serializable{
    public static final Color DEFAULT_COLOR = Color.GREENYELLOW;
    private int id;
    private Color color;
    private String label;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Color getColor() {
        if(color == null)
            return DEFAULT_COLOR;
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getLabel() {
        if(label == null)
            return String.valueOf(id);
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
