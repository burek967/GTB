package gtb.controller.mouse;

import gtb.model.Graph;
import gtb.view.GraphRenderer;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public interface MouseMode {

    void onPress(MouseEvent event, GraphRenderer renderer, Graph graph);
    void onRelease(MouseEvent event, GraphRenderer renderer, Graph graph);
    void onDrag(MouseEvent event, GraphRenderer renderer, Graph graph);
    void onScroll(ScrollEvent event, GraphRenderer renderer);

}
