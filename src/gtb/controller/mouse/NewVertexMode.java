package gtb.controller.mouse;

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
public class NewVertexMode implements MouseMode {
    @Override
    public Reverseable onPress(MouseEvent event, GraphRenderer renderer, Graph graph) {
        return ActionsManager.NO_ACTION;
    }

    @Override
    public Reverseable onRelease(MouseEvent event, GraphRenderer renderer, Graph graph) {
        Vertex v = graph.addVertex();
        v.getData().setPosition(renderer.getPositionAt(new Position((float)event.getX(),(float) event.getY())));
        renderer.redraw();
        return new AddElementAction(v, graph);
    }

    @Override
    public void onDrag(MouseEvent event, GraphRenderer renderer, Graph graph) {

    }

    @Override
    public void onScroll(ScrollEvent event, GraphRenderer renderer) {

    }
}
