package gtb.controller.events;

import gtb.model.operations.Reverseable;
import javafx.event.Event;
import javafx.event.EventType;

// TODO think of fancier name
public class GTBActionEvent extends Event {

    public static final EventType<GTBActionEvent> ANY = new EventType<>(EventType.ROOT, "GTB_ACTION");
    public static final EventType<GTBActionEvent> ACTION_FIRED = new EventType<>(GTBActionEvent.ANY, "GTB_ACTION_FIRED");
    public static final EventType<GTBActionEvent> ACTION_UNDO = new EventType<>(GTBActionEvent.ANY, "GTB_ACTION_UNDO");
    public static final EventType<GTBActionEvent> ACTION_REDO = new EventType<>(GTBActionEvent.ANY, "GTB_ACTION_REDO");
    private Reverseable action;

    public GTBActionEvent(EventType<? extends Event> eventType, Reverseable r) {
        super(eventType);
        this.action = r;
    }
}
