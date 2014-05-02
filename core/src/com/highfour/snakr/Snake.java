package com.highfour.snakr;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Snake {

    private Texture img;
    private Rectangle rect = new Rectangle();
    private int direction = 1;
    private int speed = 100;

    private int lives = 3;

    public Snake(Texture img) {
        this.img = img;
        rect.x = 800 / 2 - (int) rect.width / 2;
        rect.y = 600 / 2 - (int) rect.width / 2;
        rect.height = 30;
        rect.width = 30;
    }

    // reset player position and remove a life
    private void resetPos() {
        rect.x = 800 / 2 - (int) rect.width / 2;
        rect.y = 600 / 2 - (int) rect.height / 2;
        if (lives >= 1) {
            lives--;
        }
    }

    /*
    Setter
    */

    public void setX(int x) {
        if (x < 0 || x > 800) {
            resetPos();
            return;
        }
        rect.x = x;
    }

    public void setY(int y) {
        if (y < 0 || y > 600) {
            resetPos();
            return;
        }
        rect.y = y;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /*
    Getter
    */

    public int getLives() {
        return lives;
    }

    public Texture getImg() {
        return img;
    }

    public Rectangle getRect() {
        return rect;
    }

    public int getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public float getX() {
        return rect.x;
    }

    public float getY() {
        return rect.y;
    }
}

