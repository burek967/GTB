package gtb.model.operations;

import gtb.controller.events.GTBActionEvent;
import gtb.model.Graph;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;

import java.util.Stack;

/**
 * Created by angela on 5/4/16.
 */
public class ActionsManager {
    public static final Reverseable NO_ACTION = new Reverseable() {
        @Override
        public void reverse(Graph g) {
        }

        @Override
        public void doIt(Graph g) {
        }
    };
    private final Scene scene;
    private final MenuItem undoButton;
    private final MenuItem redoButton;
    private Stack<Reverseable> undoStack = new Stack<>();
    private Stack<Reverseable> redoStack = new Stack<>();
    private Graph g;

    public ActionsManager(Graph g, MenuItem undo, MenuItem redo, Scene s) {
        this.g = g;
        this.undoButton = undo;
        this.redoButton = redo;
        this.scene = s;
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public void undo() {
        if (undoStack.empty()) return;
        Reverseable r = undoStack.pop();
        r.reverse(g);
        redoStack.push(r);
        // I'm probably putting this in wrong place, oh well
        GTBActionEvent.fireEvent(scene, new GTBActionEvent(GTBActionEvent.ACTION_UNDO, r));
    }

    public void redo() {
        if (redoStack.empty()) return;
        Reverseable r = redoStack.pop();
        r.doIt(g);
        undoStack.push(r);
        GTBActionEvent.fireEvent(scene, new GTBActionEvent(GTBActionEvent.ACTION_REDO, r));
    }

    public void addOperation(Reverseable operation) {
        if (operation == NO_ACTION) return;
        redoStack.clear();
        undoStack.add(operation);
        GTBActionEvent.fireEvent(scene, new GTBActionEvent(GTBActionEvent.ACTION_FIRED, operation));
    }

    public void reset(Graph g) {
        undoStack.clear();
        redoStack.clear();
        this.g = g;
    }
}
