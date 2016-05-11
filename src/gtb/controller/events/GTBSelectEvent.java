package gtb.controller.events;

import gtb.model.GraphElement;
import javafx.event.Event;
import javafx.event.EventType;

public class GTBSelectEvent extends Event {

    private GraphElement elem;

    public static final EventType<GTBSelectEvent> ANY = new EventType<>(EventType.ROOT,"GTB_SELECTION");

    public static final EventType<GTBSelectEvent> SELECT = new EventType<>(GTBSelectEvent.ANY,"GTB_SELECTION_SELECTED");

    public static final EventType<GTBSelectEvent> DESELECT = new EventType<>(GTBSelectEvent.ANY,"GTB_SELECTION_DESELECTED");


    public GTBSelectEvent(EventType<? extends Event> eventType, GraphElement e) {
        super(eventType);
        elem = e;
    }
}
