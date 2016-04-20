package gtb.test;

import gtb.model.Edge;
import gtb.model.Graph;
import gtb.model.Vertex;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by angela on 4/21/16.
 */
public class GraphTest {
    @Test
    public void test1() {
        Graph g = new Graph();
        Vertex v[] = new Vertex[5];
        for(int i = 0; i < 5; ++i) {
            v[i] = new Vertex();
            g.addVertex(v[i]);
            assertEquals(i, v[i].getData().getId());
        }
        for(int i = 0; i < 5; ++i) {
            Edge e = g.addDirectedEdge(v[i], v[(i + 1) % 5]);
            assertEquals(i+5, e.getData().getId());
        }
        assertEquals(5, g.getVertices().size());
        assertEquals(5, g.getEdges().size());
        g.deleteVertex(v[0]);
        assertEquals(4, g.getVertices().size());
        assertEquals(3, g.getEdges().size());
        g.deleteEdge(g.getEdges().get(0));
        assertEquals(2, g.getEdges().size());
    }
}