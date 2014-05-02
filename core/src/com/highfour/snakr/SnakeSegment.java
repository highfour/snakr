package com.highfour.snakr;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

/**
 * snakr - com.highfour.snakr
 * Created by Kilian Koeltzsch on 02. May 2014.
 */

public class SnakeSegment {

    private Rectangle rect = new Rectangle();
    private Color segment_color;

    private float pos_x;
    private float pos_y;

    public final int width = 30;
    public final int height = 30;

    public SnakeSegment(Color segment_color, float pos_x, float pos_y) {
        this.segment_color = segment_color;
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
}
