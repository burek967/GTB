package gtb.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by angela on 4/20/16.
 */
public class Graph implements Serializable{
    private HashMap<Vertex, List<Vertex>> vertices;
    private List<Edge> edges;
    private int lastVertexId = 0;

    public Graph() {
        vertices = new HashMap<>();
        edges = new ArrayList<>();
    }

    public void setLastVertexId(int lastVertexId){
        this.lastVertexId = lastVertexId;
    }

    public int getLastVertexId(){
        return lastVertexId;
    }

    public void addVertex(Vertex v) {
        vertices.put(v, new ArrayList<>());
    }

    public Vertex addVertex() {
        Vertex v = new Vertex();
        v.getData().setId(lastVertexId++);
        vertices.put(v, new ArrayList<>());
        return v;
    }

    public void addEdge(Edge e) {
        edges.add(e);
        vertices.get(e.getFirstVertex()).add(e.getSecondVertex());
        if(!e.isDirected())
            vertices.get(e.getSecondVertex()).add(e.getFirstVertex());
    }

    public Edge addDirectedEdge(Vertex v1, Vertex v2) {
        Edge e = new Edge(v1, v2, true);
        addEdge(e);
        vertices.get(v1).add(v2);
        return e;
    }

    public Edge addUndirectedEdge(Vertex v1, Vertex v2) {
        Edge e = new Edge(v1, v2, false);
        addEdge(e);
        vertices.get(v1).add(v2);
        vertices.get(v2).add(v1);
        return e;
    }

    public List<Edge> deleteVertex(Vertex v) {
        vertices.remove(v);
        List<Edge> l1 = new ArrayList<>(), l2 = new ArrayList<>();
        edges.stream().forEach((x) -> ((x.getFirstVertex() == v || x.getSecondVertex() == v) ? l1 : l2).add(x));
        edges = l2;
        vertices.remove(v);
        return l1;
    }

    public void deleteEdge(Edge e) {
        edges.remove(e);
    }

    public List<Vertex> getVertices() {
        return new ArrayList<>(vertices.keySet());
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Vertex> getVerticesConnectedWith(Vertex v) {
        return vertices.get(v);
    }

}
