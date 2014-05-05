package com.highfour.snakr;

import com.badlogic.gdx.math.Rectangle;

public class Snake {

    private Rectangle rect = new Rectangle();
    private int size = 20;

    public Snake(float x, float y) {
        rect.x = x - rect.width / 2;
        rect.y = y - rect.width / 2;
        rect.height = size;
        rect.width = size;
    }

    /*****
    GETTER
    *****/

    public float getX() {
        return rect.x;
    }

    public float getY() {
        return rect.y;
    }

    public int getSize() {
        return size;
    }

    /*****
    SETTER
    *****/

    public void setPos(float x, float y) {
        rect.x = x;
        rect.y = y;
    }
}

