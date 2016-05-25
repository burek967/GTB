package gtb.io;

import gtb.model.Graph;
import gtb.model.Vertex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Created by qwerty on 2016-04-27.
 */
public class GraphImport {
    public static Graph graphImport(Reader r, final boolean directed) throws IOException {
        Graph G = new Graph();
        String sCurrentLine;
        BufferedReader br = new BufferedReader(r);
        int V, E = 0;
        if ((sCurrentLine = br.readLine()) != null) {
            int i = 0;
            while (sCurrentLine.charAt(i) != ' ') i++;
            String sFirstVal = sCurrentLine.substring(0, i).trim();
            String sSecondVal = sCurrentLine.substring(i + 1, sCurrentLine.length()).trim();
            V = Integer.parseInt(sFirstVal);
            E = Integer.parseInt(sSecondVal);
            for (i = 0; i < V; i++) G.addVertex();
        }
        List<Vertex> vertices = G.getVertices();
        int j = 0;
        while ((sCurrentLine = br.readLine()) != null) {
            if (j > E) return null;
            j++;
            int i = 0;
            while (sCurrentLine.charAt(i) != ' ') i++;
            String sFirstVal = sCurrentLine.substring(0, i).trim();
            String sSecondVal = sCurrentLine.substring(i + 1, sCurrentLine.length()).trim();
            int FirstVal = Integer.parseInt(sFirstVal);
            int SecondVal = Integer.parseInt(sSecondVal);
            Vertex v1 = vertices.get(FirstVal);
            Vertex v2 = vertices.get(SecondVal);
            if (directed)
                G.addDirectedEdge(v1, v2);
            else
                G.addUndirectedEdge(v1, v2);
        }
        return G;
    }
}