package com.imaginegames.mmgame.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.imaginegames.mmgame.GameControl;
import com.imaginegames.mmgame.entities.Bullet;

public class GameScreen implements Screen {
	
	public static final float SPEED = 240;
	
	public static final float PLAYER_ANIMATION_SPEED = 0.2f;
	public static final int PLAYER_PWIDTH = 232;
	public static final int PLAYER_PHEIGHT = 326;
	public static final float PLAYER_SCALE = 0.5f;
	public static final float PLAYER_WIDTH = PLAYER_PWIDTH * PLAYER_SCALE;
	public static final float PLAYER_HEIGHT = PLAYER_PHEIGHT * PLAYER_SCALE;
	public static final float SHOOT_COOLDOWN = 1.5f;

	Animation<?>[] rolls;
	
	float x;
	float y;
	private int roll;
	float stateTime;
	float shootTimer;
	
	private static int DIRECTION;
	
	GameControl game;
	
	ArrayList<Bullet> bullets;
	
	public GameScreen(GameControl game) {
		this.game = game;
		x = (MainMenuScreen.BUTTON_X - MainMenuScreen.LOGO_WIDTH * MainMenuScreen.LOGO_SCALE) / 2;
		y = 100;
		DIRECTION = 1;
		
		roll = 0;
		rolls = new Animation[3];
		TextureRegion[][] player_animated_sheet = TextureRegion.split(new Texture("player_sheet.png"), PLAYER_PWIDTH, PLAYER_PHEIGHT);
		rolls[0] = new Animation<>(PLAYER_ANIMATION_SPEED, player_animated_sheet[0]);
		
		
		bullets = new ArrayList<Bullet>();
		shootTimer = 1.5f;
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		//shoot control code
		shootTimer += delta;
		if (Gdx.input.justTouched() && shootTimer >= SHOOT_COOLDOWN) {
			shootTimer = 0;
			bullets.add(new Bullet(x + PLAYER_WIDTH, y + PLAYER_HEIGHT / 4 - Bullet.BULLET_HEIGHT / 2));
		}
		//Update bullets
		ArrayList<Bullet> bullets_to_remove = new ArrayList<Bullet>();
		for (Bullet bullet : bullets) {
			bullet.update(delta);
			if (bullet.remove) {
				bullets_to_remove.add(bullet);
			}
		}
		bullets.removeAll(bullets_to_remove);
		
		//Movement code
			if (Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.D)) {
				x += SPEED * delta;
				y += SPEED * delta;
				DIRECTION = 1;
			}
			else if (Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.A)) {
				x -= SPEED * delta;
				y += SPEED * delta;
				DIRECTION = -1;
			}
			else if (Gdx.input.isKeyPressed(Keys.S) && Gdx.input.isKeyPressed(Keys.D)) {
				x += SPEED * delta;
				y -= SPEED * delta;
				DIRECTION = 1;
			}

			else if (Gdx.input.isKeyPressed(Keys.S) && Gdx.input.isKeyPressed(Keys.A)) {
				x -= SPEED * delta;
				y -= SPEED * delta;
				DIRECTION = -1;
			}
		
		else if (Gdx.input.isKeyPressed(Keys.A)) {
			x -= SPEED * delta;
			DIRECTION = -1;
		}
		else if (Gdx.input.isKeyPressed(Keys.D)) {
			x += SPEED * delta;
			DIRECTION = 1;
		}
		else if (Gdx.input.isKeyPressed(Keys.W)) {
			y += SPEED * delta;
		}
		else if (Gdx.input.isKeyPressed(Keys.S)) {
			y -= SPEED * delta;
		}
		else if (Gdx.input.isKeyPressed(Keys.TAB)) {
			if (roll != 2) {
				roll++;
			}
			else if (roll == 2) {
				roll = 0;
			}
		}
		
		if (y < 0) {
			y = 0;
		}
		
		stateTime += delta;
		
		Gdx.gl.glClearColor(0.25f, 0.3f, 0.7f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		for (Bullet bullet : bullets) {
			bullet.render(game.batch, delta);
		}
		if (DIRECTION == 1) {
			game.batch.draw((TextureRegion)rolls[roll].getKeyFrame(stateTime, true), x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
		}
		else if (DIRECTION == -1) {
			game.batch.draw((TextureRegion)rolls[roll].getKeyFrame(stateTime, true), x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
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
