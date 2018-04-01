package com.imaginegames.mmgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.imaginegames.mmgame.GameControl;
import com.imaginegames.mmgame.tools.CollisionRect;

public class Bullet {
	
	public static float SPEED = 800;
	
	private static Animation<?> BULLET_ANIMATION;
	private static final int PWIDTH = 74;
	private static final int PHEIGHT = 31;
	private static final float SCALE = 1.2f;
	public static final float WIDTH = 74 * SCALE;
	public static final float HEIGHT = 31 * SCALE;
	private static final float ANIMATION_SPEED = 0.15f;
	public float x, y; // Don't do variable a static if it have a many objects with their own parameters for this variable
	int speed_direction;
	
	Texture x_line;
	Texture y_line;
	private CollisionRect bullet_rect;
	
	public boolean remove = false;
	
	private float stateTime;
	private int roll;
	
	public Bullet (float x, float y, int speed_direction) {
		SPEED = 850 * GameControl.GAMESPEED;
		this.x = x;
		this.y = y;
		x_line = new Texture("x_line.png");
		y_line = new Texture("y_line.png");
		this.speed_direction = speed_direction;
		roll = 1; // max = 2
		TextureRegion[][] rocket_animated_sheet = TextureRegion.split(new Texture("rocket_sheet.png"), PWIDTH, PHEIGHT);
		
		if (BULLET_ANIMATION == null) {
			BULLET_ANIMATION = new Animation<>(ANIMATION_SPEED, rocket_animated_sheet[roll]);
			}
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
			batch.draw((TextureRegion) BULLET_ANIMATION.getKeyFrame(stateTime, true), x, y, WIDTH, HEIGHT);
			if (GameControl.XY_TRACKING == true) {
			batch.draw(x_line, x + WIDTH, y, 1, HEIGHT);
			batch.draw(x_line, x, y, 1, HEIGHT);
			batch.draw(y_line, x, y + HEIGHT, WIDTH, 1);
			batch.draw(y_line, x, y, WIDTH, 1);
			}
		}
		else if (speed_direction == -1) {
			batch.draw((TextureRegion) BULLET_ANIMATION.getKeyFrame(stateTime, true), x + WIDTH, y, -WIDTH, HEIGHT);
			if (GameControl.XY_TRACKING) {
			batch.draw(x_line, x, y, 1, HEIGHT);
			batch.draw(x_line, x + WIDTH, y, 1, HEIGHT);
			batch.draw(y_line, x, y, WIDTH, 1);
			batch.draw(y_line, x, y + HEIGHT, WIDTH, 1);
			}
			
		}
		stateTime += delta;
	}
	
	public CollisionRect getCollisionRect() {
		return bullet_rect;
	}

}
