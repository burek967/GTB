package gtb.model.operations;

import gtb.model.Graph;
import gtb.model.GraphElement;

import java.util.List;

/**
 * Created by angela on 5/4/16.
 */
public class RemoveElementAction implements Reverseable {
    private GraphElement e;
    private List<? extends GraphElement> dependentElements;

    public RemoveElementAction(GraphElement e, List<? extends GraphElement> dependentElements) {
        this.e = e;
        this.dependentElements = dependentElements;
    }

    @Override
    public void reverse(Graph g) {
        e.addYourself(g);
        for (GraphElement e : dependentElements)
            e.addYourself(g);
    }

    @Override
    public void doIt(Graph g) {
        e.removeYourself(g);
    }
}
