package gtb.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angela on 4/20/16.
 */
public class Edge extends GraphElement<EdgeData> {
    private Vertex v1, v2;
    private boolean directed;

    public Edge(Vertex v1, Vertex v2, boolean directed) {
        this.v1 = v1;
        this.v2 = v2;
        this.directed = directed;
        setData(new EdgeData());
    }

    public Vertex getFirstVertex() {
        return v1;
    }

    public Vertex getSecondVertex() {
        return v2;
    }

    public boolean isDirected() {
        return directed;
    }

    @Override
    public List<? extends GraphElement> removeYourself(Graph g) {
        g.deleteEdge(this);
        return new ArrayList<>();
    }

    @Override
    public void addYourself(Graph g) {
        g.addEdge(this);
    }
}
