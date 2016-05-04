package gtb.model;

/**
 * Created by angela on 4/20/16.
 */
public class Vertex extends GraphElement<VertexData> {
    public Vertex() {
        setData(new VertexData());
    }

    @Override
    public void commitSeppuku(Graph g) {
        g.deleteVertex(this);
    }
}
