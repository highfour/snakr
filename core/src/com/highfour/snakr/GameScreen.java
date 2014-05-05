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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

public class GameScreen implements Screen {

    Snakr game;

    // Snake 1 - green
    private LinkedList<Snake> player1 = new LinkedList<Snake>();
    private Color player1_color = new Color(153/255f, 196/255f, 84/255f, 1);
    private HashMap<String, Integer> player1_data = new HashMap<String, Integer>();

    // Snake 2 - blue
    private LinkedList<Snake> player2 = new LinkedList<Snake>();
    private Color player2_color = new Color(106/255f, 131/255f, 177/255f, 1);
    private HashMap<String, Integer> player2_data = new HashMap<String, Integer>();

    // Store items
    private Vector<Item> items = new Vector<Item>();

    // used for moving the player in certain amounts of time
    long time, oldtime;

    // the texture used for the snake's lives
    private Texture heart = new Texture("heart.png");

    public GameScreen(final Snakr game) {
        this.game = game;

        // have both players start of with nothing more than a square
        player1.add(new Snake(600, 460));
        player2.add(new Snake(200, 160));

        // initialize player data
        player1_data.put("lives", 3);
        player1_data.put("direction", 3);
        player1_data.put("length", 1);
        player1_data.put("dir_changed", 0);
        player1_data.put("number", 1);


        player2_data.put("lives", 3);
        player2_data.put("direction", 1);
        player2_data.put("length", 1);
        player2_data.put("dir_changed", 0);
        player2_data.put("number", 2);
    }

    @Override
    public void render (float delta) {
        int tmp_direction;

        // set background color to white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        /**********
        DRAW SHAPES
        **********/

        game.shapes.begin(ShapeType.Filled);
        Snake s;

        // draw items
        for (Item item : items) {
            game.shapes.setColor(item.getColor());
            game.shapes.rect(item.getX(), item.getY(), item.getSize(), item.getSize());
        }


        // draw player1 on the screen
        game.shapes.setColor(player1_color);
        for (int i=0; i<player1.size(); i++) {
            s = player1.get(i);
            if(i>0) {               // the tail of the snake is smaller than the head
                game.shapes.rect(s.getX()+1, s.getY()+1, s.getSize()-2, s.getSize()-2);
            } else {
                game.shapes.rect(s.getX(), s.getY(), s.getSize(), s.getSize());
            }
        }

        // draw player2 on the screen
        game.shapes.setColor(player2_color);
        for (int i=0; i<player2.size(); i++) {
            s = player2.get(i);
            if(i>0) {               // the tail of the snake is smaller than the head
                game.shapes.rect(s.getX()+1, s.getY()+1, s.getSize()-2, s.getSize()-2);
            } else {
                game.shapes.rect(s.getX(), s.getY(), s.getSize(), s.getSize());
            }
        }

        game.shapes.end();

        /**********
        DRAW IMAGES
        **********/
        game.batch.begin();

        // draw hearts in upper right hand corner - player1
        for (int i = 0; i < player1_data.get("lives"); i++) {
            game.batch.draw(heart, 10 + 30*i, 560);
        }

        // draw hearts in upper left hand corner - player2
        for (int i = 0; i < player2_data.get("lives"); i++) {
            game.batch.draw(heart, 760 - 30*i, 560);
        }

        game.batch.end();


        /***********
        UPDATE STUFF
        ***********/
        time = TimeUtils.millis();
        // draw an new head for each player every x milliseconds, this could also
        // be separated for both players to allow for speed increasing powerups
        if (time - oldtime >= 200) { //DEBUG: this is a little fast, set the time up
            updatePos(player1, player1_data);
            updatePos(player2, player2_data);
            oldtime = time;
        }

        // place a new item on screen if there are no more
        if (items.size() < 3) genItem();


        /*************
        WAIT FOR INPUT
        *************/
        // ↑ ↓ ← → = Player 1
        if (player1_data.get("dir_changed") == 0) {
            tmp_direction = player1_data.get("direction");
            if (player1_data.get("direction") % 2 == 0) {
                if (Gdx.input.isKeyPressed(Keys.LEFT)) player1_data.put("direction", 3);
                if (Gdx.input.isKeyPressed(Keys.RIGHT)) player1_data.put("direction", 1);
            } else {
                if (Gdx.input.isKeyPressed(Keys.DOWN)) player1_data.put("direction", 2);
                if (Gdx.input.isKeyPressed(Keys.UP)) player1_data.put("direction", 0);
            }
            if (tmp_direction != player1_data.get("direction")) {
                player1_data.put("dir_changed", 1);
            }
        }

        // W A S D = Player 2
        if (player2_data.get("dir_changed") == 0) {
            tmp_direction = player2_data.get("direction");
            if (player2_data.get("direction") % 2 == 0) {
                if (Gdx.input.isKeyPressed(Keys.A)) player2_data.put("direction", 3);
                if (Gdx.input.isKeyPressed(Keys.D)) player2_data.put("direction", 1);
            } else {
                if (Gdx.input.isKeyPressed(Keys.S)) player2_data.put("direction", 2);
                if (Gdx.input.isKeyPressed(Keys.W)) player2_data.put("direction", 0);
            }
            if (tmp_direction != player2_data.get("direction")) {
                player2_data.put("dir_changed", 1);
            }
        }
    }

