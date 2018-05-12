package com.imaginegames.mmgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.imaginegames.mmgame.GameControl;
import com.imaginegames.mmgame.attachable.ScreenButton;
import com.imaginegames.mmgame.events.VictoryDay;

public class MainMenuScreen implements Screen{

    public static float BUTTON_X = Gdx.graphics.getWidth() * 0.03f;
	private float PLAY_Y;
	private float SETTINGS_Y;
	private float EXIT_Y;

	private static float LOGO_WIDTH = 32;
	private static float LOGO_HEIGHT = 145;
	private static float LOGO_SCALE = 3f;
	private static float LOGO_ANIMATION_SPEED;

	private GameControl game;

	private Animation<?>[] rolls;
	private float stateTime;

	private BitmapFont font, font_s, font_info, font_colored;
	private GlyphLayout playt, playt_s, settingst, settingst_s, exitt, exitt_s, version, fps_text, welcome_title;

	private TextureAtlas atlas;
	private Skin skin;

	private ScreenButton play_button, settings_button, exit_button;


	public MainMenuScreen(GameControl game) {
		this.game = game;

		LOGO_ANIMATION_SPEED = 0.2f / GameControl.GAMESPEED;
		rolls = new Animation[1];
		TextureRegion[][] player_animated_sheet = TextureRegion.split(game.assetManager.get("player_normal_sheet.png", Texture.class), GameScreen.PLAYER_PWIDTH, GameScreen.PLAYER_PHEIGHT);
		rolls[0] = new Animation<>(LOGO_ANIMATION_SPEED, player_animated_sheet[0]);

		font = game.assetManager.get("fonts/Play-Bold.ttf", BitmapFont.class);
		font_s = game.assetManager.get("fonts/Play-Bold_S.ttf", BitmapFont.class);
		font_info = game.assetManager.get("fonts/Play-Regular_Info.ttf", BitmapFont.class);
		font_colored = game.assetManager.get("fonts/Play-Regular_Colored.ttf", BitmapFont.class);

		playt = new GlyphLayout(font, "Играть");
		playt_s = new GlyphLayout(font_s, "Играть");
		exitt = new GlyphLayout(font, "Выход");
		exitt_s = new GlyphLayout(font_s, "Выход");
		settingst = new GlyphLayout(font, "Настройки");
		settingst_s = new GlyphLayout(font_s, "Настройки");
		version = new GlyphLayout(font_colored, GameControl.VERSION + ": ");
        welcome_title = new GlyphLayout(font_colored, GameControl.WELCOME_TITLE);

        PLAY_Y = Gdx.graphics.getHeight() * 0.90f;
        SETTINGS_Y = PLAY_Y - playt.height * 2f;
        EXIT_Y = SETTINGS_Y - settingst.height * 2f;

        atlas = new TextureAtlas();
        skin = new Skin(atlas);

        play_button = new ScreenButton(BUTTON_X, PLAY_Y, playt.width, playt.height);
        settings_button = new ScreenButton(BUTTON_X, SETTINGS_Y, settingst.width, settingst.height);
        exit_button = new ScreenButton(BUTTON_X, EXIT_Y, exitt.width, exitt.height);

	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.24f, 0.6f, 0.24f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		fps_text = new GlyphLayout(font_info, "FPS: " + Gdx.graphics.getFramesPerSecond());
		stateTime += delta;
        
		game.batch.begin();
		game.batch.draw((TextureRegion)rolls[0].getKeyFrame(stateTime, true), Gdx.graphics.getWidth() * 0.7f + LOGO_WIDTH * LOGO_SCALE, (Gdx.graphics.getHeight() - LOGO_HEIGHT * LOGO_SCALE) / 2, -LOGO_WIDTH * LOGO_SCALE, LOGO_HEIGHT * LOGO_SCALE);

		//Draw font
		font.draw(game.batch, playt, BUTTON_X, PLAY_Y + playt.height);
		font.draw(game.batch, settingst, BUTTON_X, SETTINGS_Y + settingst.height);
		font.draw(game.batch, exitt, BUTTON_X, EXIT_Y + exitt.height);
        font_info.draw(game.batch, fps_text, 0, 0 + version.height * 2.4f);
        font_info.draw(game.batch, version, 0, 0 + version.height * 1.2f);
		font_colored.draw(game.batch, welcome_title, version.width, 0 + version.height * 1.2f);

		/* NOTICE: X when do .getX() counts from left, BUT .getY() is REVERSED and counts from UP to DOWN.
		* On upper side Y it's MINIMUM for example, at bottom it's MAXIMAL.
		*/
		
		//Play button
		if (play_button.isOnButton(0)) {
			font_s.draw(game.batch, playt_s, BUTTON_X, PLAY_Y + playt.height);
		}
		if (play_button.isReleasedButton(0)) {
			game.setScreen(new GameScreen(game));
			this.dispose();
		}
		
		//Settings button
		if (settings_button.isOnButton(0)) {
			font_s.draw(game.batch, settingst_s, BUTTON_X, SETTINGS_Y + settingst.height);
		}
		if (settings_button.isReleasedButton(0)) {
			game.setScreen(new SettingsMenuScreen(game));
			this.dispose();
		}

		//Exit button
		if (exit_button.isOnButton(0)) {
			font_s.draw(game.batch, exitt_s, BUTTON_X, EXIT_Y + exitt.height);
		}
		if (exit_button.isReleasedButton(0)) {
			Gdx.app.exit();
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
