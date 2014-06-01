package com.highfour.snakr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

public class GameScreen implements Screen {

    Snakr game;

    // countdown numbers
    TextureAtlas numbers = new TextureAtlas("numbers.pack");

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
    long time, player1_time, player2_time;

    // the texture used for the snake's lives
    private Texture heart = new Texture("heart.png");

    boolean movement = true;

    public GameScreen(final Snakr game) {
        this.game = game;

        // have both players start of with nothing more than a square
        player1.add(new Snake(600, 460));
        player2.add(new Snake(200, 160));

        // initialize player data
        player1_data.put("lives", 3);
        player1_data.put("direction", 3);
        player1_data.put("length", 3);
        player1_data.put("dir_changed", 0);
        player1_data.put("number", 1);
        player1_data.put("time", 200);


        player2_data.put("lives", 3);
        player2_data.put("direction", 1);
        player2_data.put("length", 3);
        player2_data.put("dir_changed", 0);
        player2_data.put("number", 2);
        player2_data.put("time", 200);
    }

    @Override
    public void render (float delta) {
        int tmp_direction;

        // set background color to white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        TextureAtlas.AtlasRegion number_1 = numbers.findRegion("1");
        TextureAtlas.AtlasRegion number_2 = numbers.findRegion("2");
        TextureAtlas.AtlasRegion number_3 = numbers.findRegion("3");

        /**********
        DRAW SHAPES
        **********/

        game.shapes.begin(ShapeType.Filled);
        Snake s;

        // draw items
        for (Item item : items) {
            game.shapes.setColor(item.getColor());
            game.shapes.rect(item.getX()+1, item.getY()+1, item.getSize()-2, item.getSize()-2);
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

        if (movement) {
            time = TimeUtils.millis();
            // draw an new head for each player every x milliseconds
            if (time - player1_time >= player1_data.get("time")) {
                updatePos(player1, player1_data);
                player1_time = time;
            }

            if (time - player2_time >= player2_data.get("time")) {
                updatePos(player2, player2_data); // DEBUG: for singleplayer reasons
                player2_time = time;
            }
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

    private void repositionItem(Item item) {
        float randX = MathUtils.random(0,39);
        float randY = MathUtils.random(0,29);
        if (isOccupied(randX*20, randY*20)) {
            repositionItem(item);
        } else {
            item.setPos_x(randX * 20);
            item.setPos_y(randY * 20);
            float rando = MathUtils.random(0,100);
            if (rando > 75 && rando < 95) {
                item.setColor(Color.ORANGE);
            }
            if (rando >= 95) {
                item.setColor(Color.DARK_GRAY);
            }
        }
    }

    private void updatePos(LinkedList<Snake> snake, HashMap<String, Integer> playerdata) {
        float firstX = snake.getFirst().getX();
        float firstY = snake.getFirst().getY();

        for (Item i : items) {
            if (snake.get(0).getX() == i.getX() && snake.get(0).getY() == i.getY()) {
                playerdata.put("length", playerdata.get("length") + 1);
                if (i.getColor() == Color.ORANGE) {
                    playerdata.put("time", playerdata.get("time")-50);
                    i.setColor(Color.RED);
                } else if (i.getColor() == Color.DARK_GRAY) {
                    playerdata.put("time", 25);
                    i.setColor(Color.RED);
                } else {
                    playerdata.put("time", 200);
                }
                repositionItem(i);
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
            // game over, congratulate other player
            resetPlayer(snake, playerdata);
            if (snake == player1) {
                game.setScreen(new GameOverScreen(game, 2));
            } else {
                game.setScreen(new GameOverScreen(game, 1));
            }
        }
    }

    private void testCollision(LinkedList<Snake> snake, HashMap<String, Integer> playerdata) {
        // CollisionTest for the snake itself
        for (int i=4; i<snake.size(); i++) {
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

    private void countdown() {
        long time = TimeUtils.millis();
        long tmpTime = time;
        while (time - tmpTime < 3000) {
            time = TimeUtils.millis();
        }
    }

    private void resetPlayer (LinkedList<Snake> snake, HashMap<String, Integer> playerdata) {

        // dead snake turns black
        if (playerdata.get("number") == 1) {
            player1_color = Color.BLACK;
        }
        if (playerdata.get("number") == 2) {
            player2_color = Color.BLACK;
        }

        // stop the snakes
        movement = false;

        // display countdown
        countdown();

        // reset game

        player1.get(0).setPos(600, 460);
        player2.get(0).setPos(200, 160);
        player1_data.put("direction", 3);
        player2_data.put("direction", 1);
        player1_data.put("time", 200);
        player2_data.put("time", 200);
        player1_color = new Color(153/255f, 196/255f, 84/255f, 1);
        player2_color = new Color(106/255f, 131/255f, 177/255f, 1);
        player1_data.put("length", 3);
        while (player1.size() > 3) {
            player1.removeLast();
        }
        player2_data.put("length", 3);
        while (player2.size() > 3) {
            player2.removeLast();
        }

        movement = true;
    }

    public boolean isOccupied(float x, float y) {
        for (Snake s : player1) {
            if (s.getX() == x && s.getY() == y) {
                return true;
            }
        }
        for (Snake s : player2) {
            if (s.getX() == x && s.getY() == y) {
                return true;
            }
        }
        for (Item i : items) {
            if (i.getX() == x && i.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private void genItem () {
        float randX = MathUtils.random(0,39);
        float randY = MathUtils.random(0,29);
        if (isOccupied(randX*20, randY*20)) {
            genItem();
        } else {
            items.add(new Item(randX*20,randY*20));
        }
    }

    @Override
    public void resize(int width, int height) {    }

    @Override
    public void show() {    }

    @Override
    public void hide() {    }

    @Override
    public void pause() {    }

    @Override
    public void resume() {    }

    @Override
    public void dispose() {
        heart.dispose();
    }
}
