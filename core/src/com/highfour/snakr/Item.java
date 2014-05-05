package com.highfour.snakr;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * snakr - com.highfour.snakr
 * Created by Kilian Koeltzsch on 02. May 2014.
 */

public class Item {

    private Rectangle rect = new Rectangle();
    private Color item_color = Color.RED;
    private int size = 20;

    private float pos_x;
    private float pos_y;

    public Item(float pos_x, float pos_y) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        rect.width = size;
        rect.height = size;
    }

    public void reposition() {
        float randX = MathUtils.random(0,39);
        float randY = MathUtils.random(0,29);
        this.pos_x = randX*20;
        this.pos_y = randY*20;
    }

    /*****
    GETTER
    *****/

    public float getX() {
        return pos_x;
    }

    public float getY() {
        return pos_y;
    }

    public Color getColor() {
        return item_color;
    }

    public int getSize() {
        return size;
    }
}
