package gtb.controller.mouse;

import javafx.scene.Cursor;

public enum MouseModes {
    MOVE(Cursor.MOVE, new MoveMode()),
    ADD_VERTEX(Cursor.DEFAULT, new NewVertexMode()),
    ADD_DIRECTED_EDGE(Cursor.DEFAULT, new NewDirectedEdgeMode()),
    ADD_UNDIRECTED_EDGE(Cursor.DEFAULT, new NewUndirectedEdgeMode()),
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
