package gtb.controller.mouse;

import gtb.model.Graph;
import gtb.model.Position;
import gtb.model.Vertex;
import gtb.view.GraphRenderer;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class MoveMode implements MouseMode {

    private int lastX = -1;
    private int lastY = -1;
    private Vertex vertexDragged = null;

    @Override
    public void onPress(MouseEvent event, GraphRenderer renderer, Graph graph) {
        lastX = (int) event.getX();
        lastY = (int) event.getY();
        vertexDragged = renderer.getVertexAt(new Position(lastX, lastY));
    }

    @Override
    public void onRelease(MouseEvent event, GraphRenderer renderer, Graph graph) {
        lastX = lastY = -1;
        vertexDragged = null;
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
}
