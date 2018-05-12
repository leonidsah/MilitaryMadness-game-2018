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
import com.imaginegames.mmgame.attachable.ScreenButton;

public class SettingsMenuScreen implements Screen {
		
	private static float language_y, xy_y, gamespeed_y, back_y, fullscreen_y, undead_y;
	private static final int DECOR_FIREBALL_PWIDTH = 128;
	private static final int DECOR_FIREBALL_PHEIGHT = 128;
	private static final float DECOR_FIREBALL_SCALE = 1f;
	private static final float DECOR_FIREBALL_WIDTH = DECOR_FIREBALL_PWIDTH * DECOR_FIREBALL_SCALE;
	private static final float DECOR_FIREBALL_HEIGHT = DECOR_FIREBALL_PHEIGHT * DECOR_FIREBALL_SCALE;
	private static final float DECOR_FIREBALL_ANIMATION_SPEED = 0.04f;
	
	private float stateTime;
		
	private GameControl game;
	private float button_x = MainMenuScreen.BUTTON_X;

	private Animation<?> decoration_fireball;
	private TextureRegion[][] decoration_fireball_sheet;
	
	private BitmapFont font, font_s, font_c;
	private GlyphLayout gamespeed, gamespeed_s, back, back_s, xy, xy_s, language, language_s, on, off, russian, english,
			gamespeed_c, fullscreen, fullscreen_s, unavailable, undead, undead_s;

	private ScreenButton back_button, language_button, xy_button, gamespeed_button, fullscreen_button, undead_button;

	public SettingsMenuScreen(GameControl game) {
		this.game = game;
		decoration_fireball_sheet = TextureRegion.split(game.assetManager.get("hexagonal_sheet.png", Texture.class), DECOR_FIREBALL_PWIDTH, DECOR_FIREBALL_PHEIGHT);
		decoration_fireball = new Animation<>(DECOR_FIREBALL_ANIMATION_SPEED / GameControl.GAMESPEED, decoration_fireball_sheet[0]);

		font = game.assetManager.get("fonts/Play-Bold.ttf", BitmapFont.class);
		font_s = game.assetManager.get("fonts/Play-Bold_S.ttf", BitmapFont.class);
		font_c = game.assetManager.get("fonts/Play-Regular.ttf", BitmapFont.class);

		language = new GlyphLayout(font, "Язык: ");
		language_s = new GlyphLayout(font_s, "Язык: ");
		xy = new GlyphLayout(font, "Отображение осей X и Y\n" + "для некоторых объектов: ");
		xy_s = new GlyphLayout(font_s, "Отображение осей X и Y\n" + "для некоторых объектов: ");
		back = new GlyphLayout(font, "Назад");
		back_s = new GlyphLayout(font_s, "Назад");
		fullscreen = new GlyphLayout(font, "Полноэкранный режим: ");
		fullscreen_s = new GlyphLayout(font_s, "Полноэкранный режим: ");
		on = new GlyphLayout(font_c, "Включено");
		off = new GlyphLayout(font_c, "Отключено");
		russian = new GlyphLayout(font_c, "Русcкий");
		english = new GlyphLayout(font_c, "English");
		gamespeed = new GlyphLayout(font, "Скорость игры: ");
		gamespeed_s = new GlyphLayout(font_s, "Скорость игры: ");
		gamespeed_c = new GlyphLayout(font_c, "x" + GameControl.GAMESPEED);
		unavailable = new GlyphLayout(font_c, "-");
		undead = new GlyphLayout(font, "Бессмертие: ");
		undead_s = new GlyphLayout(font_s, "Бессмертие: ");

		back_y = Gdx.graphics.getHeight() * 0.05f;
		language_y = Gdx.graphics.getHeight() * 0.95f - language.height;
		xy_y = language_y - Gdx.graphics.getHeight() * 0.06f - xy.height;
		gamespeed_y = xy_y - Gdx.graphics.getHeight() * 0.06f - gamespeed.height;
		fullscreen_y = gamespeed_y - Gdx.graphics.getHeight() * 0.06f - fullscreen.height;
		undead_y = fullscreen_y - Gdx.graphics.getHeight() * 0.06f - undead.height;

		back_button = new ScreenButton(button_x, back_y, back.width, back.height);
		language_button = new ScreenButton(button_x, language_y, language.width, language.height);
		xy_button = new ScreenButton(button_x, xy_y, xy.width, xy.height);
		gamespeed_button = new ScreenButton(button_x, gamespeed_y, gamespeed.width, gamespeed.height);
		fullscreen_button = new ScreenButton(button_x, fullscreen_y, fullscreen.width, fullscreen.height);
		undead_button = new ScreenButton(button_x, undead_y, undead.width, undead.height);
	}

	@Override
	public void show() {
		
		}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.678f, 0.847f, 0.902f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stateTime += delta;
		
