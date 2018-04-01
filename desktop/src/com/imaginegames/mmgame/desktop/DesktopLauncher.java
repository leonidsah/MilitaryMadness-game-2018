package com.imaginegames.mmgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.imaginegames.mmgame.GameControl;

public class DesktopLauncher {
	public static void main (String[] arg) {
			
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		//config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width - 100;
        //config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height - 200;
		config.width = 1100;
		config.height = 850;
        config.resizable = true;
        config.fullscreen = GameControl.FULLSCREEN;
		config.foregroundFPS = 60;
		config.title = "Millitary Madness" + " " + GameControl.VERSION;
		new LwjglApplication(new GameControl(), config);
	}
}