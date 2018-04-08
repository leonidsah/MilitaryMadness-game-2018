package com.imaginegames.mmgame.screens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.imaginegames.mmgame.GameControl;
import com.imaginegames.mmgame.entities.Bullet;
import com.imaginegames.mmgame.entities.Explosion;
import com.imaginegames.mmgame.entities.Fireball;
import com.imaginegames.mmgame.tools.CollisionRect;

public class GameScreen implements Screen {
	
	public static float SPEED;
	
	public static final int PLAYER_PWIDTH = 232;
	public static final int PLAYER_PHEIGHT = 326;
	public static final float PLAYER_SCALE = 0.5f;
	public static final float PLAYER_WIDTH = PLAYER_PWIDTH * PLAYER_SCALE;
	public static final float PLAYER_HEIGHT = PLAYER_PHEIGHT * PLAYER_SCALE;
	
	private static final int GAMEPAUSE_PWIDTH = 256;
	private static final int GAMEPAUSE_PHEIGHT = 256;
	private static final float GAMEPAUSE_SCALE = 0.375f;

	private static final int SHOOT_BUTTOM_PWIDTH = 256;
	private static final int SHOOT_BUTTOM_PHEIGHT= 256;
	private static final float SHOOT_BUTTON_SCALE = 0.875f;

	private static final int STAT_BUTTOM_PWIDTH = 256;
	private static final int STAT_BUTTOM_PHEIGHT= 256;
	private static final float STAT_BUTTON_SCALE = 0.4375f;

	private static final float GAMEPAUSE_WIDTH = GAMEPAUSE_PWIDTH * GAMEPAUSE_SCALE;
	private static final float GAMEPAUSE_HEIGHT = GAMEPAUSE_PHEIGHT * GAMEPAUSE_SCALE;
	private static final float GAMEPAUSE_X = Gdx.graphics.getWidth() - GAMEPAUSE_WIDTH;
	private static final float GAMEPAUSE_Y = Gdx.graphics.getHeight() - GAMEPAUSE_HEIGHT;

	private static final float SHOOT_BUTTON_WIDTH = SHOOT_BUTTOM_PWIDTH * SHOOT_BUTTON_SCALE;
	private static final float SHOOT_BUTTON_HEIGHT = SHOOT_BUTTOM_PHEIGHT * SHOOT_BUTTON_SCALE;
	private static final float SHOOT_BUTTON_X = Gdx.graphics.getWidth() - SHOOT_BUTTON_WIDTH;
	private static final float SHOOT_BUTTON_Y = 0;

	private static final float STAT_BUTTON_WIDTH = STAT_BUTTOM_PWIDTH * STAT_BUTTON_SCALE;
	private static final float STAT_BUTTON_HEIGHT = STAT_BUTTOM_PHEIGHT * STAT_BUTTON_SCALE;
	private static final float STAT_BUTTON_X = Gdx.graphics.getWidth() - GAMEPAUSE_WIDTH - STAT_BUTTON_WIDTH;
	private static final float STAT_BUTTON_Y = Gdx.graphics.getHeight() - STAT_BUTTON_HEIGHT;

	private static final int MOVE_BUTTOM_PWIDTH = 256;
	private static final int MOVE_BUTTOM_PHEIGHT= 256;
	private static final float MOVE_BUTTON_SCALE = 0.625f;

	private static final float MOVE_BUTTON_WIDTH = MOVE_BUTTOM_PWIDTH * MOVE_BUTTON_SCALE;
	private static final float MOVE_BUTTON_HEIGHT = MOVE_BUTTOM_PHEIGHT * MOVE_BUTTON_SCALE;
	private static final float LEFT_X = 0;
	private static final float LEFT_Y = MOVE_BUTTON_HEIGHT * 0.75f;
	private static final float DOWN_X = MOVE_BUTTON_WIDTH;
	private static final float DOWN_Y = 0;
	private static final float RIGHT_X = MOVE_BUTTON_WIDTH * 2;
	private static final float RIGHT_Y = MOVE_BUTTON_HEIGHT * 0.75f;
	private static final float UP_X = MOVE_BUTTON_WIDTH;
	private static final float UP_Y = MOVE_BUTTON_HEIGHT * 1.5f;


	public static final float SHOOT_COOLDOWN = 1.1f;
	public static final float MIN_FIREBALL_SPAWN_TIME = 0.4f;
	public static final float MAX_FIREBALL_SPAWN_TIME = 1.2f;

