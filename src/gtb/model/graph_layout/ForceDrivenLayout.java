package gtb.model.graph_layout;

import gtb.model.Edge;
import gtb.model.Graph;
import gtb.model.Position;
import gtb.model.Vertex;

/**
 * Algorytm o gigantycznej złożoności (na razie)
 * I jest mocno niestabilny
 * Created by angela on 5/19/16.
 */
public class ForceDrivenLayout implements GraphLayout {
    public void tick(Graph g) {
        for(Vertex v1 : g.getVertices()) {
            Position p1 = v1.getData().getPosition();
            //suma sił działających na wierzchołek
            float xf = 0, yf = 0;
            for(Vertex v2 : g.getVertices()) {
                if(v1 == v2) continue;
                boolean connected = false;
                for(Edge e : g.getEdges()) {
                    if((e.getFirstVertex() == v1 && e.getSecondVertex() == v2) ||
                            (e.getFirstVertex() == v2 && e.getSecondVertex() == v1)) {
                        connected = true;
                        break;
                    }
                }
                //jeżeli wierzchołki są połączone krawędzią to używamy prawa hooka (jak sprężyna)
                if(connected) {
                    Position p2 = v2.getData().getPosition();
                    double dx = p2.getX()-p1.getX();
                    double dy = p2.getY()-p1.getY();
                    double d = Math.sqrt(dx*dx+dy*dy);
                    double K = 0.00005, X = 100;
                    xf += dx*K*(d-X);
                    yf += dy*K*(d-X);
                }
                //jeżeli wierzchołki nie są połączone to się odpychają - prawo coulomba
                //else {
                    Position p2 = v2.getData().getPosition();
                    double dx = p2.getX()-p1.getX();
                    double dy = p2.getY()-p1.getY();
                    double d = Math.sqrt(dx*dx+dy*dy);
                    double K = 15;
                    xf -= dx*K/(d*d);
                    yf -= dy*K/(d*d);
                //}
            }
            v1.getData().setPosition(new Position(p1.getX()+xf, p1.getY()+yf));
        }
    }

    @Override
    public void layoutGraph(Graph g) {
        tick(g);
    }
}
