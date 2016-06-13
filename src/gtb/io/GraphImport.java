package gtb.io;

import gtb.model.Graph;
import gtb.model.Vertex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by qwerty on 2016-04-27.
 */
public class GraphImport {
    public static Graph graphImport(Reader r, final boolean directed) throws IOException {
        Graph G = new Graph();
        String sCurrentLine;
        BufferedReader br = new BufferedReader(r);
        int V, E;
        boolean zeroIndexed = true; // we assume initially that input graph is zero-indexed
        boolean vZero = false;      // does vertex 0 have any neighbors? (vZero && !zeroIndexed) is always false
        if ((sCurrentLine = br.readLine()) != null) {
            int[] T = Arrays.stream(sCurrentLine.split("\\s+", 2)).mapToInt(Integer::parseInt).toArray();
            V = T[0];
            E = T[1];
            for (int i = 0; i < V; i++) G.addVertex();
        } else throw new IOException("Empty input");

        int T[][] = new int[E][2];

        List<Vertex> vertices = G.getVertices();
        Collections.sort(vertices);
        for(int j=0;j<E;++j){
            if ((sCurrentLine = br.readLine()) == null)
                throw new IOException("Not enough data on input");
            T[j] = Arrays.stream(sCurrentLine.split("\\s+", 2)).mapToInt(Integer::parseInt).toArray();
            if(T[j][0] == 0 || T[j][1] == 0) {
                if(!zeroIndexed)
                    throw new IndexOutOfBoundsException();
                vZero = true;
            }
            if(T[j][0] == V || T[j][1] == V){
                if(vZero)
                    throw new IndexOutOfBoundsException();
                zeroIndexed = false;
            }
        }

        if(!zeroIndexed) {
            G.setLastVertexId(G.getLastVertexId()+1);
            vertices.stream().forEach(vertex -> vertex.getData().setId(vertex.getData().getId() + 1));
            for(int[] x : T){
                x[0]--;
                x[1]--;
            }
        }

        for(int i=0;i<E;++i) {
            Vertex v1 = vertices.get(T[i][0]);
            Vertex v2 = vertices.get(T[i][1]);
            if (directed)
                G.addDirectedEdge(v1, v2);
            else
                G.addUndirectedEdge(v1, v2);
        }
        return G;
    }
}