	Animation<?>[] rolls;
	Texture x_line, y_line, gamepause, shoot_button, move_button, stat_button;
	Texture blank;
	
	float x;
	float y;
	private int roll;
	float stateTime;
	float shootTimer;
	float fireballsSpawnTimer;
	private static float PLAYER_ANIMATION_SPEED = 0.2f;
	
	Random random;
	
	public static int PLAYER_DIRECTION;
	float health = 1; // 1 - full health; 0 - dead
	boolean dead = false;
	float stat_bar_width;
	CollisionRect player_rect;
	
	GameControl game;
	
	ArrayList<Bullet> bullets;
	ArrayList<Fireball> fireballs;
	ArrayList<Explosion> explosions;
	
	BitmapFont gameFont;
	private float FONT_SCALE = 0.09f;
	GlyphLayout gamespeed, fps, xtracker, ytracker;
	
	public GameScreen(GameControl game) {
		this.game = game;
		SPEED = 240 * GameControl.GAMESPEED;
		x = (Gdx.graphics.getWidth() - PLAYER_WIDTH) / 2;
		y = 0;
		PLAYER_DIRECTION = 1;
		player_rect = new CollisionRect(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
		
		blank = new Texture("blank.png");
		x_line = new Texture("x_line.png");
		y_line = new Texture("y_line.png");
		gamepause = new Texture("gamepause.png");

		move_button = new Texture("move_button.png");
		shoot_button = new Texture("shoot_button.png");
		stat_button = new Texture("stat_button.png");
		
		random = new Random();
		fireballsSpawnTimer = 
				(random.nextFloat() * (MAX_FIREBALL_SPAWN_TIME - MIN_FIREBALL_SPAWN_TIME) + MIN_FIREBALL_SPAWN_TIME)
						/ GameControl.GAMESPEED; // Takes random from 0 to 0.6f and plus 0.2f; totals: random from 0.2f to 0.8f (+ game speed toggler)
		
		roll = 0;
		rolls = new Animation[3];
		TextureRegion[][] player_animated_sheet = TextureRegion.split(new Texture("player_sheet.png"), PLAYER_PWIDTH, PLAYER_PHEIGHT);
		rolls[0] = new Animation<>(PLAYER_ANIMATION_SPEED, player_animated_sheet[0]);
		
		bullets = new ArrayList<Bullet>();
		shootTimer = 1.5f;
		fireballs = new ArrayList<Fireball>();
		explosions = new ArrayList<Explosion>();
		
		gameFont = new BitmapFont(Gdx.files.internal("fonts/menu_s.fnt"));
		gameFont.getData().setScale(FONT_SCALE);
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.412f, 0.604f, 0.949f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gamespeed = new GlyphLayout(gameFont, "Скорость x" + GameControl.GAMESPEED);
		fps = new GlyphLayout(gameFont, "FPS: " + Gdx.graphics.getFramesPerSecond());
		xtracker = new GlyphLayout(gameFont, "X: " + (int)x);
		ytracker = new GlyphLayout(gameFont, "Y: " + (int)y);
		
		stat_bar_width = PLAYER_WIDTH * 0.8f;
		
		SPEED = 240 * GameControl.GAMESPEED;
		
		//Movement code
		if (Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.D)) {
			x += SPEED * delta;
			y += SPEED * delta;
			PLAYER_DIRECTION = 1;
		}
		if (Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.A)) {
			x -= SPEED * delta;
			y += SPEED * delta;
			PLAYER_DIRECTION = -1;
		}
		else if (Gdx.input.isKeyPressed(Keys.S) && Gdx.input.isKeyPressed(Keys.D)) {
			x += SPEED * delta;
			y -= SPEED * delta;
			PLAYER_DIRECTION = 1;
		}

