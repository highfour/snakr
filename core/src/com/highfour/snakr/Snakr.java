package com.highfour.snakr;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Snakr extends Game {
	SpriteBatch batch;
	BitmapFont font;
    ShapeRenderer shapes;

    @Override
	public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
        shapes = new ShapeRenderer();
	}

	@Override
	public void render () {
        super.render();
	}

    @Override
    public void dispose () {
        batch.dispose();
    }
}
