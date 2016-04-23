package gtb.controller.mouse;

import gtb.view.GraphRenderer;
import javafx.scene.input.MouseEvent;

public class MoveMode implements MouseMode {

    private int lastX = -1;
    private int lastY = -1;

    @Override
    public void onPress(MouseEvent event, GraphRenderer renderer) {
        lastX = (int) event.getX();
        lastY = (int) event.getY();
    }

    @Override
    public void onRelease(MouseEvent event, GraphRenderer renderer) {
        lastX = lastY = -1;
    }

    @Override
    public void onDrag(MouseEvent event, GraphRenderer renderer) {
        if(lastX != -1)
            renderer.changeOffset(lastX - (int) event.getX(), lastY - (int) event.getY());
        lastX = (int) event.getX();
        lastY = (int) event.getY();
    }
}