		else if (Gdx.input.isKeyPressed(Keys.S) && Gdx.input.isKeyPressed(Keys.A)) {
			x -= SPEED * delta;
			y -= SPEED * delta;
			PLAYER_DIRECTION = -1;
		}
	//Android movement code
		//Left
		if (Gdx.input.getX() >= LEFT_X && Gdx.input.getX() < LEFT_X + MOVE_BUTTON_WIDTH && Gdx.graphics.getHeight() - Gdx.input.getY() >= LEFT_Y &&
				Gdx.graphics.getHeight() - Gdx.input.getY() < LEFT_Y + MOVE_BUTTON_HEIGHT && Gdx.input.isTouched()) {
			x -= SPEED * delta;
			PLAYER_DIRECTION = -1;
		}
		//Down
		if (Gdx.input.getX() >= DOWN_X && Gdx.input.getX() < DOWN_X + MOVE_BUTTON_WIDTH && Gdx.graphics.getHeight() - Gdx.input.getY() >= DOWN_Y &&
				Gdx.graphics.getHeight() - Gdx.input.getY() < DOWN_Y + MOVE_BUTTON_HEIGHT && Gdx.input.isTouched()) {
			y -= SPEED * delta;
		}
		//Right
		if (Gdx.input.getX() >= RIGHT_X && Gdx.input.getX() < RIGHT_X + MOVE_BUTTON_WIDTH && Gdx.graphics.getHeight() - Gdx.input.getY() >= RIGHT_Y &&
				Gdx.graphics.getHeight() - Gdx.input.getY() < RIGHT_Y + MOVE_BUTTON_HEIGHT && Gdx.input.isTouched()) {
			x += SPEED * delta;
			PLAYER_DIRECTION = 1;
		}
		//Up
		if (Gdx.input.getX() >= UP_X && Gdx.input.getX() < UP_X + MOVE_BUTTON_WIDTH && Gdx.graphics.getHeight() - Gdx.input.getY() >= UP_Y &&
				Gdx.graphics.getHeight() - Gdx.input.getY() < UP_Y + MOVE_BUTTON_HEIGHT && Gdx.input.isTouched()) {
			y += SPEED * delta;
		}
	// Ground limitation
	if (y < 0) {
		y = 0;
	}
	//Update player collision rect
	player_rect.move(x, y);
		
		//Rockets spawn/control code
		shootTimer += delta;
		if (Gdx.input.getX() >= SHOOT_BUTTON_X && Gdx.input.getX() <= SHOOT_BUTTON_X + SHOOT_BUTTON_WIDTH && Gdx.graphics.getHeight() - Gdx.input.getY() >= SHOOT_BUTTON_Y &&
				Gdx.graphics.getHeight() - Gdx.input.getY() <= SHOOT_BUTTON_Y + SHOOT_BUTTON_HEIGHT) {
			if (Gdx.input.justTouched() && shootTimer >= SHOOT_COOLDOWN) {
				shootTimer = 0;
				if (PLAYER_DIRECTION == 1) {
					bullets.add(new Bullet(x + PLAYER_WIDTH, y + PLAYER_HEIGHT / 4 - Bullet.HEIGHT / 2, PLAYER_DIRECTION));
				} else if (PLAYER_DIRECTION == -1) {
					bullets.add(new Bullet(x - Bullet.WIDTH, y + PLAYER_HEIGHT / 4 - Bullet.HEIGHT / 2, PLAYER_DIRECTION));
				}
			}
		}
		//Fireballs spawn
		fireballsSpawnTimer -= delta;
		if (fireballsSpawnTimer <= 0) {
			fireballsSpawnTimer = random.nextFloat() * (MAX_FIREBALL_SPAWN_TIME - MIN_FIREBALL_SPAWN_TIME) + MIN_FIREBALL_SPAWN_TIME;
			fireballs.add(new Fireball(random.nextInt(Gdx.graphics.getHeight() - (int)Math.ceil(Fireball.HEIGHT))));
		}

		//Fireballs update
		ArrayList<Fireball> fireballs_to_remove = new ArrayList<Fireball>();
		for (Fireball fireball : fireballs) {
			fireball.update(delta);
			if (fireball.remove) {
				fireballs_to_remove.add(fireball);
			}
		}
		
		//Rockets update
		ArrayList<Bullet> bullets_to_remove = new ArrayList<Bullet>();
		for (Bullet bullet : bullets) {
			bullet.update(delta);
			if (bullet.remove) {
				bullets_to_remove.add(bullet);
			}
		}
			
