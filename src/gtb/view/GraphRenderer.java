package gtb.view;

import gtb.model.Edge;
import gtb.model.Graph;
import gtb.model.Position;
import gtb.model.Vertex;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class GraphRenderer {
    private Canvas canvas;
    private GraphicsContext ctx;
    private Graph graph;
    private int xOffset=0;
    private int yOffset=0;
    private double scale=1.0;
    private boolean debugInfo=false;
    
    private static final float arrowSize = 15, vertexRadius = 20;

    public void changeOffset(int dx, int dy){
        xOffset -= dx;
        yOffset -= dy;
        redraw();
    }

    public void changeScale(double dy, int x, int y){
        if(dy < 0 && scale <= 0.4)
            return;
        dy /= 400;
        double t = 1 + dy/scale;
        scale += dy;
        xOffset = (int)(t*(xOffset-x) + x);
        yOffset = (int)(t*(yOffset-y) + y);
        redraw();
    }

    public GraphRenderer(Canvas c){
        canvas = c;
        ctx = canvas.getGraphicsContext2D();
        graph = new Graph();
        Vertex v1 = graph.addVertex();
        v1.getData().setPosition(new Position(150, 100));
        Vertex v2 = graph.addVertex();
        v2.getData().setPosition(new Position(220, 350));
        Vertex v3 = graph.addVertex();
        v3.getData().setPosition(new Position(70, 220));
        graph.addDirectedEdge(v1, v2);
        graph.addDirectedEdge(v2, v3);
        graph.addDirectedEdge(v3, v1);
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
        drawGraph();

        if(debugInfo) {
            ctx.setFont(Font.font("DejaVu Sans Mono",15.0));
            ctx.setFill(Color.BLACK);
            StringBuilder sb = new StringBuilder();
            sb.append("canvas:  ").append((int)canvas.getWidth()).append("x").append((int)canvas.getHeight()).append('\n');
            sb.append("xOffset: ").append(xOffset).append('\n');
            sb.append("yOffset: ").append(yOffset).append('\n');
            sb.append("scale:   ").append(scale).append('\n');
            ctx.setTextAlign(TextAlignment.LEFT);
            ctx.setTextBaseline(VPos.BOTTOM);
            ctx.fillText(sb.toString(), 20, canvas.getHeight());
        }
    }

    private void drawGraph() {
        ctx.setFont(Font.font("DejaVu Sans Mono",13.0));
        ctx.setTextAlign(TextAlignment.CENTER);
        ctx.setTextBaseline(VPos.CENTER);
        graph.getVertices().forEach(this::drawVertex);
        graph.getEdges().forEach(this::drawEdge);
    }

    private void drawEdge(Edge e) {
        ctx.setStroke(Color.GREEN);
        Position p1 = e.getFirstVertex().getData().getPosition();
        Position p2 = e.getSecondVertex().getData().getPosition();
        float p1x = (float)scale*p1.getX()+xOffset, p1y = (float)scale*p1.getY()+yOffset, p2x = (float)scale*p2.getX()+xOffset, p2y = (float)scale*p2.getY()+yOffset;
        float dx = p2x-p1x;
        float dy = p2y-p1y;
        float d = (float)Math.sqrt(dx*dx+dy*dy);
        float r = (float) scale * vertexRadius;
        float arr = (float) Math.min(scale,1.5f) * arrowSize;
        ctx.strokeLine(p1x+r*dx/d, p1y+r*dy/d,
                p2x-r*dx/d, p2y-r*dy/d);
        if(e.isDirected()) {
            ctx.setFill(Color.GREEN);
            double xp = -arr*dx/d;
            double yp = -arr*dy/d;
            double ypp = Math.sqrt(arr*arr*xp*xp/(yp*yp+xp*xp)*0.25);
            double xpp = ypp*yp/xp;
            double[] ptx = new double[] {
                    p2x-r*dx/d,
                    p2x-r*dx/d+xp+xpp,
                    p2x-r*dx/d+xp-xpp
            };
            double[] pty = new double[] {
                    p2y-r*dy/d,
                    p2y-r*dy/d+yp-ypp,
                    p2y-r*dy/d+yp+ypp
            };

            ctx.fillPolygon(ptx, pty, 3);
        }
    }

    private void drawVertex(Vertex v) {
        ctx.setFill(Color.GREENYELLOW);
        Position p = v.getData().getPosition();
        float r = (float) scale * vertexRadius;
        float x = (float) scale*p.getX()+xOffset, y = (float) scale*p.getY()+yOffset;
        ctx.fillOval(x-r, y-r, 2*r, 2*r);
        ctx.setStroke(Color.GREEN);
        ctx.strokeOval(x-r, y-r, 2*r, 2*r);
        ctx.setFill(Color.BLACK);
        ctx.fillText(String.valueOf(v.getData().getId()), x, y);
    }
}
