package com.highfour.snakr;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

/**
 * snakr - com.highfour.snakr
 * Created by Kilian Koeltzsch on 02. May 2014.
 */

public class Item {

    private Rectangle rect = new Rectangle();
    private Color item_color = Color.RED;

    private float pos_x;
    private float pos_y;

    private int width = 10;
    private int height = 10;

    public Item(float pos_x, float pos_y) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }

    public float getX() {
        return pos_x;
    }

    public float getY() {
        return pos_y;
    }

    public Color getColor() {
        return item_color;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
