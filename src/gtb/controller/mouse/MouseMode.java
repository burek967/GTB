package gtb.controller.mouse;

import gtb.model.Graph;
import gtb.model.GraphElement;
import gtb.model.operations.Reverseable;
import gtb.view.GraphRenderer;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public interface MouseMode {

    Reverseable onPress(MouseEvent event, GraphRenderer renderer, Graph graph);
    Reverseable onRelease(MouseEvent event, GraphRenderer renderer, Graph graph);
    void onDrag(MouseEvent event, GraphRenderer renderer, Graph graph);
    void onScroll(ScrollEvent event, GraphRenderer renderer);
    void onElementRemoved(GraphElement e);
}
