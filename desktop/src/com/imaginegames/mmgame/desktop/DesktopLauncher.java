package com.imaginegames.mmgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.imaginegames.mmgame.GameControl;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
		final String GENERATION = "Alpha"; 
		final String RELEASE = "0.1";
				
		final String VERSION = GENERATION + " " + RELEASE;
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		//config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width - 100;
        //config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height - 200;
		config.width = GameControl.WIDTH;
		config.height = GameControl.HEIGHT;
        config.resizable = true;
		config.foregroundFPS = 60;
		config.title = "Millitary Madness" + " " + VERSION;
		new LwjglApplication(new GameControl(), config);
	}
}