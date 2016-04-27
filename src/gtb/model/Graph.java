package gtb.model;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by angela on 4/20/16.
 */
public class Graph {
    private List<Vertex> vertices;
    private List<Edge> edges;
    private int lastVertexId = 0, lastEdgeId = 0;

    public Graph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addVertex(Vertex v) {
        v.getData().setId(lastVertexId++);
        vertices.add(v);
    }

    public Vertex addVertex() {
        Vertex v = new Vertex();
        v.getData().setId(lastVertexId++);
        vertices.add(v);
        return v;
    }

    public void addEdge(Edge e) {
        e.getData().setId(lastEdgeId++);
        edges.add(e);
    }

    public Edge addDirectedEdge(Vertex v1, Vertex v2) {
        Edge e = new Edge(v1, v2, true);
        addEdge(e);
        return e;
    }

    public Edge addUndirectedEdge(Vertex v1, Vertex v2) {
        Edge e = new Edge(v1, v2, false);
        addEdge(e);
        return e;
    }

    public void deleteVertex(Vertex v) {
        vertices.remove(v);
        edges = edges.stream().filter(e -> (e.getFirstVertex() != v && e.getSecondVertex() != v)).collect(Collectors.toList());
    }

    public void deleteEdge(Edge e) {
        edges.remove(e);
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
