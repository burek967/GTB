package gtb.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GraphRenderer {
    private Canvas canvas;
    private GraphicsContext ctx;
    private int xOffset=0;
    private int yOffset=0;
    private double scale=1.0; // todo someday ;_;
    private boolean debugInfo=false;

    public void changeOffset(int dx, int dy){
        xOffset -= dx;
        yOffset -= dy;
        redraw();
    }

    public GraphRenderer(Canvas c){
        canvas = c;
        ctx = canvas.getGraphicsContext2D();
    }

    public void setDebugInfo(boolean b){
        debugInfo = b;
        redraw();
    }

    private void rect(double a, double b, double c, double d){
        ctx.fillRect(a+xOffset,b+yOffset,c,d);
    }

    private void line(double a, double b, double c, double d){
        ctx.strokeLine(a+xOffset,b+yOffset,c+xOffset,d+yOffset);
    }

    public void redraw(){
        ctx.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        ctx.setFill(Color.RED);
        rect(0,0,10,10);
        rect(canvas.getWidth()-10,0,10,10);
        rect(canvas.getWidth()-10,canvas.getHeight()-10,10,10);
        rect(0,canvas.getHeight()-10,10,10);
        line(0,0,canvas.getWidth(),canvas.getHeight());
        line(0,canvas.getHeight(),canvas.getWidth(),0);

        if(debugInfo) {
            ctx.setFont(Font.font("DejaVu Sans Mono",15.0));
            ctx.setFill(Color.BLACK);
            StringBuilder sb = new StringBuilder();
            sb.append("canvas:  ").append((int)canvas.getWidth()).append("x").append((int)canvas.getHeight()).append('\n');
            sb.append("xOffset: ").append(xOffset).append('\n');
            sb.append("yOffset: ").append(yOffset).append('\n');
            ctx.fillText(sb.toString(), 20, canvas.getHeight() - 50);
        }
    }
}
