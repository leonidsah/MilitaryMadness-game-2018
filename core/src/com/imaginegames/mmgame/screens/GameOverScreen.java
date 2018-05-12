package com.imaginegames.mmgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.imaginegames.mmgame.GameControl;
import com.imaginegames.mmgame.attachable.ScreenButton;

public class GameOverScreen implements Screen {
	
	private GameControl game;

	private int score, highscore;
	private BitmapFont score_font, font, font_s;
	private GlyphLayout gameover_text, score_text, highscore_text, back_text, back_text_s;
	private float button_x = MainMenuScreen.BUTTON_X;
	private float back_y = Gdx.graphics.getHeight() * 0.05f;
	private ScreenButton back_button;
	
	public GameOverScreen(GameControl game, int score) {
		this.game = game;
		this.score = score;
		
		//Get highscore
		Preferences prefs = Gdx.app.getPreferences("gamecontrol");
		highscore = prefs.getInteger("highscore", 0);
		if (score > highscore) {
			prefs.putInteger("highscore", score);
			prefs.flush();
		}	
		score_font = game.assetManager.get("fonts/Play-Bold.ttf", BitmapFont.class);
		font = game.assetManager.get("fonts/Play-Bold.ttf", BitmapFont.class);
		font_s = game.assetManager.get("fonts/Play-Bold_S.ttf", BitmapFont.class);
		
		gameover_text = new GlyphLayout(score_font, "Игра окончена");
		score_text = new GlyphLayout(score_font, "Набрано очков: \n" + score, Color.WHITE, 0, Align.center, false);
		highscore_text = new GlyphLayout(score_font, "Последний рекорд " + highscore + " очков");
		back_text = new GlyphLayout(font, "В главное меню");
		back_text_s = new GlyphLayout(font_s, "В главное меню");

		back_button = new ScreenButton(button_x, back_y, back_text.width, back_text.height);
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.24f, 0.6f, 0.24f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		score_font.draw(game.batch, gameover_text, (Gdx.graphics.getWidth() - gameover_text.width) / 2, Gdx.graphics.getHeight() * 0.8f + gameover_text.height);
		score_font.draw(game.batch, score_text, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 0.55f + score_text.height);
		score_font.draw(game.batch, highscore_text, (Gdx.graphics.getWidth() - highscore_text.width) / 2, Gdx.graphics.getHeight() * 0.425f + highscore_text.height);
		font.draw(game.batch, back_text, button_x, back_y + back_text.height);
		
		//Back button
		if (back_button.isOnButton(0)) {
			font_s.draw(game.batch, back_text_s, button_x, back_y + back_text.height);
		}
		if (back_button.isReleasedButton(0)) {
			game.setScreen(new MainMenuScreen(game));
			this.dispose();
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
