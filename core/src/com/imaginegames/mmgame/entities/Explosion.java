package com.imaginegames.mmgame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion {
	
	private static final int PWIDTH = 256;
	private static final int PHEIGHT = 256;
	public static float WIDTH;
	public static float HEIGHT;
	private static final float ANIMATION_SPEED = 0.04f;
	private static final float SCALE = 1f;
	
	private static Animation<?> ANIMATION;
	private float stateTime;
	float x, y;
	
	public boolean remove = false;
	
	public Explosion (float firstRectX, float secondRectX, float firstRectWIDTH, float secondRectWIDTH, float firstRectY, float secondRectY, float firstRectHEIGHT, float secondRectHEIGHT, float EXPLOSION_SIZE) {
		WIDTH = PWIDTH * EXPLOSION_SIZE * SCALE;
		HEIGHT = PHEIGHT * EXPLOSION_SIZE * SCALE;
		this.x = Math.min(firstRectX, secondRectX) + (firstRectWIDTH + secondRectWIDTH - WIDTH) / 2;
		this.y = Math.min(firstRectY, secondRectY) + (firstRectHEIGHT + secondRectHEIGHT - HEIGHT) / 2;
		stateTime = 0;
		TextureRegion[][] animation_sheet = TextureRegion.split(new Texture("explosion_sheet.png"), PWIDTH, PHEIGHT);
		
		if (ANIMATION == null) {
			ANIMATION = new Animation<>(ANIMATION_SPEED, animation_sheet[0]);
		}
	}
	
	public void update(float delta) {
		stateTime += delta;
		if (ANIMATION.isAnimationFinished(stateTime)) {
			remove = true;
		}
	}
	
	public void render(SpriteBatch batch) {
		batch.draw((TextureRegion) ANIMATION.getKeyFrame(stateTime), x, y, WIDTH, HEIGHT);
	}
	
}
