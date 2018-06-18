package com.imaginegames.mmgame.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.imaginegames.mmgame.GameControl;
import com.imaginegames.mmgame.tools.CollisionRect;

public class Bullet {
	
	public static float SPEED = 1500;
	
	private static final float SCALE = 1f;
	public static final float WIDTH = 16 * SCALE;
	public static final float HEIGHT = 8 * SCALE;
	public float x, y; // Don't do variable a static if it have a many objects with their own parameters for this variable
	int speed_direction;

	private GameControl game;
	private Texture x_line, y_line, rocket;
	private CollisionRect bullet_rect;
	
	public boolean remove = false;;
	
	public Bullet (GameControl game, float x, float y, int speed_direction) {
		this.game = game;
		this.x = x;
		this.y = y;
		SPEED = 1500 * GameControl.GAMESPEED;
		
		x_line = game.assetManager.get("x_line.png", Texture.class);
        y_line = game.assetManager.get("y_line.png", Texture.class);
		rocket = game.assetManager.get("bullet.png", Texture.class);
		
		this.speed_direction = speed_direction;
		bullet_rect = new CollisionRect(x, y, WIDTH, HEIGHT);
		
		}
	public void update(float deltaTime) {
		x += speed_direction * SPEED * deltaTime;
		if (y > Gdx.graphics.getHeight() || y < 0 - HEIGHT || x < 0 - WIDTH || x > Gdx.graphics.getWidth()) {
			remove = true;
		}
		bullet_rect.move(x, y);
	}
	public void render(SpriteBatch batch, float delta) {
		if (speed_direction == 1) {
			batch.draw(rocket, x, y, WIDTH, HEIGHT);
			if (GameControl.SHOW_STAT) {
			batch.draw(x_line, x + WIDTH, y, 1, HEIGHT);
			batch.draw(x_line, x, y, 1, HEIGHT);
			batch.draw(y_line, x, y + HEIGHT, WIDTH, 1);
			batch.draw(y_line, x, y, WIDTH, 1);
			}
		}
		else if (speed_direction == -1) {
			batch.draw(rocket, x + WIDTH, y, -WIDTH, HEIGHT);
			if (GameControl.SHOW_STAT) {
			batch.draw(x_line, x, y, 1, HEIGHT);
			batch.draw(x_line, x + WIDTH, y, 1, HEIGHT);
			batch.draw(y_line, x, y, WIDTH, 1);
			batch.draw(y_line, x, y + HEIGHT, WIDTH, 1);
			}
			
		}
	}
	
	public CollisionRect getCollisionRect() {
		return bullet_rect;
	}

}
