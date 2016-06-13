package gtb.model.graph_layout;

import gtb.model.Edge;
import gtb.model.Graph;
import gtb.model.Position;
import gtb.model.Vertex;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Algorytm o gigantycznej złożoności (na razie)
 * I jest mocno niestabilny
 * Created by angela on 5/19/16.
 */
public class ForceDrivenLayout implements GraphLayout {
    private class VertexLayoutData {
        int component = 0;
        ArrayList<Vertex> neighbours = new ArrayList<>();
    };
    private HashMap<Vertex, VertexLayoutData> hm;
    private static final int ITERATIONS = 200;
    public void tick(Graph g) {
        for (Vertex v1 : g.getVertices()) {
            Position p1 = v1.getData().getPosition();
            //suma sił działających na wierzchołek
            float xf = 0, yf = 0;
            //połączone zachowują się jak sprężyna - prawo hooka
            for (Vertex v2 : hm.get(v1).neighbours) {
                if (v1 == v2) continue;
                Position p2 = v2.getData().getPosition();
                double dx = p2.getX() - p1.getX();
                double dy = p2.getY() - p1.getY();
                double d = Math.sqrt(dx * dx + dy * dy);
                double K = 0.0005, X = 100;
                xf += dx * K * (d - X);
                yf += dy * K * (d - X);
            }
            //wszystkie się odpychają - prawo coulomba
            for (Vertex v2 : g.getVertices()) {
                if (v1 == v2) continue;
                Position p2 = v2.getData().getPosition();
                double dx = p2.getX() - p1.getX();
                double dy = p2.getY() - p1.getY();
                double d = Math.sqrt(dx * dx + dy * dy);
                double K = 20;
                if(hm.get(v1).component == hm.get(v2).component)
                    K = 500;
                xf -= dx * K / (d * d);
                yf -= dy * K / (d * d);
            }
            v1.getData().setPosition(new Position(p1.getX() + xf, p1.getY() + yf));
        }
    }

    private void dfs(Vertex v, int component) {
        hm.get(v).component = component;
        for(Vertex vv : hm.get(v).neighbours)
            if(hm.get(vv).component == 0)
                dfs(vv, component);
    }

    @Override
    public void layoutGraph(Graph g) {
        hm = new HashMap<>();
        for(Vertex v : g.getVertices()) {
            hm.put(v, new VertexLayoutData());
        }
        for(Edge e: g.getEdges()) {
            hm.get(e.getFirstVertex()).neighbours.add(e.getSecondVertex());
            hm.get(e.getSecondVertex()).neighbours.add(e.getFirstVertex());
        }
        int lastComponent = 0;
        for(Vertex v : g.getVertices()) {
            VertexLayoutData vd = hm.get(v);
            if(vd.component > 0) continue;
            dfs(v, ++lastComponent);
        }
        for(int i = 0; i < ITERATIONS; ++i)
            tick(g);
    }
}
