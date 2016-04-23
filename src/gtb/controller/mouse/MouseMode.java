package gtb.controller.mouse;

import gtb.view.GraphRenderer;
import javafx.scene.input.MouseEvent;

public interface MouseMode {

    void onPress(MouseEvent event, GraphRenderer renderer);
    void onRelease(MouseEvent event, GraphRenderer renderer);
    void onDrag(MouseEvent event, GraphRenderer renderer);

}
