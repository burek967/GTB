package gtb.io;

import gtb.model.Edge;
import gtb.model.Graph;
import gtb.model.Vertex;
import gtb.view.GraphRenderer;

import java.io.*;
import java.util.List;
import java.util.Locale;

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

    /**
     * Generates TikZ output. Document won't compile without arrows.meta TikZ package
     * since it's used for styling arrow heads.
     *
     * @param G        renderer
     * @param writer   writer to print to
     */

    public static void tikzExport(GraphRenderer G, PrintWriter writer) {
        List<Vertex> vertices = G.getGraph().getVertices();
        List<Edge> edges = G.getGraph().getEdges();
        writer.printf("\\begin{tikzpicture}[x=1,y=1,line width=0.8]\n");
        for (Edge e : edges) {
            Integer[] tab = G.getEdgeEndpoints(e);
            if (e.isDirected())
                writer.printf("\\draw[-{Latex[length=10]}](%d,%d) -- (%d,%d);\n", (Object[]) tab);
            else
                writer.printf("\\draw(%d,%d) -- (%d,%d);\n", (Object[]) tab);
            if(!e.getData().getLabel().isEmpty()){
                double k;
                if(tab[2] > tab[0])
                    k = Math.atan2(tab[1]-tab[3], tab[2]-tab[0]);
                else
                    k = Math.atan2(tab[3]-tab[1], tab[0]-tab[2]);
                writer.printf(Locale.US, "\\node[rotate=%f] at (%d,%d) {$\\large\\texttt{%s}$};\n", -k*180/Math.PI, (int)((tab[0]+tab[2])/2+10*Math.sin(k)), (int)((tab[1]+tab[3])/2+10*Math.cos(k)), e.getData().getLabel());
            }
        }
        for (Vertex v : vertices) {
            Integer[] tab = G.getVertexCoordinates(v);
            writer.printf("\\draw[ultra thick](%d,%d) circle [radius=%d];\n", (Object[]) tab);
            writer.printf("\\node at (%d,%d) {$\\Large\\texttt{%s}$};\n", tab[0], tab[1], v.getData().getId());
        }
        writer.println("\\end{tikzpicture}");
        writer.close();
    }

}