		// Check for collisions (fireballs & bullets)
		for (Bullet bullet : bullets) {
			for (Fireball fireball : fireballs) {
				if (bullet.getCollisionRect().CollidesWith(fireball.getCollisionRect())) {
					fireballs_to_remove.add(fireball);
					bullets_to_remove.add(bullet);
					explosions.add(new Explosion(bullet.x, fireball.x, Bullet.WIDTH, Fireball.WIDTH, bullet.y, fireball.y, Bullet.HEIGHT, Fireball.HEIGHT, 0.5f));
				}
			}
		}
		//Check for collisions (fireballs & player)
				for (Fireball fireball : fireballs) {
					if (fireball.getCollisionRect().CollidesWith(player_rect)) {
						fireballs_to_remove.add(fireball);
						if (health > 0) {
							health -= 0.05f;
						}
						explosions.add(new Explosion(x, fireball.x, PLAYER_WIDTH, Fireball.WIDTH, y, fireball.y, PLAYER_HEIGHT, Fireball.HEIGHT, 0.25f));
				}
			}
				if (health < 0) {
					health = 0;
				}
		//Remove all objects in remove lists (out of bounds or destroyed objects)
				//Explosions update
				ArrayList<Explosion> explosions_to_remove = new ArrayList<Explosion>();
				for (Explosion explosion : explosions) {
					explosion.update(delta);
					if (explosion.remove) {
						explosions_to_remove.add(explosion);
					}
				}
		explosions.removeAll(explosions_to_remove);
		fireballs.removeAll(fireballs_to_remove);
		bullets.removeAll(bullets_to_remove);
		//Statistics output
		if (Gdx.input.getX() >= STAT_BUTTON_X && Gdx.input.getX() < STAT_BUTTON_X + STAT_BUTTON_WIDTH && Gdx.graphics.getHeight() - Gdx.input.getY() >= STAT_BUTTON_Y &&
				Gdx.graphics.getHeight() - Gdx.input.getY() < STAT_BUTTON_Y + STAT_BUTTON_HEIGHT && Gdx.input.justTouched()) {
			GameControl.XY_TRACKING = !GameControl.XY_TRACKING;
			GameControl.SHOW_STAT = !GameControl.SHOW_STAT;
		}
	
		if (health <= 0) {
			dead = true;
		}
		
		stateTime += delta;
		TextureRegion currentFrame = (TextureRegion)rolls[roll].getKeyFrame(stateTime, true);
		
		game.batch.begin();
		for (Bullet bullet : bullets) {
			bullet.render(game.batch, delta);
		}
		
		for (Fireball fireball : fireballs) {
			fireball.render(game.batch, delta);
		}
		
		for (Explosion explosion : explosions) {
			explosion.render(game.batch);
		}
		
