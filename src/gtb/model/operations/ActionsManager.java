package gtb.model.operations;

import gtb.model.Graph;

import java.util.Stack;

/**
 * Created by angela on 5/4/16.
 */
public class ActionsManager {
    private Stack<Reverseable> undoStack = new Stack<>();
    private Stack<Reverseable> redoStack = new Stack<>();
    private Graph g;
    public static final Reverseable NO_ACTION = new NoOperation();

    private static class NoOperation implements Reverseable {

        @Override
        public void reverse(Graph g) {

        }

        @Override
        public void doIt(Graph g) {

        }
    }

    public ActionsManager(Graph g) {
        this.g = g;
    }

    public void undo() {
        if(undoStack.empty()) return;
        Reverseable r = undoStack.pop();
        r.reverse(g);
        redoStack.push(r);
    }

    public void redo() {
        if(redoStack.empty()) return;
        Reverseable r = undoStack.pop();
        r.doIt(g);
        undoStack.push(redoStack.pop());
    }

    public void addOperation(Reverseable operation) {
        if(operation == NO_ACTION) return;
        redoStack.clear();
        undoStack.add(operation);
    }
}
