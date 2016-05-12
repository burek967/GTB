package gtb.model;

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
}
