package com.highfour.snakr;

/**
 * snakr - com.highfour.snakr
 * Created by Kilian Koeltzsch on 02. May 2014.
 */

public class SnakeSegment {

    private float pos_x;
    private float pos_y;
    private int direction;


    public final int width = 30;
    public final int height = 30;

    public SnakeSegment(float pos_x, float pos_y) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }

    public float getX() {
        return pos_x;
    }

    public void setX(float pos_x) {
        this.pos_x = pos_x;
    }

    public float getY() {
        return pos_y;
    }

    public void setY(float pos_y) {
        this.pos_y = pos_y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
