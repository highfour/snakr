package com.highfour.snakr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;

import java.util.LinkedList;

public class Snake {

    private Rectangle rect = new Rectangle();
    private int direction;
    private int start_direction;
    private int speed = 100;
    private Color snake_color = Color.BLACK;

    private float start_x;
    private float start_y;

    private int lives = 3;

    private LinkedList<SnakeSegment> tail = new LinkedList<SnakeSegment>();

    public Snake(Color color, float x, float y, int direction) {
        this.setColor(color);
        rect.x = x - rect.width / 2;
        rect.y = y - rect.width / 2;
        rect.height = 30;
        rect.width = 30;

        this.direction = direction;
        this.start_direction = direction;

        start_x = x - rect.width / 2;
        start_y = y - rect.height / 2;

        for (int i = 0; i < 3; i++) {
            tail.add(new SnakeSegment(this.start_x, this.start_y));
            // FIXME: this will not be a good decision once a snake can collide with its own tail
        }
    }

    // reset player position and remove a life
    private void resetPos() {
        if (lives > 1) {
            lives--;
            direction = start_direction;
            rect.x = start_x;
            rect.y = start_y;
        } else {
            lives--;
            snake_color = Color.BLACK;
            speed = 0;
        }
    }

    public void updatePos() {

        moveTail(this.getX(), this.getY(), this.getDirection());

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

    // used in updatePos() to continuously add values to the snakes X and Y coordinates
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

    private void moveTail (float x, float y, int direction) {
        float tmpx, tmpy;
        int tmpdirection;
        int offset_x, offset_y;

        for (int i = 0; i < tail.size(); i++) {
            offset_x = 0;
            offset_y = 0;

            tmpx = tail.get(i).getX();
            tmpy = tail.get(i).getY();
            tmpdirection = tail.get(i).getDirection();

            switch(direction) {
                case 0:
                    offset_x = 0;
                    offset_y = -(2 + tail.get(i).width);
                    break;
                case 1:
                    offset_x = -(2 + tail.get(i).width);
                    offset_y = 0;
                    break;
                case 2:
                    offset_x = 0;
                    offset_y = (2 + tail.get(i).width);
                    break;
                case 3:
                    offset_x = (2 + tail.get(i).width);
                    offset_y = 0;
                    break;
                default:
                    System.out.println("wrong direction");
            }

            tail.get(i).setX(x + offset_x);
            tail.get(i).setY(y + offset_y);
            tail.get(i).setDirection(direction);

            x = tmpx;
            y = tmpy;
            direction = tmpdirection;
        }
    }

    /*
    Setter
    */

    public void setX(float x) {
        if (x < 0 || x > 800 - this.rect.width) {
            resetPos();
        } else {
            rect.x = x;
        }
    }

    public void setY(float y) {
        if (y < 0 || y > 600 - this.rect.height) {
            resetPos();
        } else {
            rect.y = y;
        }
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setColor(Color color) {
        snake_color = color;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /*
    Getter
    */

    public LinkedList<SnakeSegment> getTail() {
        return tail;
    }

    public int getLives() {
        return lives;
    }

    public int getDirection() {
        return direction;
    }

    public Color getColor() {
        return snake_color;
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

    public int getWidth() {
        return (int) rect.width;
    }

    public int getHeight() {
        return (int) rect.height;
    }
}

