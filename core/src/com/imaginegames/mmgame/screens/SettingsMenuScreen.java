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

public class SettingsMenuScreen implements Screen {
		
	private static float LANGUAGE_Y;
	private static float XY_Y;
	private static float GAMESPEED_Y;
	private static float BACK_Y;
	private static float FULLSCREEN_Y;
	private static final int DECOR_FIREBALL_PWIDTH = 128;
	private static final int DECOR_FIREBALL_PHEIGHT = 128;
	private static final float DECOR_FIREBALL_SCALE = 1f;
	private static final float DECOR_FIREBALL_WIDTH = DECOR_FIREBALL_PWIDTH * DECOR_FIREBALL_SCALE;
	private static final float DECOR_FIREBALL_HEIGHT = DECOR_FIREBALL_PHEIGHT * DECOR_FIREBALL_SCALE;
	private static final float DECOR_FIREBALL_ANIMATION_SPEED = 0.04f;
	
	private float stateTime;
		
	GameControl game;
	
	Animation<?> decoration_fireball;
	TextureRegion[][] decoration_fireball_sheet;
	
	BitmapFont font, font_s, font_c, font_u;
	private float FONT_SCALE = 0.15f;
	GlyphLayout gamespeed, gamespeed_s, back, back_s, xy, xy_s, language, language_s, on, off, russian, english, gamespeed_c, fullscreen, fullscreen_s, unavailable;

