package gtb.model.operations;

import gtb.controller.events.GTBActionEvent;
import gtb.model.Graph;
import javafx.scene.control.MenuItem;

import java.util.Stack;

/**
 * Created by angela on 5/4/16.
 */
public class ActionsManager {
    private Stack<Reverseable> undoStack = new Stack<>();
    private Stack<Reverseable> redoStack = new Stack<>();
    private Graph g;
    public static final Reverseable NO_ACTION = new NoOperation();
    private MenuItem undoButton;
    private MenuItem redoButton;

    public boolean canRedo(){
        return !redoStack.isEmpty();
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    private static class NoOperation implements Reverseable {

        @Override
        public void reverse(Graph g) {

        }

        @Override
        public void doIt(Graph g) {

        }
    }

    public ActionsManager(Graph g, MenuItem undo, MenuItem redo) {
        this.g = g;
        this.undoButton = undo;
        this.redoButton = redo;
    }

    public void undo() {
        if(undoStack.empty()) return;
        Reverseable r = undoStack.pop();
        r.reverse(g);
        redoStack.push(r);
        // I'm probably putting this in wrong place, oh well
        GTBActionEvent.fireEvent(undoButton,new GTBActionEvent(GTBActionEvent.ACTION_UNDO,r));
    }

    public void redo() {
        if(redoStack.empty()) return;
        Reverseable r = undoStack.pop();
        r.doIt(g);
        undoStack.push(redoStack.pop());
        GTBActionEvent.fireEvent(redoButton,new GTBActionEvent(GTBActionEvent.ACTION_REDO,r));
    }

    public void addOperation(Reverseable operation) {
        if(operation == NO_ACTION) return;
        redoStack.clear();
        undoStack.add(operation);
    }
}
