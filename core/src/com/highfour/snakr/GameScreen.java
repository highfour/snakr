package com.highfour.snakr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.LinkedList;
import java.util.Vector;

public class GameScreen implements Screen {

    Snakr game;

    // Snake 1 - green
    private Snake player1 = new Snake(new Color(153/255f, 196/255f, 84/255f, 1), 600, 450, 3);
    // Snake 2 - blue
    private Snake player2 = new Snake(new Color(106/255f, 131/255f, 177/255f, 1), 200, 150, 1);

    // Store items
    private Vector<Item> items = new Vector<Item>();
    long time = 0; //debug: i'm being used to place and remove items so long as collisions don't yet work
    long oldtime; //debug: plz remove us after implementing that

    // the texture used for the snake's lives
    private Texture heart = new Texture("heart.png");

    public GameScreen(final Snakr game) {
        this.game = game;
    }

    @Override
    public void render (float delta) {
        // set background color to white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        /**********
        DRAW SHAPES
        **********/
        game.shapes.begin(ShapeType.Filled);

        // draw player1 on the screen
        game.shapes.setColor(player1.getColor());
        game.shapes.rect(player1.getX(), player1.getY(), player1.getWidth(), player1.getHeight());

        // draw player2 on the screen
        game.shapes.setColor(player2.getColor());
        game.shapes.rect(player2.getX(), player2.getY(), player2.getWidth(), player2.getHeight());

        // draw tail for player1
        LinkedList<SnakeSegment> tail1 = player1.getTail();
        game.shapes.setColor(player1.getColor());
        for (SnakeSegment snse : tail1) {
            game.shapes.rect(snse.getX(), snse.getY(), snse.width, snse.height);
        }

        // draw tail for player2
        LinkedList<SnakeSegment> tail2 = player2.getTail();
        game.shapes.setColor(player2.getColor());
        for (SnakeSegment snse : tail2) {
            game.shapes.rect(snse.getX(), snse.getY(), snse.width, snse.height);
        }

        // draw items
        for (Item item : items) {
            game.shapes.setColor(item.getColor());
            game.shapes.rect(item.getX(), item.getY(), item.getWidth(), item.getHeight());
        }

        game.shapes.end();


        /**********
        DRAW IMAGES
        **********/
        game.batch.begin();

        // draw hearts in upper right hand corner - player1
        for (int i = 0; i < player1.getLives(); i++) {
            game.batch.draw(heart, 10 + 30*i, 560);
        }

        // draw hearts in upper left hand corner - player2
        for (int i = 0; i < player2.getLives(); i++) {
            game.batch.draw(heart, 760 - 30*i, 560);
        }

        game.batch.end();



        // continuously update player positions
        player1.updatePos();
        player2.updatePos();

        time = TimeUtils.millis();
        if (time - oldtime >= 2000) {
            // TODO: remove items on collision, not every two seconds...
            items.removeAllElements();
            oldtime = time;
        }

        // place a new item on screen if there are no more
        if (items.isEmpty()) genItem();


        /*************
        WAIT FOR INPUT
        *************/
        // ↑ ↓ ← → = Player 1
        if (player1.getDirection() % 2 == 0) {
            if (Gdx.input.isKeyPressed(Keys.LEFT)) player1.setDirection(3);
            if (Gdx.input.isKeyPressed(Keys.RIGHT)) player1.setDirection(1);
        } else {
            if (Gdx.input.isKeyPressed(Keys.DOWN)) player1.setDirection(2);
            if (Gdx.input.isKeyPressed(Keys.UP)) player1.setDirection(0);
        }

        // W A S D = Player 2
        if (player2.getDirection() % 2 == 0) {
            if (Gdx.input.isKeyPressed(Keys.A)) player2.setDirection(3);
            if (Gdx.input.isKeyPressed(Keys.D)) player2.setDirection(1);
        } else {
            if (Gdx.input.isKeyPressed(Keys.S)) player2.setDirection(2);
            if (Gdx.input.isKeyPressed(Keys.W)) player2.setDirection(0);
        }

    }

    private void genItem () {
        float randX = MathUtils.random(50,750);
        float randY = MathUtils.random(50,550);
        // TODO: check if there's nothing there
        items.add(new Item(randX,randY));
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
        heart.dispose();
    }
}
