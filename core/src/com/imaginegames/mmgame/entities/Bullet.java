package com.imaginegames.mmgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bullet {
	
	public static final int SPEED = 470;
	
	private static Animation<?> BULLET_ANIMATION;
	private static final int BULLET_PWIDTH = 74;
	private static final int BULLET_PHEIGHT = 31;
	private static final float BULLET_SCALE = 1f;
	public static final float BULLET_WIDTH = BULLET_PWIDTH * BULLET_SCALE;
	public static final float BULLET_HEIGHT = BULLET_PHEIGHT * BULLET_SCALE;
	private static final float ROCKET_ANIMATION_SPEED = 0.15f;
	float x, y; // NOT STATIC FUCK
	
	public boolean remove = false;
	
	private float stateTime;
	private int roll;
	
	public Bullet (float x, float y) {
		this.x = x;
		this.y = y;
		roll = 0; // max = 2
		TextureRegion[][] rocket_animated_sheet = TextureRegion.split(new Texture("rocket_sheet.png"), BULLET_PWIDTH, BULLET_PHEIGHT);
		
		if (BULLET_ANIMATION == null) {
			BULLET_ANIMATION = new Animation<>(ROCKET_ANIMATION_SPEED, rocket_animated_sheet[roll]);
			}
		}
	public void update(float deltaTime) {
		x += SPEED * deltaTime;
		if (y > Gdx.graphics.getHeight() || y < 0 - BULLET_HEIGHT || x < 0 - BULLET_WIDTH || x > Gdx.graphics.getWidth()) {
			remove = true;
		}
	}
	public void render(SpriteBatch batch, float delta) {
		batch.draw((TextureRegion) BULLET_ANIMATION.getKeyFrame(stateTime, true), x, y, BULLET_WIDTH, BULLET_HEIGHT);
		stateTime += delta;
	}

}
