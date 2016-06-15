package gtb.model;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by angela on 4/21/16.
 */
public class Position implements Serializable{
    private static Random rG = new Random();
    private float x, y;

    public Position() {
        this.x = rG.nextFloat() * 500;
        this.y = rG.nextFloat() * 500;
    }

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
