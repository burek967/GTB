package gtb.controller.mouse;

import gtb.controller.EditWindow;
import gtb.controller.ImportWindow;
import gtb.model.*;
import gtb.model.operations.ActionsManager;
import gtb.model.operations.Reverseable;
import gtb.view.GraphRenderer;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * Created by angela on 6/1/16.
 */
public class EditMode implements MouseMode {
    @Override
    public Reverseable onPress(MouseEvent event, GraphRenderer renderer, Graph graph) {
        return ActionsManager.NO_ACTION;
    }

    @Override
    public Reverseable onRelease(MouseEvent event, GraphRenderer renderer, Graph graph) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Position p = new Position(x, y);
        Vertex vertex = renderer.getVertexAt(p);
        if (vertex == null) {
            Edge selectedEdge = renderer.getEdgeAt(p);
            renderer.selectElement(selectedEdge);
        }
        else {
            EditWindow win = new EditWindow(((Node)event.getSource()).getScene().getWindow(), vertex.getData());
            win.showAndWait();
            renderer.redraw();
        }
        renderer.redraw();
        return ActionsManager.NO_ACTION;
    }

    @Override
    public void onDrag(MouseEvent event, GraphRenderer renderer, Graph graph) {

    }

    @Override
    public void onScroll(ScrollEvent event, GraphRenderer renderer) {

    }

    @Override
    public void onElementRemoved(GraphElement e) {

    }
}
