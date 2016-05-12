package gtb.model;

import java.util.List;

/**
 * Created by angela on 4/20/16.
 */
public class Vertex extends GraphElement<VertexData> {
    public Vertex() {
        setData(new VertexData());
    }

    @Override
    public List<? extends GraphElement> removeYourself(Graph g) {
        return g.deleteVertex(this);
    }

    @Override
    public void addYourself(Graph g) {
        g.addVertex(this);
    }
}
