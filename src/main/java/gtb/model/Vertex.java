package gtb.model;

import java.util.List;

/**
 * Created by angela on 4/20/16.
 */
public class Vertex extends GraphElement<VertexData> implements Comparable<Vertex> {
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

    @Override
    public int compareTo(Vertex o) {
        int id1 = getData().getId(), id2 = o.getData().getId();
        if(id1 == id2) return 0;
        if(id1 < id2) return -1;
        return 1;
    }
}
