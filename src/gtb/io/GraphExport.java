package gtb.io;

import gtb.model.Edge;
import gtb.model.Graph;
import gtb.model.Vertex;
import gtb.view.GraphRenderer;

import java.io.*;
import java.util.List;

/**
 * Created by qwerty on 2016-04-27.
 */

public class GraphExport {
    public static void graphExport(Graph G, PrintWriter writer) {
        List<Vertex> vertices = G.getVertices();
        List<Edge> edges = G.getEdges();
        writer.println(vertices.size() + " " + edges.size());
        for (Edge e : edges) {
            writer.println(e.getFirstVertex().getData().getId() + " " + e.getSecondVertex().getData().getId());
            if (!e.isDirected())
                writer.println(e.getSecondVertex().getData().getId() + " " + e.getFirstVertex().getData().getId());
        }
        writer.close();
    }

    public static void psTricksExport(GraphRenderer G, PrintWriter writer) {
        List<Vertex> vertices = G.getGraph().getVertices();
        List<Edge> edges = G.getGraph().getEdges();
        writer.println("\\psset{unit=1px, linewidth=2px, arrowsize=5 5}");
        writer.printf("\\begin{pspicture}(%d,%d)\n", (int) G.getCanvas().getWidth(), (int) G.getCanvas().getHeight());
        for (Edge e : edges) {
            if (e.isDirected())
                writer.printf("\\psline{->}(%d,%d)(%d,%d)\n", (Object[]) G.getEdgeEndpoints(e));
            else
                writer.printf("\\psline(%d,%d)(%d,%d)\n", (Object[]) G.getEdgeEndpoints(e));
        }
        for (Vertex v : vertices) {
            Integer[] tab = G.getVertexCoordinates(v);
            writer.printf("\\pscircle(%d,%d){%d}\n", (Object[]) tab);
            writer.printf("\\rput(%d,%d){$\\Large\\texttt{%s}$}\n", tab[0], tab[1], v.getData().getId());
        }
        writer.println("\\end{pspicture}");
        writer.close();
    }

}
