package com.imaginegames.mmgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.imaginegames.mmgame.GameControl;

public class MainMenuScreen implements Screen{
	
	/*
	RUS_CHARS = "йцукенгшщзхъфывапролджэячсмитьбюЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ";
	ENG_CHARS = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
	SYMBOLS = "1234567890!@#$%^&*()_-=+~`{}|/<>[],.:;'?";
	*/
	
	private static final float PLAY_Y = Gdx.graphics.getHeight() * 0.90f;
	private static final float SETTINGS_Y = PLAY_Y - Gdx.graphics.getHeight() * 0.12f;
	private static final float EXIT_Y = SETTINGS_Y - Gdx.graphics.getHeight() * 0.12f;


	public static float BUTTON_X = Gdx.graphics.getWidth() * 0.03f;

	public static float LOGO_WIDTH = 32;
	public static float LOGO_HEIGHT = 145;
	public static float LOGO_SCALE = 3f;
	public static float LOGO_ANIMATION_SPEED;
	
	private Animation<?>[] rolls;
	
	public BitmapFont menuFont, menuFont_s, menuFont_c;
	private float FONT_SCALE = 0.25f;
	private GlyphLayout playt, playt_s, settingst, settingst_s, exitt, exitt_s, version, fps_text;

	private Texture x_line, y_line;
	
	private GameControl game;
	
	private float stateTime;
	
	public MainMenuScreen(GameControl game) {
		this.game = game;
		LOGO_ANIMATION_SPEED = 0.2f / GameControl.GAMESPEED;
		x_line = new Texture("x_line.png");
		y_line = new Texture("y_line.png");
		
		menuFont = new BitmapFont(Gdx.files.internal("fonts/menu_s.fnt"));
		menuFont.getData().setScale(FONT_SCALE);

		menuFont_s = new BitmapFont(Gdx.files.internal("fonts/menu.fnt"));
		menuFont_s.getData().setScale(FONT_SCALE);
		
		menuFont_c = new BitmapFont(Gdx.files.internal("fonts/menu.fnt"));
		menuFont_c.getData().setScale(FONT_SCALE * 0.5f);
		menuFont_c.setColor(Color.SKY);
		
		playt = new GlyphLayout(menuFont, "Играть");
		playt_s = new GlyphLayout(menuFont_s, "Играть");
		exitt = new GlyphLayout(menuFont, "Выход");
		exitt_s = new GlyphLayout(menuFont_s, "Выход");
		settingst = new GlyphLayout(menuFont, "Настройки");
		settingst_s = new GlyphLayout(menuFont_s, "Настройки");
		version = new GlyphLayout(menuFont_c, "Версия " + GameControl.VERSION);
		
		rolls = new Animation[1];
		TextureRegion[][] player_animated_sheet = TextureRegion.split(new Texture("player_normal_sheet.png"), GameScreen.PLAYER_PWIDTH, GameScreen.PLAYER_PHEIGHT);
		rolls[0] = new Animation<>(LOGO_ANIMATION_SPEED, player_animated_sheet[0]);
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.250f, 0.250f, 0.600f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		fps_text = new GlyphLayout(menuFont_c, "FPS: " + Gdx.graphics.getFramesPerSecond());
		stateTime += delta;
        
		game.batch.begin();
		game.batch.draw((TextureRegion)rolls[0].getKeyFrame(stateTime, true), Gdx.graphics.getWidth() * 0.7f + LOGO_WIDTH * LOGO_SCALE, (Gdx.graphics.getHeight() - LOGO_HEIGHT * LOGO_SCALE) / 2, -LOGO_WIDTH * LOGO_SCALE, LOGO_HEIGHT * LOGO_SCALE);

		//Draw font
		menuFont.draw(game.batch, playt, BUTTON_X, PLAY_Y + playt.height);
		menuFont.draw(game.batch, settingst, BUTTON_X, SETTINGS_Y + settingst.height);
		menuFont.draw(game.batch, exitt, BUTTON_X, EXIT_Y + exitt.height);
		menuFont_c.draw(game.batch, version, 0, 0 + version.height * 1.2f);
		menuFont_c.draw(game.batch, fps_text, 0, 0 + version.height * 2.4f);

		/* NOTICE: X when do .getX() counts from left, BUT .getY() is REVERSED and counts from UP to DOWN.
		* On upper side Y it's MINIMUM for example, at bottom it's MAXIMAL.
		*/
		
		//Play button
		if (Gdx.input.getX() > BUTTON_X && Gdx.input.getX() < BUTTON_X + playt.width && Gdx.graphics.getHeight() - Gdx.input.getY() > PLAY_Y && Gdx.graphics.getHeight() - Gdx.input.getY() < PLAY_Y + playt.height) {
			menuFont_s.draw(game.batch, playt_s, BUTTON_X, PLAY_Y + playt.height);
			if (Gdx.input.justTouched()) {
				game.setScreen(new GameScreen(game));
				this.dispose();
			}
		}
		
		//Settings button
		if (Gdx.input.getX() > BUTTON_X && Gdx.input.getX() < BUTTON_X + settingst.width && Gdx.graphics.getHeight() - Gdx.input.getY() > SETTINGS_Y && Gdx.graphics.getHeight() - Gdx.input.getY() < SETTINGS_Y + settingst.height) {
			menuFont_s.draw(game.batch, settingst_s, BUTTON_X, SETTINGS_Y + settingst.height);
			if (Gdx.input.justTouched()) {
				game.setScreen(new SettingsMenuScreen(game));
				this.dispose();
			}
		}
		
		//Exit button
		if (Gdx.input.getX() > BUTTON_X && Gdx.input.getX() < BUTTON_X + exitt.width && Gdx.graphics.getHeight() - Gdx.input.getY() > EXIT_Y && Gdx.graphics.getHeight() - Gdx.input.getY() < EXIT_Y + exitt.height) {
			menuFont_s.draw(game.batch, exitt_s, BUTTON_X, EXIT_Y + exitt.height);
			if (Gdx.input.justTouched()) {
				Gdx.app.exit();
			}
		}
		//Show statistics
		if (GameControl.SHOW_STAT) {
			game.batch.draw(x_line, BUTTON_X, PLAY_Y, 1, playt.height);
			game.batch.draw(y_line, BUTTON_X, PLAY_Y, playt.width, 1);
			game.batch.draw(x_line, BUTTON_X + playt.width, PLAY_Y, 1, playt.height);
			game.batch.draw(y_line, BUTTON_X, PLAY_Y + playt.height, playt.width, 1);
			
			game.batch.draw(x_line, BUTTON_X, SETTINGS_Y, 1, settingst.height);
			game.batch.draw(y_line, BUTTON_X, SETTINGS_Y, settingst.width, 1);
			game.batch.draw(x_line, BUTTON_X + settingst.width, SETTINGS_Y, 1, settingst.height);
			game.batch.draw(y_line, BUTTON_X, SETTINGS_Y + settingst.height, settingst.width, 1);
			
			game.batch.draw(x_line, BUTTON_X, EXIT_Y, 1, exitt.height);
			game.batch.draw(y_line, BUTTON_X, EXIT_Y, exitt.width, 1);
			game.batch.draw(x_line, BUTTON_X + exitt.width, EXIT_Y, 1, exitt.height);
			game.batch.draw(y_line, BUTTON_X, EXIT_Y + exitt.height, exitt.width, 1);
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
