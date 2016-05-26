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
    private HashMap<Vertex, ArrayList<Vertex>> hm;
    private static final int ITERATIONS = 100;
    public void tick(Graph g) {
        for (Vertex v1 : g.getVertices()) {
            Position p1 = v1.getData().getPosition();
            //suma sił działających na wierzchołek
            float xf = 0, yf = 0;
            //połączone zachowują się jak sprężyna - prawo hooka
            for (Vertex v2 : hm.get(v1)) {
                if (v1 == v2) continue;
                Position p2 = v2.getData().getPosition();
                double dx = p2.getX() - p1.getX();
                double dy = p2.getY() - p1.getY();
                double d = Math.sqrt(dx * dx + dy * dy);
                double K = 0.0005, X = 10;
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
                double K = 500;
                xf -= dx * K / (d * d);
                yf -= dy * K / (d * d);
            }
            v1.getData().setPosition(new Position(p1.getX() + xf, p1.getY() + yf));
        }
    }

    @Override
    public void layoutGraph(Graph g) {
        hm = new HashMap<>();
        for(Vertex v : g.getVertices()) {
            hm.put(v, new ArrayList<>());
        }
        for(Edge e: g.getEdges()) {
            hm.get(e.getFirstVertex()).add(e.getSecondVertex());
            hm.get(e.getSecondVertex()).add(e.getFirstVertex());
        }
        for(int i = 0; i < ITERATIONS; ++i)
            tick(g);
    }
}
