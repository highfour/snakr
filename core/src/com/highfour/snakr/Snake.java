package com.highfour.snakr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Snake {

    private Texture img;
    private Rectangle rect = new Rectangle();
    private int direction = 1;
    private int speed = 100;

    private int lives = 3;

    public Snake(Texture img, float x, float y) {
        this.img = img;
        rect.x = x - rect.width / 2;
        rect.y = y - rect.width / 2;
        rect.height = 30;
        rect.width = 30;
    }

    // reset player position and remove a life
    private void resetPos() {
        rect.x = 800 / 2 - rect.width / 2;
        rect.y = 600 / 2 - rect.height / 2;
        if (lives >= 1) {
            lives--;
        }
    }

    public void render() {
        switch(this.getDirection()){
            case 0:
                this.addY(this.getSpeed() * Gdx.graphics.getDeltaTime());
                break;
            case 1:
                this.addX(this.getSpeed() * Gdx.graphics.getDeltaTime());
                break;
            case 2:
                this.addY(-(this.getSpeed() * Gdx.graphics.getDeltaTime()));
                break;
            case 3:
                this.addX(-(this.getSpeed() * Gdx.graphics.getDeltaTime()));
                break;
            default:
                System.out.println("wrong direction");
        }
    }

    private void addX (float x) {
        float old_x = this.getX();
        old_x += x;
        this.setX(old_x);
    }

    private void addY (float y) {
        float old_y = this.getY();
        old_y += y;
        this.setY(old_y);
    }

    /*
    Setter
    */

    public void setX(float x) {
        if (x < 0 || x > 800 - this.rect.width) {
            resetPos();
            return;
        }
        rect.x = x;
    }

    public void setY(float y) {
        if (y < 0 || y > 600 - this.rect.height) {
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

