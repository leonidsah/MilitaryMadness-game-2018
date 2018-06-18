package com.imaginegames.mmgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.imaginegames.mmgame.GameControl;

public class DesktopLauncher {
	public static void main (String[] arg) {
			
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width - 100;
        //config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height - 200;
		config.foregroundFPS = 120;
		config.vSyncEnabled = false;
		config.width = GameControl.WIDTH;
		config.height = GameControl.HEIGHT;
        config.fullscreen = GameControl.FULLSCREEN;
		config.title = "Military Madness" + " " + GameControl.VERSION;
		new LwjglApplication(new GameControl(), config);
	}
}