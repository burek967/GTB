package gtb.controller.mouse;

import gtb.view.GraphRenderer;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

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

    public void onScroll(ScrollEvent event, GraphRenderer renderer){
        System.out.println(event.getDeltaX() + " " + event.getDeltaY());
        System.out.println(event.getX() + " " + event.getY());
        renderer.changeScale(event.getDeltaY(),(int) event.getX(),(int) event.getY());
    }
}
