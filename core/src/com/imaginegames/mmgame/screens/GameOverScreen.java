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

public class GameOverScreen implements Screen {
	
	GameControl game;
	
	private float FONT_SCALE = 0.25f;
	private float FONT2_SCALE = 0.15f;
	int score, highscore;
	BitmapFont scoreFont, scoreFont2, scoreFont2_s;
	GlyphLayout gameover_text, score_text, highscore_text, back_text, back_text_s;
	private float back_x = Gdx.graphics.getWidth() * 0.03f;
	private float back_y = Gdx.graphics.getHeight() * 0.05f;
	
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
		scoreFont = new BitmapFont(Gdx.files.internal("fonts/menu_s.fnt"));
		scoreFont2 = new BitmapFont(Gdx.files.internal("fonts/menu_s.fnt"));
		scoreFont2_s = new BitmapFont(Gdx.files.internal("fonts/menu.fnt"));
		scoreFont.getData().setScale(FONT_SCALE);
		scoreFont2.getData().setScale(FONT2_SCALE);
		scoreFont2_s.getData().setScale(FONT2_SCALE);
		
		gameover_text = new GlyphLayout(scoreFont, "Игра окончена");
		score_text = new GlyphLayout(scoreFont, "Набрано очков: \n" + score, Color.WHITE, 0, Align.center, false);
		highscore_text = new GlyphLayout(scoreFont, "Последний рекорд " + highscore + " очков");
		back_text = new GlyphLayout(scoreFont2, "В главное меню");
		back_text_s = new GlyphLayout(scoreFont2_s, "В главное меню");
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.300f, 0.300f, 0.745f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		scoreFont.draw(game.batch, gameover_text, (Gdx.graphics.getWidth() - gameover_text.width) / 2, Gdx.graphics.getHeight() * 0.8f + gameover_text.height);
		scoreFont.draw(game.batch, score_text, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 0.55f + score_text.height);
		scoreFont.draw(game.batch, highscore_text, (Gdx.graphics.getWidth() - highscore_text.width) / 2, Gdx.graphics.getHeight() * 0.425f + highscore_text.height);
		scoreFont2.draw(game.batch, back_text, back_x, back_y + back_text.height);
		
		//Back button
		if (Gdx.input.getX() > back_x && Gdx.input.getX() <= back_x + back_text.width && Gdx.graphics.getHeight() - Gdx.input.getY() > back_y
				&& Gdx.graphics.getHeight() - Gdx.input.getY() <= back_y + back_text.height) {
			scoreFont2_s.draw(game.batch, back_text_s, back_x, back_y + back_text.height);
			if (Gdx.input.justTouched()) {
				game.setScreen(new MainMenuScreen(game));
				this.dispose();
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
