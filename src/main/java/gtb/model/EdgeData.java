package gtb.model;

import javafx.scene.paint.Color;

/**
 * Created by angela on 4/20/16.
 */
public class EdgeData extends GraphElementData {

    @Override
    public Color getDefaultColor() {
        return Color.GREEN;
    }

    @Override
    public String getDefaultLabel() {
        return "";
    }

    @Override
    public Color getDefaultTextColor() {
        return Color.BLACK;
    }
}
