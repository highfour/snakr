package com.highfour.snakr.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.highfour.snakr.Snakr;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "snakr";
        config.width = 800;
        config.height = 600;
        config.fullscreen = false;
        config.resizable = false;
        new LwjglApplication(new Snakr(), config);
    }
}
