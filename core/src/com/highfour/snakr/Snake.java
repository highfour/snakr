package com.highfour.snakr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;

import java.util.LinkedList;

public class Snake {

    private Rectangle rect = new Rectangle();
    private int size = 20;

    public Snake(float x, float y) {
        rect.x = x - rect.width / 2;
        rect.y = y - rect.width / 2;
        rect.height = size;
        rect.width = size;
    }

    /*
    Getter
    */

    public float getX() {
        return rect.x;
    }

    public float getY() {
        return rect.y;
    }

    public int getSize() {
        return size;
    }
}

