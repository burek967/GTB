package gtb.file_support;


import gtb.model.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by qwerty on 2016-04-27.
 */

public class GraphExport {
    public static void graphExport(Graph G, String path) throws FileNotFoundException, UnsupportedEncodingException{
        List<Vertex> vertices = G.getVertices();
        List<Edge> edges = G.getEdges();
        PrintWriter writer;
        writer = new PrintWriter(path, "UTF-8");
        writer.println(vertices.size()+" "+edges.size());
        for(Edge e: edges){
            writer.println(e.getFirstVertex().getData().getId()+" "+e.getSecondVertex().getData().getId());
            if(!e.isDirected())writer.println(e.getSecondVertex().getData().getId()+" "+e.getFirstVertex().getData().getId());
        }
        writer.close();
    }
}