    private void updatePos(LinkedList<Snake> snake, HashMap<String, Integer> playerdata) {
        float firstX = snake.getFirst().getX();
        float firstY = snake.getFirst().getY();

        for (Item i : items) {
            if (snake.get(0).getX() == i.getX() && snake.get(0).getY() == i.getY()) {
                playerdata.put("length", playerdata.get("length") + 1);
                i.reposition();
                break;
            }
        }

        testCollision(snake, playerdata);

        switch(playerdata.get("direction")){
            case 0:
                snake.addFirst(new Snake(firstX, firstY + snake.getFirst().getSize()));
                break;
            case 1:
                snake.addFirst(new Snake(firstX + snake.getFirst().getSize(), firstY));
                break;
            case 2:
                snake.addFirst(new Snake(firstX, firstY - snake.getFirst().getSize()));
                break;
            case 3:
                snake.addFirst(new Snake(firstX - snake.getFirst().getSize(), firstY));
                break;
            default:
                System.out.println("wrong direction");
        }

        playerdata.put("dir_changed", 0);

        float newX = snake.getFirst().getX();
        float newY = snake.getFirst().getY();
        if (newX < 0 || newX >= 800 || newY < 0 || newY >= 600) {
            lostLive(snake, playerdata);
        }

        if (snake.size() > playerdata.get("length")) {
            snake.removeLast();
        }


    }

    private void lostLive(LinkedList<Snake> snake, HashMap<String, Integer> playerdata) {
        int lives = playerdata.get("lives");
        if (lives >= 1) {
            lives--;
            playerdata.put("lives", lives);
            resetPlayer(snake, playerdata);
        } else {
            // TODO: kill player
        }
    }

    private void testCollision(LinkedList<Snake> snake, HashMap<String, Integer> playerdata) {
        // CollisionTest for the snake itself
        for (int i=1; i<snake.size(); i++) {
            if (snake.get(0).getX() == snake.get(i).getX() && snake.get(0).getY() == snake.get(i).getY()) {
                lostLive(snake, playerdata);
            }
        }

        // CollisionTest with the other snake
        if (playerdata.get("number") == 1) {
            for (int i=1; i<player2.size(); i++) {
                if (snake.get(0).getX() == player2.get(i).getX() && snake.get(0).getY() == player2.get(i).getY()) {
                    lostLive(snake, playerdata);
                }
            }
        } else if (playerdata.get("number") == 2) {
            for (int i=1; i<player1.size(); i++) {
                if (snake.get(0).getX() == player1.get(i).getX() && snake.get(0).getY() == player1.get(i).getY()) {
                    lostLive(snake, playerdata);
                }
            }
        }

        // CollisionTest of the 2 heads
        if (player1.get(0).getX() == player2.get(0).getX() && player1.get(0).getY() == player2.get(0).getY()) {
            lostLive(player1, player1_data);
            lostLive(player2, player2_data);
        }
    }

    private void resetPlayer (LinkedList<Snake> snake, HashMap<String, Integer> playerdata) {
        // shorten snake back to three elements
        while (snake.size() > 1) {
            snake.removeLast();
            playerdata.put("length", 1);
        }

        // TODO: check if there's nothing there
        float randX = MathUtils.random(8,32);
        float randY = MathUtils.random(6,24);

        int randDirection = MathUtils.random(0,3);

        playerdata.put("direction", randDirection);
        snake.getFirst().setPos(randX*20, randY*20);
    }

    private void genItem () {
        float randX = MathUtils.random(0,40);
        float randY = MathUtils.random(0,30);
        // TODO: check if there's nothing there
        items.add(new Item(randX*20,randY*20));
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
