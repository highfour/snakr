package com.highfour.snakr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * snakr - com.highfour.snakr
 * Created by Kilian Koeltzsch on 30. May 2014.
 */

public class GameOverScreen implements Screen {

    Snakr game;
    OrthographicCamera camera;
    int player;
    Color player1_color = new Color(153/255f, 196/255f, 84/255f, 1);
    Color player2_color = new Color(106/255f, 131/255f, 177/255f, 1);

    public GameOverScreen(Snakr game, int player) {
        this.game = game;
        this.player = player;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.begin();
        game.font.setColor(Color.BLACK);
        game.font.setScale(4f);
        game.font.draw(game.batch, "Game Over!", 250, 330);
        if (player == 1) {
            game.font.setColor(player1_color);
            game.font.draw(game.batch, "Gratulation, Player 1", 160, 260);
        } else {
            game.font.setColor(player2_color);
            game.font.draw(game.batch, "Gratulation, Player 2", 160, 260);
        }
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
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
