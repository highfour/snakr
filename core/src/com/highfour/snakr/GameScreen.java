package com.highfour.snakr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameScreen implements Screen {

    Snakr game;

    Snake player1 = new Snake(new Color(153/255f, 196/255f, 84/255f, 1), 600, 450);
    Snake player2 = new Snake(new Color(106/255f, 131/255f, 177/255f, 1), 200, 150);

    public GameScreen(final Snakr game) {
        this.game = game;
    }

    @Override
    public void render (float delta) {
        // set background color to white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw actors
        game.shapes.begin(ShapeType.Filled);
        game.shapes.setColor(player1.getColor());
        game.shapes.rect(player1.getX(), player1.getY(), 30, 30);
        game.shapes.setColor(player2.getColor());
        game.shapes.rect(player2.getX(), player2.getY(), 30, 30);
        game.shapes.end();

        // send everything to be rendered
        game.batch.begin();
        // change font color
        game.font.setColor(Color.BLACK);
        // draw lives text in upper right hand corner
        game.font.draw(game.batch, "Lives: " + player1.getLives(), 10, 590);
        game.font.draw(game.batch, "Lives: " + player2.getLives(), 740, 590);
        game.batch.end();

        // render player1 on the screen and update positions
        player1.render();
        player2.render();

        // accept input and prevent the player from turning 180 degrees
        if (player1.getDirection() % 2 == 0) {
            if (Gdx.input.isKeyPressed(Keys.LEFT)) player1.setDirection(3);
            if (Gdx.input.isKeyPressed(Keys.RIGHT)) player1.setDirection(1);
        } else {
            if (Gdx.input.isKeyPressed(Keys.DOWN)) player1.setDirection(2);
            if (Gdx.input.isKeyPressed(Keys.UP)) player1.setDirection(0);
        }

        // same for player2
        if (player2.getDirection() % 2 == 0) {
            if (Gdx.input.isKeyPressed(Keys.A)) player2.setDirection(3);
            if (Gdx.input.isKeyPressed(Keys.D)) player2.setDirection(1);
        } else {
            if (Gdx.input.isKeyPressed(Keys.S)) player2.setDirection(2);
            if (Gdx.input.isKeyPressed(Keys.W)) player2.setDirection(0);
        }

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

    }
}
