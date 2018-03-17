package com.imaginegames.mmgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.imaginegames.mmgame.GameControl;

public class MainMenuScreen implements Screen{
	
	private static final int EXIT_PWIDTH = 689;
	private static final int EXIT_PHEIGHT = 224;
	private static final int PLAY_PWIDTH = 650;
	private static final int PLAY_PHEIGHT = 200;
	
	private static float BUTTON_SCALE = 0.6f;
	
	private static float EXIT_WIDTH = EXIT_PWIDTH * BUTTON_SCALE;
	private static float EXIT_HEIGHT = EXIT_PHEIGHT * BUTTON_SCALE;
	private static float PLAY_WIDTH = PLAY_PWIDTH * BUTTON_SCALE;
	private static float PLAY_HEIGHT = PLAY_PHEIGHT * BUTTON_SCALE;
	private static final float EXIT_Y = 150;
	private static final float PLAY_Y = 450;
	


	public static float BUTTON_X = GameControl.WIDTH * 0.7f - EXIT_WIDTH / 2;
	public static float LOGO_WIDTH = 114;
	public static float LOGO_HEIGHT = 163;
	public static float LOGO_SCALE = 2.8f;
	
	private Animation<?>[] rolls;
	
	Texture exit;
	Texture exit_selected;
	Texture play;
	Texture play_selected;
	GameControl game;
	
	private float stateTime;
	
	public MainMenuScreen(GameControl game) {
		this.game = game;
	}

	@Override
	public void show() {
		exit = new Texture("exit_ru.png");
		exit_selected = new Texture("exit_selected_ru.png");
		play = new Texture("play_ru.png");
		play_selected = new Texture("play_selected_ru.png");
		
		rolls = new Animation[1];
		TextureRegion[][] player_animated_sheet = TextureRegion.split(new Texture("player_sheet.png"), GameScreen.PLAYER_PWIDTH, GameScreen.PLAYER_PHEIGHT);
		rolls[0] = new Animation<>(GameScreen.PLAYER_ANIMATION_SPEED, player_animated_sheet[0]);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.25f, 0.3f, 0.7f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += delta;
		game.batch.begin();
		game.batch.draw((TextureRegion)rolls[0].getKeyFrame(stateTime, true), (BUTTON_X - LOGO_WIDTH * LOGO_SCALE) / 2, EXIT_Y, LOGO_WIDTH * LOGO_SCALE, LOGO_HEIGHT * LOGO_SCALE);
		game.batch.draw(exit, BUTTON_X, EXIT_Y, EXIT_WIDTH, EXIT_HEIGHT);
		game.batch.draw(play, BUTTON_X, PLAY_Y, PLAY_WIDTH, PLAY_HEIGHT);
		
		if (Gdx.input.getX() > BUTTON_X && Gdx.input.getX() < BUTTON_X + EXIT_WIDTH && GameControl.HEIGHT - Gdx.input.getY() > EXIT_Y && GameControl.HEIGHT - Gdx.input.getY() < EXIT_Y + EXIT_HEIGHT) {
			game.batch.draw(exit_selected, BUTTON_X, EXIT_Y, EXIT_WIDTH, EXIT_HEIGHT);
			if (Gdx.input.isTouched()) {
				Gdx.app.exit();
			}
		}
		/* NOTICE: X when do .getX() counts from left, BUT Y is REVERSED and counts from UP to DOWN.
		* On upper side Y it's MINIMUM for example, in downer side it's MAXIMAL.
		*/
		if (Gdx.input.getX() > BUTTON_X && Gdx.input.getX() < BUTTON_X + PLAY_WIDTH && GameControl.HEIGHT - Gdx.input.getY() > PLAY_Y && GameControl.HEIGHT - Gdx.input.getY() < PLAY_Y + PLAY_HEIGHT) {
			game.batch.draw(play_selected, BUTTON_X, PLAY_Y, PLAY_WIDTH, PLAY_HEIGHT);
			if (Gdx.input.isTouched()) {
				this.dispose();
				game.setScreen(new GameScreen(game));
			}
		}
		
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}

}
