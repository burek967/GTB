package gtb.controller.mouse;

import javafx.scene.Cursor;

public enum MouseModes {
    MOVE(Cursor.MOVE, new MoveMode()),
    ADD(Cursor.DEFAULT, null),
    REMOVE(Cursor.CROSSHAIR, null);

    private Cursor cursor;
    private MouseMode mode;
    MouseModes(Cursor c, MouseMode m){
        cursor = c;
        mode = m;
    }
    public Cursor getCursor(){
        return cursor;
    }
    public MouseMode getHandlers(){
        return mode;
    }
}
