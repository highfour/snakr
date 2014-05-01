package com.highfour.snakr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen implements Screen {

    SpriteBatch batch;
    Texture img;
    int direction, speed;
    Rectangle highfour;

    public GameScreen(final Snakr game) {

        batch = new SpriteBatch();
        img = new Texture("highfour.png");

        highfour = new Rectangle();
        highfour.x = 800 / 2 - 200 / 2;
        highfour.y = 600 / 2 - 200 / 2;
        highfour.width = 200;
        highfour.height = 200;

        direction = 1;
        speed = 100;
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(img, highfour.x, highfour.y, 200, 200);
        batch.end();

        switch(direction){
            case 0:
                highfour.y += speed * Gdx.graphics.getDeltaTime();
                break;
            case 1:
                highfour.x += speed * Gdx.graphics.getDeltaTime();
                break;
            case 2:
                highfour.y -= speed * Gdx.graphics.getDeltaTime();
                break;
            case 3:
                highfour.x -= speed * Gdx.graphics.getDeltaTime();
                break;
            default:
                System.out.println("wrong direction");
        }


        if (direction % 2 == 0) {
            if(Gdx.input.isKeyPressed(Keys.LEFT)) direction = 3;
            if(Gdx.input.isKeyPressed(Keys.RIGHT)) direction = 1;
        } else {
            if(Gdx.input.isKeyPressed(Keys.DOWN)) direction = 2;
            if(Gdx.input.isKeyPressed(Keys.UP)) direction = 0;
        }

        if(highfour.x < 0) highfour.x = 0;
        if(highfour.x > 800 - 200) highfour.x = 800 - 200;
        if(highfour.y < 0) highfour.y = 0;
        if(highfour.y > 600 - 200) highfour.y = 600 - 200;

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
