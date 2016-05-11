package gtb.controller.mouse;

import gtb.model.Edge;
import gtb.model.Graph;
import gtb.model.Position;
import gtb.model.Vertex;
import gtb.model.operations.AddElementAction;
import gtb.model.operations.ActionsManager;
import gtb.model.operations.Reverseable;
import gtb.view.GraphRenderer;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * Created by angela on 4/27/16.
 */
public class NewEdgeMode implements MouseMode{
    private Vertex v1;
    private boolean directed = false;

    public NewEdgeMode(boolean directed) {
        this.directed = directed;
    }

    @Override
    public Reverseable onPress(MouseEvent event, GraphRenderer renderer, Graph graph) {
        return ActionsManager.NO_ACTION;
    }

    @Override
    public Reverseable onRelease(MouseEvent event, GraphRenderer renderer, Graph graph) {
        Vertex v = renderer.getVertexAt(new Position((float)event.getX(), (float)event.getY()));
        if(v == null) return ActionsManager.NO_ACTION;
        if(v1 == null) {
            v1 = v;
            renderer.selectElement(v);
            renderer.redraw();
            return ActionsManager.NO_ACTION;
        }
        //deselect
        if(v1 == v) {
            v1 = null;
            renderer.selectElement(null);
            renderer.redraw();
            return ActionsManager.NO_ACTION;
        }
        Edge e;
        if(directed)
            e = graph.addDirectedEdge(v1, v);
        else
            e = graph.addUndirectedEdge(v1, v);
        v1 = null;
        renderer.selectElement(null);
        renderer.redraw();
        return new AddElementAction(e, graph);
    }

    @Override
    public void onDrag(MouseEvent event, GraphRenderer renderer, Graph graph) {

    }

    @Override
    public void onScroll(ScrollEvent event, GraphRenderer renderer) {

    }
}
