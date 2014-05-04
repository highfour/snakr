package com.highfour.snakr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;

import java.util.LinkedList;

public class Snake {

    private Rectangle rect = new Rectangle();
    private Color snake_color = Color.BLACK;

    private float start_x;
    private float start_y;

    private int lives = 3;

    public Snake(Color color, float x, float y) {
        this.setColor(color);
        rect.x = x - rect.width / 2;
        rect.y = y - rect.width / 2;
        rect.height = 30;
        rect.width = 30;

        start_x = x - rect.width / 2;
        start_y = y - rect.height / 2;

    }

    // reset player position and remove a life
    // TODO: move to GameScreen (and think about this as well...)
//    private void resetPos() {
//        if (lives > 1) {
//            lives--;
//            direction = start_direction;
//            rect.x = start_x;
//            rect.y = start_y;
//        } else {
//            lives--;
//            snake_color = Color.BLACK;
//            speed = 0;
//        }
//    }

    /*
    Setter
    */

    public void setColor(Color color) {
        snake_color = color;
    }

    /*
    Getter
    */

    public int getLives() {
        return lives;
    }

    public Color getColor() {
        return snake_color;
    }

    public float getX() {
        return rect.x;
    }

    public float getY() {
        return rect.y;
    }

    public int getWidth() {
        return (int) rect.width;
    }

    public int getHeight() {
        return (int) rect.height;
    }
}

