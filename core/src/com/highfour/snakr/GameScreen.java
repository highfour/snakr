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
    private LinkedList<Snake> player1 = new LinkedList<Snake>();
    private Color player1_color = new Color(153/255f, 196/255f, 84/255f, 1);
    private int player1_lives = 3;
    private int player1_direction = 3;

    // Snake 2 - blue
    private LinkedList<Snake> player2 = new LinkedList<Snake>();
    private Color player2_color = new Color(106/255f, 131/255f, 177/255f, 1);
    private int player2_lives = 3;
    private int player2_direction = 1;

    // Store items
    private Vector<Item> items = new Vector<Item>();
    long time = 0; //debug: i'm being used to place and remove items so long as collisions don't yet work
    long oldtime; //debug: plz remove us after implementing that

    // the texture used for the snake's lives
    private Texture heart = new Texture("heart.png");

    public GameScreen(final Snakr game) {
        this.game = game;

        // have both players start of with nothing more than a square
        player1.add(new Snake(player1_color, 600, 450));
        player2.add(new Snake(player2_color, 200, 150));
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
        game.shapes.setColor(player1_color);
        for (Snake s : player1) {
            game.shapes.rect(s.getX(), s.getY(), s.getWidth(), s.getHeight());
        }

        // draw player2 on the screen
        game.shapes.setColor(player2_color);
        for (Snake s : player2) {
            game.shapes.rect(s.getX(), s.getY(), s.getWidth(), s.getHeight());
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
        for (int i = 0; i < player1_lives; i++) {
            game.batch.draw(heart, 10 + 30*i, 560);
        }

        // draw hearts in upper left hand corner - player2
        for (int i = 0; i < player2_lives; i++) {
            game.batch.draw(heart, 760 - 30*i, 560);
        }

        game.batch.end();



        // continuously update player positions
        time = TimeUtils.millis();
        if (time - oldtime >= 500) {
            updatePos(player1, player1_direction, player1_color);
            updatePos(player2, player2_direction, player2_color);
            oldtime = time;
        }

//        time = TimeUtils.millis();
//        if (time - oldtime >= 2000) {
//            // TODO: remove items on collision, not every two seconds...
//            items.removeAllElements();
//            oldtime = time;
//        }

        // place a new item on screen if there are no more
        if (items.isEmpty()) genItem();


        /*************
        WAIT FOR INPUT
        *************/
        // ↑ ↓ ← → = Player 1
        if (player1_direction % 2 == 0) {
            if (Gdx.input.isKeyPressed(Keys.LEFT)) player1_direction = 3;
            if (Gdx.input.isKeyPressed(Keys.RIGHT)) player1_direction = 1;
        } else {
            if (Gdx.input.isKeyPressed(Keys.DOWN)) player1_direction = 2;
            if (Gdx.input.isKeyPressed(Keys.UP)) player1_direction = 0;
        }

        // W A S D = Player 2
        if (player2_direction % 2 == 0) {
            if (Gdx.input.isKeyPressed(Keys.A)) player2_direction = 3;
            if (Gdx.input.isKeyPressed(Keys.D)) player2_direction = 1;
        } else {
            if (Gdx.input.isKeyPressed(Keys.S)) player2_direction = 2;
            if (Gdx.input.isKeyPressed(Keys.W)) player2_direction = 0;
        }

    }

    private void updatePos(LinkedList<Snake> snake, int direction, Color color) {
        float firstX = snake.getFirst().getX();
        float firstY = snake.getFirst().getY();

        // TODO: check for boundaries

        switch(direction){
            case 0:
                snake.addFirst(new Snake(color, firstX, firstY + snake.getFirst().getHeight()));
                break;
            case 1:
                snake.addFirst(new Snake(color, firstX + snake.getFirst().getWidth(), firstY));
                break;
            case 2:
                snake.addFirst(new Snake(color, firstX, firstY - snake.getFirst().getHeight()));
                break;
            case 3:
                snake.addFirst(new Snake(color, firstX - snake.getFirst().getWidth(), firstY));
                break;
            default:
                System.out.println("wrong direction");
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
