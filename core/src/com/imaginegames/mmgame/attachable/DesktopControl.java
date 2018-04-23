package com.imaginegames.mmgame.attachable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class DesktopControl {
	
	float speed;
	private float x;
	private float y;
	private int player_direction;
	private boolean show_stat;
	
	public DesktopControl (float speed, float x, float y, int player_direction, boolean show_stat) {
		this.speed = speed;
		this.x = x;
		this.y = y;
		this.player_direction = player_direction;
		this.show_stat = show_stat;
	}
	
	public void movement(float delta) {
		if (Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.D)) {
			x += speed * delta;
			y += speed * delta;
			player_direction = 1;
		}
		else if (Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.A)) {
			x -= speed * delta;
			y += speed * delta;
			player_direction = -1;
		}
		else if (Gdx.input.isKeyPressed(Keys.S) && Gdx.input.isKeyPressed(Keys.D)) {
			x += speed * delta;
			y -= speed * delta;
			player_direction = 1;
		}

		else if (Gdx.input.isKeyPressed(Keys.S) && Gdx.input.isKeyPressed(Keys.A)) {
			x -= speed * delta;
			y -= speed * delta;
			player_direction = -1;
		}
	
		else if (Gdx.input.isKeyPressed(Keys.A)) {
			x -= speed * delta;
			player_direction = -1;
		}
		else if (Gdx.input.isKeyPressed(Keys.D)) {
			x += speed * delta;
			player_direction = 1;
		}
		else if (Gdx.input.isKeyPressed(Keys.W)) {
			y += speed * delta;
		}
		else if (Gdx.input.isKeyPressed(Keys.S)) {
			y -= speed * delta;
		}
	}
	
	public void other() {
		if (Gdx.input.isKeyJustPressed(Keys.TAB)) {
			show_stat = !show_stat;
		}
	}
	
	public float GetX() {
		return x;
	}
	
	public float GetY() {
		return y;
	}
	
	public int GetPlayer_direction() {
		return player_direction;
	}
	
	public boolean GetShow_stat() {
		return show_stat;
	}
}