		if (PLAYER_DIRECTION == 1) {
			game.batch.draw(currentFrame, x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
		}
		else if (PLAYER_DIRECTION == -1) {
			game.batch.draw(currentFrame, x + PLAYER_WIDTH, y, -PLAYER_WIDTH, PLAYER_HEIGHT);
		}

		game.batch.draw(gamepause, GAMEPAUSE_X, GAMEPAUSE_Y, GAMEPAUSE_WIDTH, GAMEPAUSE_HEIGHT);
		//Draw health bar
		if (health > 0.8f) {
			game.batch.setColor(0.000f, 1.000f, 0.000f, 1);
		}
		else if (health > 0.6f) {
			game.batch.setColor(0.596f, 1.000f, 0.596f, 1f);
		}
		else if (health > 0.4f) {
			game.batch.setColor(1.000f, 1.000f, 0.000f, 1f);
		}
		else if (health > 0.2f) {
			game.batch.setColor(1.000f, 0.647f, 0.000f, 1f);
		}
		else {
			game.batch.setColor(1.000f, 0.271f, 0.000f, 1f);
		}
		game.batch.draw(blank, x + (PLAYER_WIDTH - stat_bar_width * health) / 2, y + PLAYER_HEIGHT + 12, stat_bar_width * health, 6);
		game.batch.setColor(Color.WHITE);
		//Draw reload bar
		if (shootTimer > 0 && shootTimer<= 1) {
			if (shootTimer > 0.9f) {
				game.batch.setColor(1.000f, 1.000f, 0.000f, 1f);
			}
			else if (shootTimer > 0.8f) {
				game.batch.setColor(0.941f, 0.902f, 0.549f, 1f);
			}
			else if (shootTimer > 0.7f) {
				game.batch.setColor(0.933f, 0.910f, 0.667f, 1f);
			}
			else if (shootTimer > 0.6f) {
				game.batch.setColor(0.980f, 0.980f, 0.824f, 1f);
			}
			else if (shootTimer > 0.5f) {
				game.batch.setColor(1.000f, 1.000f, 0.878f, 1f);
			}
			else {
				game.batch.setColor(1.000f, 1.000f, 0.938f, 1f);
			}
			game.batch.draw(blank, x + (PLAYER_WIDTH - stat_bar_width * shootTimer) / 2, y + PLAYER_HEIGHT + 20, stat_bar_width * shootTimer, 4);
			game.batch.setColor(Color.WHITE);
		}
		//Draw interface buttons
			//Pause
		if (Gdx.input.getX() > GAMEPAUSE_X && Gdx.input.getX() <= GAMEPAUSE_X + GAMEPAUSE_WIDTH && Gdx.graphics.getHeight() - Gdx.input.getY() > GAMEPAUSE_Y
				&& Gdx.graphics.getHeight() - Gdx.input.getY() <= GAMEPAUSE_Y + GAMEPAUSE_HEIGHT && Gdx.input.justTouched()) {
				game.setScreen(new MainMenuScreen(game));
				this.dispose();
		}
			//Show statistics button
		game.batch.draw(stat_button, STAT_BUTTON_X, STAT_BUTTON_Y, STAT_BUTTON_WIDTH, STAT_BUTTON_HEIGHT);
			//Shoot button
		game.batch.draw(shoot_button, SHOOT_BUTTON_X, SHOOT_BUTTON_Y, SHOOT_BUTTON_WIDTH, SHOOT_BUTTON_HEIGHT);
			//Android movement buttons
                //Left
		game.batch.draw(move_button, LEFT_X, LEFT_Y, MOVE_BUTTON_WIDTH / 2, MOVE_BUTTON_HEIGHT / 2, MOVE_BUTTON_WIDTH, MOVE_BUTTON_HEIGHT, 1, 1, 180, 0, 0, MOVE_BUTTOM_PWIDTH, MOVE_BUTTOM_PHEIGHT, false, false);
		        //Down
		game.batch.draw(move_button, DOWN_X, DOWN_Y, MOVE_BUTTON_WIDTH / 2, MOVE_BUTTON_HEIGHT / 2, MOVE_BUTTON_WIDTH, MOVE_BUTTON_HEIGHT, 1, 1, -90, 0, 0, MOVE_BUTTOM_PWIDTH, MOVE_BUTTOM_PHEIGHT, false, false);
                //Right
        game.batch.draw(move_button, RIGHT_X, RIGHT_Y, MOVE_BUTTON_WIDTH / 2, MOVE_BUTTON_HEIGHT / 2, MOVE_BUTTON_WIDTH, MOVE_BUTTON_HEIGHT, 1, 1, 0, 0, 0, MOVE_BUTTOM_PWIDTH, MOVE_BUTTOM_PHEIGHT, false, false);
                //Up
        game.batch.draw(move_button, UP_X, UP_Y, MOVE_BUTTON_WIDTH / 2, MOVE_BUTTON_HEIGHT / 2, MOVE_BUTTON_WIDTH, MOVE_BUTTON_HEIGHT, 1, 1, 90, 0, 0, MOVE_BUTTOM_PWIDTH, MOVE_BUTTOM_PHEIGHT, false, false);

        //Draw  X and Y tracking
		if (GameControl.XY_TRACKING) {
			game.batch.draw(x_line, x, y, 1, PLAYER_HEIGHT);
			game.batch.draw(y_line, x, y, PLAYER_WIDTH, 1);
			game.batch.draw(x_line, x + PLAYER_WIDTH, y, 1, PLAYER_HEIGHT);
			game.batch.draw(y_line, x, y + PLAYER_HEIGHT, PLAYER_WIDTH, 1);
			for (Fireball fireball : fireballs) {
				game.batch.draw(x_line, fireball.x, fireball.y, 1, Fireball.HEIGHT);
				game.batch.draw(y_line, fireball.x, fireball.y, Fireball.WIDTH, 1);
				game.batch.draw(x_line, fireball.x + Fireball.WIDTH, fireball.y, 1, Fireball.HEIGHT);
				game.batch.draw(y_line, fireball.x, fireball.y + Fireball.HEIGHT, Fireball.WIDTH, 1);
			}
			game.batch.draw(y_line, UP_X, UP_Y, MOVE_BUTTON_WIDTH, 1);
			game.batch.draw(y_line, UP_X, UP_Y + MOVE_BUTTON_HEIGHT, MOVE_BUTTON_WIDTH, 1);
			game.batch.draw(y_line, LEFT_X, LEFT_Y, MOVE_BUTTON_WIDTH, 1);
			game.batch.draw(y_line, LEFT_X, LEFT_Y + MOVE_BUTTON_HEIGHT, MOVE_BUTTON_WIDTH, 1);
			game.batch.draw(y_line, RIGHT_X, RIGHT_Y, MOVE_BUTTON_WIDTH, 1);
			game.batch.draw(y_line, RIGHT_X, RIGHT_Y + MOVE_BUTTON_HEIGHT, MOVE_BUTTON_WIDTH, 1);
			game.batch.draw(y_line, DOWN_X, DOWN_Y, MOVE_BUTTON_WIDTH, 1);
			game.batch.draw(y_line, DOWN_X, DOWN_Y + MOVE_BUTTON_HEIGHT, MOVE_BUTTON_WIDTH, 1);

			game.batch.draw(x_line, RIGHT_X, RIGHT_Y, 1, MOVE_BUTTON_HEIGHT);
			game.batch.draw(x_line, RIGHT_X + MOVE_BUTTON_WIDTH, RIGHT_Y, 1, MOVE_BUTTON_HEIGHT);
			game.batch.draw(x_line, UP_X, UP_Y, 1, MOVE_BUTTON_HEIGHT);
			game.batch.draw(x_line, UP_X + MOVE_BUTTON_WIDTH, UP_Y, 1, MOVE_BUTTON_HEIGHT);
			game.batch.draw(x_line, DOWN_X, DOWN_Y, 1, MOVE_BUTTON_HEIGHT);
			game.batch.draw(x_line, DOWN_X + MOVE_BUTTON_WIDTH, DOWN_Y, 1, MOVE_BUTTON_HEIGHT);
			game.batch.draw(x_line, LEFT_X, LEFT_Y, 1, MOVE_BUTTON_HEIGHT);
			game.batch.draw(x_line, LEFT_X + MOVE_BUTTON_WIDTH, LEFT_Y, 1, MOVE_BUTTON_HEIGHT);

			game.batch.draw(y_line, SHOOT_BUTTON_X, SHOOT_BUTTON_Y, SHOOT_BUTTON_WIDTH, 1);
			game.batch.draw(y_line, SHOOT_BUTTON_X, SHOOT_BUTTON_Y + SHOOT_BUTTON_HEIGHT, SHOOT_BUTTON_WIDTH, 1);
			game.batch.draw(x_line, SHOOT_BUTTON_X, SHOOT_BUTTON_Y, 1, SHOOT_BUTTON_HEIGHT);
			game.batch.draw(x_line, SHOOT_BUTTON_X + SHOOT_BUTTON_WIDTH, SHOOT_BUTTON_Y, 1, SHOOT_BUTTON_HEIGHT);

			game.batch.draw(y_line, STAT_BUTTON_X, STAT_BUTTON_Y, STAT_BUTTON_WIDTH, 1);
			game.batch.draw(y_line, STAT_BUTTON_X, STAT_BUTTON_Y + STAT_BUTTON_HEIGHT, STAT_BUTTON_WIDTH, 1);
			game.batch.draw(x_line, STAT_BUTTON_X, STAT_BUTTON_Y, 1, STAT_BUTTON_HEIGHT);
			game.batch.draw(x_line, STAT_BUTTON_X + STAT_BUTTON_WIDTH, STAT_BUTTON_Y, 1, STAT_BUTTON_HEIGHT);
		}

		//Draw statistics
		if (GameControl.SHOW_STAT) {
			game.batch.setColor(Color.GOLD);
			gameFont.draw(game.batch, gamespeed, 0, Gdx.graphics.getHeight() * 0.97f + gamespeed.height);
			gameFont.draw(game.batch, fps, 0, Gdx.graphics.getHeight() * 0.94f + fps.height);
			gameFont.draw(game.batch, xtracker, 0, Gdx.graphics.getHeight() * 0.91f + xtracker.height);
			gameFont.draw(game.batch, ytracker, 0, Gdx.graphics.getHeight() * 0.88f + ytracker.height);
			game.batch.setColor(Color.WHITE);
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
