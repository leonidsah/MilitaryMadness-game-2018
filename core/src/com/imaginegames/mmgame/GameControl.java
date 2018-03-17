package com.imaginegames.mmgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.imaginegames.mmgame.screens.MainMenuScreen;

public class GameControl extends Game {
	
	public SpriteBatch batch;
	
	public static final int WIDTH = 1100;
	public static final int HEIGHT = 850;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
}