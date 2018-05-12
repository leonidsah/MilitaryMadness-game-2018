package com.imaginegames.mmgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.imaginegames.mmgame.screens.LoadingScreen;

public class GameControl extends Game {

	public SpriteBatch batch;

	public static final int WIDTH = 1250;
	public static final int HEIGHT = 860;

	public static final String GENERATION = "Alpha";
	public static final String RELEASE = "0.4.4.1"; //0.4.1 Android release + new textures 0.4.1.3 - new texture for player and touch buttons, now events hidden in t/0.4.1.4 - bullets added compare with rockets
	// 0.4.2 - Optimization update: needed assetmanager/new buttons
	public static final String WELCOME_TITLE = "";
	public static final String VERSION = GENERATION + " " + RELEASE;

	public static boolean ENGLISH_LANGUAGE = false;
	public static boolean SHOW_STAT = false;
	public static float GAMESPEED = 1.0f;
	public static boolean FULLSCREEN = false;
	public static boolean UNDEAD = false;

	public StretchViewport viewport;
	public OrthographicCamera cam;
	public AssetManager assetManager;

	@Override
	public void create() {
		assetManager = new AssetManager();
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		viewport = new StretchViewport(WIDTH, HEIGHT, cam);
		viewport.apply();
		cam.position.set(WIDTH / 2, HEIGHT / 2, 0);
		cam.update();

		this.setScreen(new LoadingScreen(this));
	}

	@Override
	public void render() {
		//batch.setProjectionMatrix(cam.combined);
		super.render();
	}


	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		super.resize(width, height);
	}

}