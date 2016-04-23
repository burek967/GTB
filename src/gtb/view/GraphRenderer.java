package gtb.view;

import gtb.model.Graph;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GraphRenderer {
    private Canvas canvas;
    private GraphicsContext ctx;
    private int xOffset=0; // for later :P
    private int yOffset=0;

    public GraphRenderer(Canvas c){
        canvas = c;
        ctx = canvas.getGraphicsContext2D();
    }

    public void redraw(Graph G){
        ctx.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        ctx.setFill(Color.RED);
        ctx.fillRect(0,0,10,10);
        ctx.fillRect(canvas.getWidth()-10,0,10,10);
        ctx.fillRect(canvas.getWidth()-10,canvas.getHeight()-10,10,10);
        ctx.fillRect(0,canvas.getHeight()-10,10,10);
        ctx.strokeLine(0,0,canvas.getWidth(),canvas.getHeight());
        ctx.strokeLine(0,canvas.getHeight(),canvas.getWidth(),0);
    }
}