	public SettingsMenuScreen(GameControl game) {
		this.game = game;	
		decoration_fireball_sheet = TextureRegion.split(new Texture("fireball_sheet.png"), DECOR_FIREBALL_PWIDTH, DECOR_FIREBALL_PHEIGHT);
		decoration_fireball = new Animation<>(DECOR_FIREBALL_ANIMATION_SPEED / GameControl.GAMESPEED, decoration_fireball_sheet[0]);
		
		font = new BitmapFont(Gdx.files.internal("fonts/menu_s.fnt"));
		font_s = new BitmapFont(Gdx.files.internal("fonts/menu.fnt"));
		font_c = new BitmapFont(Gdx.files.internal("fonts/menu.fnt"));
		font_u = new BitmapFont(Gdx.files.internal("fonts/menu.fnt"));
		font.getData().setScale(FONT_SCALE);
		font_s.getData().setScale(FONT_SCALE);
		font_c.getData().setScale(FONT_SCALE * 0.7f);
		font_u.getData().setScale(FONT_SCALE * 0.7f);
		font_c.setColor(Color.ROYAL);
		font_u.setColor(Color.GRAY);
		
		language = new GlyphLayout(font, "????: ");
		language_s = new GlyphLayout(font_s, "????: ");
		xy = new GlyphLayout(font, "??????????? ???? X ? Y\n" + "??? ????????? ????????: ");
		xy_s = new GlyphLayout(font_s, "??????????? ???? X ? Y\n" + "??? ????????? ????????: ");
		back = new GlyphLayout(font, "?????");
		back_s = new GlyphLayout(font_s, "?????");
		fullscreen = new GlyphLayout(font, "????????????? ?????: ");
		fullscreen_s = new GlyphLayout(font_s, "????????????? ?????: ");
		on = new GlyphLayout(font_c, "????????");
		off = new GlyphLayout(font_c, "?????????");
		russian = new GlyphLayout(font_c, "???????");
		english = new GlyphLayout(font_c, "English");
		gamespeed = new GlyphLayout(font, "???????? ????: ");
		gamespeed_s = new GlyphLayout(font_s, "???????? ????: ");
		gamespeed_c = new GlyphLayout(font_c, "x" + GameControl.GAMESPEED);
		unavailable = new GlyphLayout(font_u, "?????");
		
		LANGUAGE_Y = Gdx.graphics.getHeight() * 0.95f - language.height;
		XY_Y = LANGUAGE_Y - Gdx.graphics.getHeight() * 0.06f - xy.height;
		GAMESPEED_Y = XY_Y - Gdx.graphics.getHeight() * 0.06f - gamespeed.height;
		FULLSCREEN_Y = GAMESPEED_Y - Gdx.graphics.getHeight() * 0.06f - fullscreen.height;
		BACK_Y = Gdx.graphics.getHeight() * 0.05f;
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
		font.draw(game.batch, gamespeed, MainMenuScreen.BUTTON_X, GAMESPEED_Y + gamespeed.height);
		font.draw(game.batch, back, MainMenuScreen.BUTTON_X, BACK_Y + back.height);
		font.draw(game.batch, language, MainMenuScreen.BUTTON_X, LANGUAGE_Y + language.height);
		font.draw(game.batch, xy, MainMenuScreen.BUTTON_X, XY_Y + xy.height);
		font.draw(game.batch, fullscreen, MainMenuScreen.BUTTON_X, FULLSCREEN_Y + fullscreen.height);
		font_c.draw(game.batch, gamespeed_c, MainMenuScreen.BUTTON_X * 2 + gamespeed.width, GAMESPEED_Y + gamespeed.height * 0.75f);
		
		if (GameControl.XY_TRACKING) {
			font_c.draw(game.batch, on, MainMenuScreen.BUTTON_X * 2 + xy.width, XY_Y + xy.height * 0.75f);
		}
		else {
			font_c.draw(game.batch, off, MainMenuScreen.BUTTON_X * 2 + xy.width, XY_Y + xy.height * 0.75f);
		}
		
		if (GameControl.ENGLISH_LANGUAGE == false) {
		font_c.draw(game.batch, russian, MainMenuScreen.BUTTON_X * 2 + language.width, LANGUAGE_Y + language.height * 0.75f);
		}
		else {
		font_c.draw(game.batch, english, MainMenuScreen.BUTTON_X * 2 + language.width, LANGUAGE_Y + language.height * 0.75f);	
		}
		
		if (GameControl.FULLSCREEN) {
			font_u.draw(game.batch, unavailable, MainMenuScreen.BUTTON_X * 2 + fullscreen.width, FULLSCREEN_Y + fullscreen.height * 0.75f);
		}
		else {
			font_u.draw(game.batch, unavailable, MainMenuScreen.BUTTON_X * 2 + fullscreen.width, FULLSCREEN_Y + fullscreen.height * 0.75f);
		}
		
		if (Gdx.input.getX() > MainMenuScreen.BUTTON_X && Gdx.input.getX() < MainMenuScreen.BUTTON_X + back.width && Gdx.graphics.getHeight() - Gdx.input.getY() > BACK_Y && Gdx.graphics.getHeight() - Gdx.input.getY() < BACK_Y + back.height) {
			font_s.draw(game.batch, back_s, MainMenuScreen.BUTTON_X, BACK_Y + back.height);
			if (Gdx.input.isTouched()) {
				this.dispose();
				game.setScreen(new MainMenuScreen(game));
			}
		}
		
		if (Gdx.input.getX() > MainMenuScreen.BUTTON_X && Gdx.input.getX() < MainMenuScreen.BUTTON_X + language.width && Gdx.graphics.getHeight() - Gdx.input.getY() > LANGUAGE_Y && Gdx.graphics.getHeight() - Gdx.input.getY() < LANGUAGE_Y + language.height) {
			font_s.draw(game.batch, language_s, MainMenuScreen.BUTTON_X, LANGUAGE_Y + language.height);
			if (Gdx.input.justTouched()) {
				if (GameControl.ENGLISH_LANGUAGE == false) {
					GameControl.ENGLISH_LANGUAGE = true;
				}
				else if (GameControl.ENGLISH_LANGUAGE == true) {
					GameControl.ENGLISH_LANGUAGE = false;
				}
			}
		}
		if (Gdx.input.getX() > MainMenuScreen.BUTTON_X && Gdx.input.getX() < MainMenuScreen.BUTTON_X + xy.width && Gdx.graphics.getHeight() - Gdx.input.getY() > XY_Y && Gdx.graphics.getHeight() - Gdx.input.getY() < XY_Y + xy.height) {
			font_s.draw(game.batch, xy_s, MainMenuScreen.BUTTON_X, XY_Y + xy.height);
			if (Gdx.input.justTouched()) {
				if (!GameControl.XY_TRACKING) {
					GameControl.XY_TRACKING = true;
				}
				else {
					GameControl.XY_TRACKING = false;
				}
			}
		}
		
		if (Gdx.input.getX() > MainMenuScreen.BUTTON_X && Gdx.input.getX() < MainMenuScreen.BUTTON_X + gamespeed.width && Gdx.graphics.getHeight() - Gdx.input.getY() > GAMESPEED_Y && Gdx.graphics.getHeight() - Gdx.input.getY() < GAMESPEED_Y + gamespeed.height) {
			font_s.draw(game.batch, gamespeed_s, MainMenuScreen.BUTTON_X, GAMESPEED_Y + gamespeed.height);
			if (Gdx.input.justTouched()) {
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
					decoration_fireball = new Animation<>(DECOR_FIREBALL_ANIMATION_SPEED / GameControl.GAMESPEED, decoration_fireball_sheet[0]);
				}
		}
		if (Gdx.input.getX() > MainMenuScreen.BUTTON_X && Gdx.input.getX() < MainMenuScreen.BUTTON_X + fullscreen.width && Gdx.graphics.getHeight() - Gdx.input.getY() > FULLSCREEN_Y && Gdx.graphics.getHeight() - Gdx.input.getY() < FULLSCREEN_Y + fullscreen.height) {
			font_s.draw(game.batch, fullscreen_s, MainMenuScreen.BUTTON_X,FULLSCREEN_Y + fullscreen.height);
			if (Gdx.input.justTouched()) {
				if (!GameControl.FULLSCREEN) {
					GameControl.FULLSCREEN = true;
					//Gdx.graphics.setFullscreenMode(null);
				}
				else {
					GameControl.FULLSCREEN = false;
				}
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
