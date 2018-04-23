package com.imaginegames.mmgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.imaginegames.mmgame.GameControl;
import com.imaginegames.mmgame.tools.CollisionRect;

public class Fireball {
	
	public static float SPEED;
	
	private static Animation<?> ANIMATION;
	private static final int PWIDTH = 128;
	private static final int PHEIGHT = 128;
	private static final float SCALE = 0.625f;
	private final float FIREBALL_X = Gdx.graphics.getWidth();
	public static final float WIDTH = PWIDTH * SCALE;
	public static final float HEIGHT = PHEIGHT * SCALE;
	private static final float ANIMATION_SPEED = 0.03f;
	public float x, y; // Don't do variable a static if it have a many objects with their own parameters for this variable
	
	public boolean remove = false;
	
	private float stateTime;
	
	private CollisionRect fireball_rect;
	
	public Fireball (float y) {
		SPEED = 560 * GameControl.GAMESPEED;
		this.x = FIREBALL_X;
		this.y = y;
		fireball_rect = new CollisionRect(x, y, WIDTH, HEIGHT);
		TextureRegion[][] fireball_animated_sheet = TextureRegion.split(new Texture("fireball_sheet.png"), PWIDTH, PHEIGHT);
		if (ANIMATION == null) {
			ANIMATION = new Animation<>(ANIMATION_SPEED, fireball_animated_sheet[0]);
			}
		}
	public void update(float deltaTime) {
		x -= SPEED * deltaTime;
		if (x <= 0 - WIDTH) {
			remove = true;
		}
		fireball_rect.move(x, y);
	}
	public void render(SpriteBatch batch, float delta) {
		batch.draw((TextureRegion) ANIMATION.getKeyFrame(stateTime, true), x, y, WIDTH, HEIGHT);
		stateTime += delta;
	}
	
	public CollisionRect getCollisionRect() {
		return fireball_rect;
	}
}

