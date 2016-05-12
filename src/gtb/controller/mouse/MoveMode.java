package gtb.controller.mouse;

import gtb.model.*;
import gtb.model.operations.ActionsManager;
import gtb.model.operations.Reverseable;
import gtb.view.GraphRenderer;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class MoveMode implements MouseMode {

    private int lastX = -1;
    private int lastY = -1;
    private Vertex vertexDragged = null;

    @Override
    public Reverseable onPress(MouseEvent event, GraphRenderer renderer, Graph graph) {
        lastX = (int) event.getX();
        lastY = (int) event.getY();
        Position p = new Position(lastX, lastY);
        vertexDragged = renderer.getVertexAt(p);
        if(vertexDragged == null) {
            Edge selectedEdge = renderer.getEdgeAt(p);
            renderer.selectElement(selectedEdge);
        }
        else
            renderer.selectElement(vertexDragged);
        renderer.redraw();
        return ActionsManager.NO_ACTION;
    }

    @Override
    public Reverseable onRelease(MouseEvent event, GraphRenderer renderer, Graph graph) {
        lastX = lastY = -1;
        vertexDragged = null;
        return ActionsManager.NO_ACTION;
    }

    @Override
    public void onDrag(MouseEvent event, GraphRenderer renderer, Graph graph) {
        if(vertexDragged != null) {
            vertexDragged.getData().setPosition(renderer.getPositionAt(
                    new Position((float)event.getX(), (float)event.getY())));
            renderer.redraw();
        }
        else {
            if (lastX != -1)
                renderer.changeOffset(lastX - (int) event.getX(), lastY - (int) event.getY());
            lastX = (int) event.getX();
            lastY = (int) event.getY();
        }
    }

    public void onScroll(ScrollEvent event, GraphRenderer renderer){
        renderer.changeScale(event.getDeltaY(),(int) event.getX(),(int) event.getY());
    }

    @Override
    public void onElementRemoved(GraphElement e) {

    }
}
