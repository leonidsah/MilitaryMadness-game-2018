package com.imaginegames.mmgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
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
	SYMBOLS = "1234567890!@#$%^&*()_-=+~`{}|/<>[],.:;'�?";
	*/
	
	private static final float PLAY_Y = Gdx.graphics.getHeight() * 0.90f;
	private static final float SETTINGS_Y = PLAY_Y - Gdx.graphics.getHeight() * 0.12f;
	private static final float EXIT_Y = SETTINGS_Y - Gdx.graphics.getHeight() * 0.12f;


	public static float BUTTON_X = Gdx.graphics.getWidth() * 0.03f;
	
	public static float LOGO_WIDTH = 114;
	public static float LOGO_HEIGHT = 163;
	public static float LOGO_SCALE = 3f;
	public static float LOGO_ANIMATION_SPEED;
	
	private Animation<?>[] rolls;
	
	public BitmapFont menuFont, menuFont_s, menuFont_c;
	private float FONT_SCALE = 0.25f;
	GlyphLayout playt, playt_s, settingst, settingst_s, exitt, exitt_s, version;
	
	Texture x_line, y_line;
	
	GameControl game;
	
	private float stateTime;
	
	public MainMenuScreen(GameControl game) {
		this.game = game;
		LOGO_ANIMATION_SPEED = 0.2f / GameControl.GAMESPEED;
		x_line = new Texture("x_line.png");
		y_line = new Texture("y_line.png");
		
		menuFont = new BitmapFont(Gdx.files.internal("fonts/menu_s.fnt"));
		menuFont_s = new BitmapFont(Gdx.files.internal("fonts/menu.fnt"));
		menuFont.getData().setScale(FONT_SCALE);
		menuFont_s.getData().setScale(FONT_SCALE);
		
		menuFont_c = new BitmapFont(Gdx.files.internal("fonts/menu.fnt"));
		menuFont_c.getData().setScale(FONT_SCALE * 0.3f);
		menuFont_c.setColor(Color.GRAY);
		
		playt = new GlyphLayout(menuFont, "������");
		playt_s = new GlyphLayout(menuFont_s, "������");
		exitt = new GlyphLayout(menuFont, "�����");
		exitt_s = new GlyphLayout(menuFont_s, "�����");
		settingst = new GlyphLayout(menuFont, "���������");
		settingst_s = new GlyphLayout(menuFont_s, "���������");
		version = new GlyphLayout(menuFont_c, "������ " + GameControl.VERSION);
		
		rolls = new Animation[1];
		TextureRegion[][] player_animated_sheet = TextureRegion.split(new Texture("player_sheet.png"), GameScreen.PLAYER_PWIDTH, GameScreen.PLAYER_PHEIGHT);
		rolls[0] = new Animation<>(LOGO_ANIMATION_SPEED, player_animated_sheet[0]);
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.690f, 0.769f, 0.871f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += delta;
		
		if (Gdx.input.isKeyJustPressed(Keys.TAB)) {
			if (!GameControl.XY_TRACKING) {
				GameControl.XY_TRACKING = true;
			}
			else {
				GameControl.XY_TRACKING = false;
			}
		}
		
		game.batch.begin();
		game.batch.draw((TextureRegion)rolls[0].getKeyFrame(stateTime, true), Gdx.graphics.getWidth() * 0.8f - LOGO_WIDTH * LOGO_SCALE, (Gdx.graphics.getHeight() - LOGO_HEIGHT * LOGO_SCALE) / 2, LOGO_WIDTH * LOGO_SCALE, LOGO_HEIGHT * LOGO_SCALE);

		
		menuFont.draw(game.batch, playt, BUTTON_X, PLAY_Y + playt.height);
		menuFont.draw(game.batch, settingst, BUTTON_X, SETTINGS_Y + settingst.height);
		menuFont.draw(game.batch, exitt, BUTTON_X, EXIT_Y + exitt.height);
		menuFont_c.draw(game.batch, version, 0, 0 + version.height * 1.2f);

		/* NOTICE: X when do .getX() counts from left, BUT .getY() is REVERSED and counts from UP to DOWN.
		* On upper side Y it's MINIMUM for example, in downer side it's MAXIMAL.
		*/
		
		//Play button
		if (Gdx.input.getX() > BUTTON_X && Gdx.input.getX() < BUTTON_X + playt.width && Gdx.graphics.getHeight() - Gdx.input.getY() > PLAY_Y && Gdx.graphics.getHeight() - Gdx.input.getY() < PLAY_Y + playt.height) {
			menuFont_s.draw(game.batch, playt_s, BUTTON_X, PLAY_Y + playt.height);
			if (Gdx.input.isTouched()) {
				this.dispose();
				game.setScreen(new GameScreen(game));
			}
		}
		
		//Settings button
		if (Gdx.input.getX() > BUTTON_X && Gdx.input.getX() < BUTTON_X + settingst.width && Gdx.graphics.getHeight() - Gdx.input.getY() > SETTINGS_Y && Gdx.graphics.getHeight() - Gdx.input.getY() < SETTINGS_Y + settingst.height) {
			menuFont_s.draw(game.batch, settingst_s, BUTTON_X, SETTINGS_Y + settingst.height);
			if (Gdx.input.isTouched()) {
				this.dispose();
				game.setScreen(new SettingsMenuScreen(game));
			}
		}
		
		//Exit button
		if (Gdx.input.getX() > BUTTON_X && Gdx.input.getX() < BUTTON_X + exitt.width && Gdx.graphics.getHeight() - Gdx.input.getY() > EXIT_Y && Gdx.graphics.getHeight() - Gdx.input.getY() < EXIT_Y + exitt.height) {
			menuFont_s.draw(game.batch, exitt_s, BUTTON_X, EXIT_Y + exitt.height);
			if (Gdx.input.isTouched()) {
				Gdx.app.exit();
			}
		}
		if (GameControl.XY_TRACKING) {
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
			
			game.batch.draw(x_line, Gdx.graphics.getWidth() * 0.8f - LOGO_WIDTH * LOGO_SCALE, (Gdx.graphics.getHeight() - LOGO_HEIGHT * LOGO_SCALE) / 2, 1, LOGO_HEIGHT * LOGO_SCALE);
			game.batch.draw(y_line, Gdx.graphics.getWidth() * 0.8f - LOGO_WIDTH * LOGO_SCALE, (Gdx.graphics.getHeight() - LOGO_HEIGHT * LOGO_SCALE) / 2, LOGO_WIDTH * LOGO_SCALE, 1);
			game.batch.draw(x_line, Gdx.graphics.getWidth() * 0.8f - LOGO_WIDTH * LOGO_SCALE + LOGO_WIDTH * LOGO_SCALE, (Gdx.graphics.getHeight() - LOGO_HEIGHT * LOGO_SCALE) / 2, 1, LOGO_HEIGHT * LOGO_SCALE);
			game.batch.draw(y_line, Gdx.graphics.getWidth() * 0.8f - LOGO_WIDTH * LOGO_SCALE, (Gdx.graphics.getHeight() - LOGO_HEIGHT * LOGO_SCALE) / 2 + LOGO_HEIGHT * LOGO_SCALE, LOGO_WIDTH * LOGO_SCALE, 1);
			
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
