package gtb.controller.mouse;

import gtb.model.Graph;
import gtb.model.Position;
import gtb.model.Vertex;
import gtb.view.GraphRenderer;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * Created by angela on 4/27/16.
 */
public class NewUndirectedEdgeMode implements MouseMode {
    private Vertex v1;

    @Override
    public void onPress(MouseEvent event, GraphRenderer renderer, Graph graph) {

    }

    @Override
    public void onRelease(MouseEvent event, GraphRenderer renderer, Graph graph) {
        Vertex v = renderer.getVertexAt(new Position((float)event.getX(), (float)event.getY()));
        if(v == null) return;
        if(v1 == null) {
            v1 = v;
            return;
        }
        graph.addUndirectedEdge(v1, v);
        v1 = null;
        renderer.redraw();
    }

    @Override
    public void onDrag(MouseEvent event, GraphRenderer renderer, Graph graph) {

    }

    @Override
    public void onScroll(ScrollEvent event, GraphRenderer renderer) {

    }
}