		game.batch.begin();
		game.batch.draw((TextureRegion) decoration_fireball.getKeyFrame(stateTime, true), Gdx.graphics.getWidth() - DECOR_FIREBALL_WIDTH, 0, DECOR_FIREBALL_WIDTH, DECOR_FIREBALL_HEIGHT);
		font.draw(game.batch, gamespeed, MainMenuScreen.BUTTON_X, gamespeed_y + gamespeed.height);
		font.draw(game.batch, back, MainMenuScreen.BUTTON_X, back_y + back.height);
		font.draw(game.batch, language, MainMenuScreen.BUTTON_X, language_y + language.height);
		font.draw(game.batch, xy, MainMenuScreen.BUTTON_X, xy_y + xy.height);
		font.draw(game.batch, fullscreen, MainMenuScreen.BUTTON_X, fullscreen_y + fullscreen.height);
		font.draw(game.batch, undead, MainMenuScreen.BUTTON_X, undead_y + undead.height);
		font_c.draw(game.batch, gamespeed_c, MainMenuScreen.BUTTON_X * 2 + gamespeed.width, gamespeed_y + gamespeed.height * 0.75f);
		
		if (GameControl.SHOW_STAT) {
			font_c.draw(game.batch, on, MainMenuScreen.BUTTON_X * 2 + xy.width, xy_y + xy.height * 0.75f);
		}
		else {
			font_c.draw(game.batch, off, MainMenuScreen.BUTTON_X * 2 + xy.width, xy_y + xy.height * 0.75f);
		}

		if (GameControl.UNDEAD) {
			font_c.draw(game.batch, on, MainMenuScreen.BUTTON_X * 2 + undead.width, undead_y + undead.height * 0.75f);
		}
		else {
			font_c.draw(game.batch, off, MainMenuScreen.BUTTON_X * 2 + undead.width, undead_y + undead.height * 0.75f);
		}
		
		if (!GameControl.ENGLISH_LANGUAGE) {
		font_c.draw(game.batch, russian, MainMenuScreen.BUTTON_X * 2 + language.width, language_y + language.height * 0.75f);
		}
		else {
		font_c.draw(game.batch, english, MainMenuScreen.BUTTON_X * 2 + language.width, language_y + language.height * 0.75f);
		}

		font_c.draw(game.batch, unavailable, MainMenuScreen.BUTTON_X * 2 + fullscreen.width, fullscreen_y + fullscreen.height * 0.75f);

		//Back button
		if (back_button.isOnButton(0)) {
			font_s.draw(game.batch, back_s, MainMenuScreen.BUTTON_X, back_y + back.height);
		}
		if (back_button.isReleasedButton(0)) {
			game.setScreen(new MainMenuScreen(game));
			this.dispose();
		}

		//Language
		if (language_button.isOnButton(0)) {
			font_s.draw(game.batch, language_s, MainMenuScreen.BUTTON_X, language_y + language.height);
		}
		if (language_button.isReleasedButton(0)) {
			GameControl.ENGLISH_LANGUAGE = !GameControl.ENGLISH_LANGUAGE;
		}

		//Show stat
		if (xy_button.isOnButton(0)) {
			font_s.draw(game.batch, xy_s, MainMenuScreen.BUTTON_X, xy_y + xy.height);
		}
		if (xy_button.isReleasedButton(0)) {
			GameControl.SHOW_STAT = !GameControl.SHOW_STAT;
		}

		//Undead
		if (undead_button.isOnButton(0)) {
			font_s.draw(game.batch, undead_s, MainMenuScreen.BUTTON_X, undead_y + undead.height);
		}
		if (undead_button.isReleasedButton(0)) {
			GameControl.UNDEAD = !GameControl.UNDEAD;
		}
		//Game speed
		if (gamespeed_button.isOnButton(0)) {
			font_s.draw(game.batch, gamespeed_s, MainMenuScreen.BUTTON_X, gamespeed_y + gamespeed.height);
		}
		if (gamespeed_button.isReleasedButton(0)) {
			if (GameControl.GAMESPEED == 1.0f) {
				GameControl.GAMESPEED = 0.5f;
				gamespeed_c = new GlyphLayout(font_c, "x" + GameControl.GAMESPEED);
			}
			else if (GameControl.GAMESPEED == 0.5f) {
				GameControl.GAMESPEED = 0.1f;
				gamespeed_c = new GlyphLayout(font_c, "x" + GameControl.GAMESPEED);
			}
			else {
				GameControl.GAMESPEED = 1.0f;
				gamespeed_c = new GlyphLayout(font_c, "x" + GameControl.GAMESPEED);
			}
		}

		if (Gdx.input.getX() > MainMenuScreen.BUTTON_X && Gdx.input.getX() < MainMenuScreen.BUTTON_X + fullscreen.width && Gdx.graphics.getHeight() - Gdx.input.getY() > fullscreen_y && Gdx.graphics.getHeight() - Gdx.input.getY() < fullscreen_y + fullscreen.height) {
			font_s.draw(game.batch, fullscreen_s, MainMenuScreen.BUTTON_X,fullscreen_y + fullscreen.height);
			if (Gdx.input.justTouched()) {
				//Gdx.graphics.setFullscreenMode(null);
				GameControl.FULLSCREEN = !GameControl.FULLSCREEN;
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
