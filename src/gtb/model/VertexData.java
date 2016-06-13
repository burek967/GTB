package gtb.model;

import javafx.scene.paint.Color;

/**
 * Created by angela on 4/20/16.
 */
public class VertexData extends GraphElementData {
    private Position position;

    public VertexData() {
        position = new Position();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Color getDefaultColor() {
        return Color.GREENYELLOW;
    }

    @Override
    public String getDefaultLabel() {
        return String.valueOf(getId());
    }

    @Override
    public Color getDefaultTextColor() {
        return Color.BLACK;
    }
}
