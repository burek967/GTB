package gtb.view;

import gtb.model.*;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class GraphRenderer {
    private Canvas canvas;
    private GraphicsContext ctx;
    private Graph graph;
    private int xOffset=0;
    private int yOffset=0;
    private float scale=1.0f;
    private boolean debugInfo=false;
    
    private static final float arrowSize = 15, vertexRadius = 20;

    private GraphElement selectedElement;

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

    public GraphRenderer(Canvas c, Graph graph){
        canvas = c;
        ctx = canvas.getGraphicsContext2D();
        this.graph = graph;
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
        if(selectedElement == e)
            ctx.setStroke(Color.RED);
        else
            ctx.setStroke(Color.GREEN);
        Position p1 = e.getFirstVertex().getData().getPosition();
        Position p2 = e.getSecondVertex().getData().getPosition();
        float p1x = scale*p1.getX()+xOffset, p1y = scale*p1.getY()+yOffset,
                p2x = scale*p2.getX()+xOffset, p2y = scale*p2.getY()+yOffset;
        float dx = p2x-p1x;
        float dy = p2y-p1y;
        float d = (float)Math.sqrt(dx*dx+dy*dy);
        float r = scale * vertexRadius;
        float arr = Math.min(scale,1.5f) * arrowSize;
        ctx.strokeLine(p1x+r*dx/d, p1y+r*dy/d,
                p2x-r*dx/d, p2y-r*dy/d);
        if(e.isDirected()) {
            if(selectedElement == e)
                ctx.setFill(Color.RED);
            else
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

        Position p = v.getData().getPosition();
        float r = scale * vertexRadius;
        float x = scale*p.getX()+xOffset, y = scale*p.getY()+yOffset;

        ctx.setFill(Color.GREENYELLOW);
        ctx.fillOval(x-r, y-r, 2*r, 2*r);

        if(v == selectedElement) {
            ctx.setStroke(Color.RED);
            ctx.setLineWidth(2.0);

            ctx.strokeOval(x-r, y-r, 2*r, 2*r);
            ctx.setLineWidth(1.0);
            ctx.setFill(new RadialGradient(0, 0, 0.5, 0.5, 1, true,
                    CycleMethod.REFLECT, new Stop(0, new Color(1, 1, 1, 0.5)), new Stop(0.7, Color.TRANSPARENT)));
            ctx.fillOval(x-r, y-r, 2*r, 2*r);
        }
        else {
            ctx.setStroke(Color.GREEN);
            ctx.strokeOval(x-r, y-r, 2*r, 2*r);
        }

        ctx.setFill(Color.BLACK);
        ctx.fillText(String.valueOf(v.getData().getId()), x, y);
    }

    /**
     * Converts mouse / screen position to model position
     */
    public Position getPositionAt(Position p) {
        return new Position((p.getX()-xOffset)/scale, (p.getY()-yOffset)/scale);
    }

    /**
     * Get vertex at mouse/screen position p
     * Returns null if no vertex
     */
    public Vertex getVertexAt(Position p) {
        p = getPositionAt(p);
        for (Vertex v: graph.getVertices()) {
            Position pv = v.getData().getPosition();
            float dx = p.getX()-pv.getX();
            float dy = p.getY()-pv.getY();
            if(dx*dx+dy*dy <= vertexRadius*vertexRadius) {
                return v;
            }
        }
        return null;
    }

    /**
     * Get edge at mouse/screen position p
     * Returns null if no edge
     */
    public Edge getEdgeAt(Position p) {
        p = getPositionAt(p);
        for(Edge e : graph.getEdges()) {
            Position pv1 = e.getFirstVertex().getData().getPosition();
            Position pv2 = e.getSecondVertex().getData().getPosition();
            float A = pv1.getY()-pv2.getY();
            float B = pv2.getX()-pv1.getX();
            float C = pv1.getX()*pv2.getY()-pv1.getY()*pv2.getX();
            float l = (A*p.getX()+B*p.getY()+C);
            float d2 = l*l/(A*A+B*B);
            if(d2 > 100) continue;
            if(p.getX() <= Math.max(pv1.getX(), pv2.getX())+5 &&
                p.getX() >= Math.min(pv1.getX(), pv2.getX())-5 &&
                p.getY() <= Math.max(pv1.getY(), pv2.getY())+5 &&
                p.getY() >= Math.min(pv1.getY(), pv2.getY())-5)
                return  e;
        }
        return null;
    }

    public void selectElement(GraphElement e) {
        selectedElement = e;
    }

    public GraphElement getSelectedElement() {
        return selectedElement;
    }
}
