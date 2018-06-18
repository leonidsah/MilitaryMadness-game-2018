package com.imaginegames.mmgame.screens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.imaginegames.mmgame.GameControl;
import com.imaginegames.mmgame.attachable.GameInputProcessor;
import com.imaginegames.mmgame.attachable.ScreenButton;
import com.imaginegames.mmgame.attachable.TouchButton;
import com.imaginegames.mmgame.entities.Bullet;
import com.imaginegames.mmgame.entities.Explosion;
import com.imaginegames.mmgame.entities.Fireball;
import com.imaginegames.mmgame.tools.CollisionRect;
import com.imaginegames.mmgame.attachable.DesktopControl;;
import net.dermetfan.gdx.physics.box2d.PositionController;

public class GameScreen implements Screen {

	//Player
	public static final int PLAYER_PWIDTH = 32;
	public static final int PLAYER_PHEIGHT = 145;
	public static final float PLAYER_SCALE = 1f;
	public static final float PLAYER_WIDTH = PLAYER_PWIDTH * PLAYER_SCALE;
	public static final float PLAYER_HEIGHT = PLAYER_PHEIGHT * PLAYER_SCALE;

	//Upper buttons
	private static final int PANEL_BUTTON_PWIDTH = 128;
	private static final int PANEL_BUTTON_PHEIGHT = 128;
	private static final float PANEL_SCALE = 1f;

	private static final float PANEL_BUTTON_WIDTH = PANEL_BUTTON_PWIDTH * PANEL_SCALE;
	private static final float PANEL_BUTTON_HEIGHT = PANEL_BUTTON_PHEIGHT * PANEL_SCALE;
	private static final float PANEL_BUTTON_Y = Gdx.graphics.getHeight() - PANEL_BUTTON_HEIGHT;

	private static final float GAMEPAUSE_X = Gdx.graphics.getWidth() - PANEL_BUTTON_WIDTH;
	private static final float STAT_BUTTON_X = Gdx.graphics.getWidth() - PANEL_BUTTON_WIDTH * 2;

	//Shoot button
	private static final int SHOOT_BUTTON_PWIDTH = 256;
	private static final int SHOOT_BUTTON_PHEIGHT= 256;
	private static final float SHOOT_BUTTON_SCALE = 0.75f;

	private static final float SHOOT_BUTTON_WIDTH = SHOOT_BUTTON_PWIDTH * SHOOT_BUTTON_SCALE;
	private static final float SHOOT_BUTTON_HEIGHT = SHOOT_BUTTON_PHEIGHT * SHOOT_BUTTON_SCALE;
	private static final float SHOOT_BUTTON_X = Gdx.graphics.getWidth() - SHOOT_BUTTON_WIDTH;
	private static final float SHOOT_BUTTON_Y = 0;

	//Move buttons
	private static final int MOVE_BUTTON_PWIDTH = 256;
	private static final int MOVE_BUTTON_PHEIGHT= 256;
	private static final float MOVE_BUTTON_SCALE = 0.625f;

	private static final float MOVE_BUTTON_WIDTH = MOVE_BUTTON_PWIDTH * MOVE_BUTTON_SCALE;
	private static final float MOVE_BUTTON_HEIGHT = MOVE_BUTTON_PHEIGHT * MOVE_BUTTON_SCALE;
	private static final float LEFT_X = 0;
	private static final float LEFT_Y = MOVE_BUTTON_HEIGHT * 0.75f;
	private static final float DOWN_X = MOVE_BUTTON_WIDTH;
	private static final float DOWN_Y = 0;
	private static final float RIGHT_X = MOVE_BUTTON_WIDTH * 2;
	private static final float RIGHT_Y = MOVE_BUTTON_HEIGHT * 0.75f;
	private static final float UP_X = MOVE_BUTTON_WIDTH;
	private static final float UP_Y = MOVE_BUTTON_HEIGHT * 1.5f;

	public static final float MIN_FIREBALL_SPAWN_TIME = 0.3f;
	public static final float MAX_FIREBALL_SPAWN_TIME = 1.5f;

	private GameControl game;
	private Random random;

	private Animation<?>[] rolls;
	private Texture blank, x_line, y_line, gamepause, shoot_button, move_button, stat_button;
	private ArrayList<Bullet> bullets;
	private ArrayList<Fireball> fireballs;
	private ArrayList<Explosion> explosions;
	
	public float x, y;
	public boolean rightMoveFlag, leftMoveFlag, upMoveFlag, downMoveFlag, doShootFlag;
	public float speed, affected_speed;
	public int PLAYER_DIRECTION;
	public float health = 1.0f; // 1 - full health; 0 - dead
	public float stat_bar_width;
	private CollisionRect player_rect;
	private float shootTimer;
	private float shoot_cooldown = 0.75f;
	private int game_score = 0;
	private float fireballsSpawnTimer;
	
	private float PLAYER_ANIMATION_SPEED = 0.2f;
	private int roll;
	float stateTime;
	
	private BitmapFont font, font_info;
	private GlyphLayout cam_scl_plus, cam_scl_minus, cam_x_plus, cam_x_minus, cam_y_plus, cam_y_minus, cam_rot_plus, cam_rot_minus;
	private String advantage = "";
	
	private DesktopControl desktop_control;
	private ScreenButton left, right, up, down, shoot;

	private Sound cancel_sound;

	/* For input processor
	public TouchButton left, right, up, down, shoot;
	private GameInputProcessor inputproc = new GameInputProcessor(this);
	*/

	//Camera part
	OrthographicCamera cam;

	//Box2D part
	World world;
	Box2DDebugRenderer debugRenderer;

	
	public GameScreen(GameControl game) {
		this.game = game;
		speed = 275;
		x = (Gdx.graphics.getWidth() - PLAYER_WIDTH) / 2;
		y = 0;
		PLAYER_DIRECTION = 1;
		player_rect = new CollisionRect(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
		
		blank = game.assetManager.get("blank.png", Texture.class);
		x_line = game.assetManager.get("x_line.png", Texture.class);
		y_line = game.assetManager.get("y_line.png", Texture.class);
		gamepause = game.assetManager.get("gamepause.png", Texture.class);

		move_button = game.assetManager.get("move_button.png", Texture.class);
		shoot_button = game.assetManager.get("shoot_button.png", Texture.class);
		stat_button = game.assetManager.get("stat_button.png", Texture.class);
		
		random = new Random();
		fireballsSpawnTimer = 
				(random.nextFloat() * (MAX_FIREBALL_SPAWN_TIME - MIN_FIREBALL_SPAWN_TIME) + MIN_FIREBALL_SPAWN_TIME)
						* GameControl.GAMESPEED; // Takes random from 0 to 0.6f and plus 0.2f; totals: random from 0.2f to 0.8f (+ game speed toggler)
		
		roll = 0;
		rolls = new Animation[3];
		TextureRegion[][] player_animated_sheet = TextureRegion.split(game.assetManager.get("player_normal_sheet.png", Texture.class), PLAYER_PWIDTH, PLAYER_PHEIGHT);
		rolls[0] = new Animation<>(PLAYER_ANIMATION_SPEED, player_animated_sheet[0]);
		
		bullets = new ArrayList<>();
		shootTimer = 1.5f;
		fireballs = new ArrayList<>();
		explosions = new ArrayList<>();

		font = game.assetManager.get("fonts/Play-Bold.ttf", BitmapFont.class);
        font_info = game.assetManager.get("fonts/Play-Regular_Info.ttf", BitmapFont.class);
        cam_scl_plus = new GlyphLayout(font, "( + )");
		cam_scl_minus = new GlyphLayout(font, "( - )");
		cam_x_plus = new GlyphLayout(font, "->");
		cam_x_minus = new GlyphLayout(font, "<-");
		cam_y_plus = new GlyphLayout(font, "/|");
		cam_y_minus = new GlyphLayout(font, "|/");
		cam_rot_plus = new GlyphLayout(font, "~)");
		cam_rot_minus = new GlyphLayout(font, "(~");


        left = new ScreenButton(LEFT_X, LEFT_Y, MOVE_BUTTON_WIDTH, MOVE_BUTTON_HEIGHT);
        right = new ScreenButton(RIGHT_X, RIGHT_Y, MOVE_BUTTON_WIDTH, MOVE_BUTTON_HEIGHT);
        up = new ScreenButton(UP_X, UP_Y, MOVE_BUTTON_WIDTH, MOVE_BUTTON_HEIGHT);
		down = new ScreenButton(DOWN_X, DOWN_Y, MOVE_BUTTON_WIDTH, MOVE_BUTTON_HEIGHT);
		shoot = new ScreenButton(SHOOT_BUTTON_X, SHOOT_BUTTON_Y, SHOOT_BUTTON_WIDTH, SHOOT_BUTTON_HEIGHT);

		left = new ScreenButton(LEFT_X, LEFT_Y, MOVE_BUTTON_WIDTH, MOVE_BUTTON_HEIGHT);
		right = new ScreenButton(RIGHT_X, RIGHT_Y, MOVE_BUTTON_WIDTH, MOVE_BUTTON_HEIGHT);
		up = new ScreenButton(UP_X, UP_Y, MOVE_BUTTON_WIDTH, MOVE_BUTTON_HEIGHT);
		down = new ScreenButton(DOWN_X, DOWN_Y, MOVE_BUTTON_WIDTH, MOVE_BUTTON_HEIGHT);
		shoot = new ScreenButton(SHOOT_BUTTON_X, SHOOT_BUTTON_Y, SHOOT_BUTTON_WIDTH, SHOOT_BUTTON_HEIGHT);

		cancel_sound = Gdx.audio.newSound(Gdx.files.internal("sounds/cancel_sound.mp3"));

		/* For input processor
		Gdx.input.setInputProcessor(inputproc);
		 */

		//Camera part
		cam = new OrthographicCamera(30, 30 * (Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
		cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
		cam.update();
		//game.batch.setProjectionMatrix(cam.combined);

		//Box 2D part
		Box2D.init();
		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.7f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		affected_speed = speed * GameControl.GAMESPEED;
		desktop_control = new DesktopControl(affected_speed, x, y, PLAYER_DIRECTION, GameControl.SHOW_STAT);

		GlyphLayout gamespeed = new GlyphLayout(font_info, "Скорость x" + GameControl.GAMESPEED);
		GlyphLayout fps_text = new GlyphLayout(font_info, "FPS: " + Gdx.graphics.getFramesPerSecond());
		GlyphLayout xtracker = new GlyphLayout(font_info, "X: " + (int) x);
		GlyphLayout ytracker = new GlyphLayout(font_info, "Y: " + (int) y);
		GlyphLayout game_score_text = new GlyphLayout(font_info, "Попаданий: " + game_score);
		GlyphLayout advantage_text = new GlyphLayout(font_info, " " + advantage);
		GlyphLayout health_text = new GlyphLayout(font_info, "Здоровье: " + (int) (health * 100) + "%");
		
		stat_bar_width = PLAYER_WIDTH * 2.0f;
		
		//Desktop movement code
		desktop_control.movement(delta);
		x = desktop_control.GetX();
		y = desktop_control.GetY();
		PLAYER_DIRECTION = desktop_control.GetPlayer_direction();
		desktop_control.other();
		GameControl.SHOW_STAT = desktop_control.GetShow_stat();

		if (GameControl.UNDEAD) {
		    health = 1.0f;
        }

		//Shoot and movement buttons
        shootTimer += delta;

		for (int n = 0; n < 2; n++) {
			if (left.isHoldButton(n)) leftMove();
			if (right.isHoldButton(n)) rightMove();
			if (down.isHoldButton(n)) downMove();
			if (up.isHoldButton(n)) upMove();
			if (shoot.isHoldButton(n)) doShoot();
		}

		/* For input processor
		if (leftMoveFlag) leftMove();

		if (rightMoveFlag) rightMove();
		if (downMoveFlag) downMove();
		if (upMoveFlag) upMove();
		if (doShootFlag) doShoot();
		System.out.println("leftMoveFlag: " + leftMoveFlag + " | " + "rightMoveFlag: " + rightMoveFlag + " | " + "downMoveFlag: " + downMoveFlag + " | " + "upMoveFlag: " + upMoveFlag );
		 */

		//Update player collision rect
		player_rect.move(x, y);

		//Fireballs spawn
		fireballsSpawnTimer -= delta;
		if (fireballsSpawnTimer <= 0) {
			fireballsSpawnTimer = random.nextFloat() * (MAX_FIREBALL_SPAWN_TIME - MIN_FIREBALL_SPAWN_TIME) + MIN_FIREBALL_SPAWN_TIME;
			fireballs.add(new Fireball(game, random.nextInt(Gdx.graphics.getHeight() - (int)Math.ceil(Fireball.HEIGHT))));
		}

		//Fireballs update
		ArrayList<Fireball> fireballs_to_remove = new ArrayList<>();
		for (Fireball fireball : fireballs) {
			fireball.update(delta);
			if (fireball.remove) {
				fireballs_to_remove.add(fireball);
			}
		}
		
		//Bullets update
		ArrayList<Bullet> bullets_to_remove = new ArrayList<>();
		for (Bullet bullet : bullets) {
			bullet.update(delta);
			if (bullet.remove) {
				bullets_to_remove.add(bullet);
			}
		}
			
		//Check for collisions (fireballs & bullets)
		for (Bullet rocket : bullets) {
			for (Fireball fireball : fireballs) {
				if (rocket.getCollisionRect().CollidesWith(fireball.getCollisionRect())) {
					fireballs_to_remove.add(fireball);
					bullets_to_remove.add(rocket);
					explosions.add(new Explosion(game, rocket.x, fireball.x, Bullet.WIDTH, Fireball.WIDTH, rocket.y, fireball.y, Bullet.HEIGHT, Fireball.HEIGHT, 0.5f));
					game_score += 1;
				}
			}
		}
		//Check for collisions (fireballs & player)
				for (Fireball fireball : fireballs) {
					if (fireball.getCollisionRect().CollidesWith(player_rect)) {
						fireballs_to_remove.add(fireball);
						if (health > 0) {
							health -= 0.1f;
						}
						else {
							game.setScreen(new GameOverScreen(game, game_score));
							this.dispose();
						}
						explosions.add(new Explosion(game, x, fireball.x, PLAYER_WIDTH, Fireball.WIDTH, y, fireball.y, PLAYER_HEIGHT, Fireball.HEIGHT, 0.25f));
				}
			}
				if (health < 0) {
					health = 0;
				}
		//Remove all objects in remove lists (out of bounds or destroyed objects)
				//Explosions update
				ArrayList<Explosion> explosions_to_remove = new ArrayList<>();
				for (Explosion explosion : explosions) {
					explosion.update(delta);
					if (explosion.remove) {
						explosions_to_remove.add(explosion);
					}
				}
		explosions.removeAll(explosions_to_remove);
		fireballs.removeAll(fireballs_to_remove);
		bullets.removeAll(bullets_to_remove);

        // Ground limitation
        if (y < 0) {
            y = 0;
        }
        if (y > Gdx.graphics.getHeight() - PLAYER_HEIGHT) {
            y = Gdx.graphics.getHeight() - PLAYER_HEIGHT;
        }
        if (x < 0) {
            x = 0;
        }
        if (x > Gdx.graphics.getWidth() - PLAYER_WIDTH) {
            x = Gdx.graphics.getWidth() - PLAYER_WIDTH;
        }

        //Score actions
        if (game_score >= 10 && game_score < 20) {
            speed = 300;
            advantage = "| Скорость + 25";
        }
        else if (game_score >= 20 && game_score < 30) {
            speed = 350;
            shoot_cooldown = 0.5f;
            advantage = "| Скорость + 75, Время перезарядки - 0.25 сек";
        }
        else if (game_score >= 30) {
            speed = 375;
            shoot_cooldown = 0.25f;
            advantage = "| Скорость + 100, Время перезарядки - 0.5 сек";
        }

		//Statistics output
		if (Gdx.input.getX() >= STAT_BUTTON_X && Gdx.input.getX() < STAT_BUTTON_X + PANEL_BUTTON_WIDTH && Gdx.graphics.getHeight() - Gdx.input.getY() >= PANEL_BUTTON_Y &&
				Gdx.graphics.getHeight() - Gdx.input.getY() < PANEL_BUTTON_Y + PANEL_BUTTON_HEIGHT && Gdx.input.justTouched()) {
			GameControl.SHOW_STAT = !GameControl.SHOW_STAT;
		}
		
		stateTime += delta;
		TextureRegion currentFrame = (TextureRegion)rolls[roll].getKeyFrame(stateTime, true);
		
		game.batch.begin();
		for (Bullet rocket : bullets) {
			rocket.render(game.batch, delta);
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

		game.batch.draw(gamepause, GAMEPAUSE_X, PANEL_BUTTON_Y, PANEL_BUTTON_WIDTH, PANEL_BUTTON_HEIGHT);

		//Draw health bar
		if (health > 0.8f) {
			game.batch.setColor(0.000f, 1.000f, 0.000f, 1f);
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
			//Camera buttons
		font.draw(game.batch, cam_scl_minus, Gdx.graphics.getWidth() - (PANEL_BUTTON_WIDTH * 2 + cam_scl_minus.width),
                Gdx.graphics.getHeight() - cam_scl_minus.height );
        font.draw(game.batch, cam_scl_plus, Gdx.graphics.getWidth() - (PANEL_BUTTON_WIDTH * 2 + cam_scl_minus.width + cam_scl_plus.width),
                Gdx.graphics.getHeight() - cam_scl_plus.height);
        if (Gdx.input.getX() > GAMEPAUSE_X && Gdx.input.getX() <= GAMEPAUSE_X + PANEL_BUTTON_WIDTH && Gdx.graphics.getHeight() - Gdx.input.getY() > PANEL_BUTTON_Y
                && Gdx.graphics.getHeight() - Gdx.input.getY() <= PANEL_BUTTON_Y + PANEL_BUTTON_HEIGHT && Gdx.input.justTouched()) {

        }
			//Pause
		if (Gdx.input.getX() > GAMEPAUSE_X && Gdx.input.getX() <= GAMEPAUSE_X + PANEL_BUTTON_WIDTH && Gdx.graphics.getHeight() - Gdx.input.getY() > PANEL_BUTTON_Y
				&& Gdx.graphics.getHeight() - Gdx.input.getY() <= PANEL_BUTTON_Y + PANEL_BUTTON_HEIGHT && Gdx.input.justTouched()) {
        		cancel_sound.play(1.0f);
				game.setScreen(new MainMenuScreen(game));
				this.dispose();
		}
			//Show statistics button
		game.batch.draw(stat_button, STAT_BUTTON_X, PANEL_BUTTON_Y, PANEL_BUTTON_WIDTH, PANEL_BUTTON_HEIGHT);
			//Shoot button
		game.batch.draw(shoot_button, SHOOT_BUTTON_X, SHOOT_BUTTON_Y, SHOOT_BUTTON_WIDTH, SHOOT_BUTTON_HEIGHT);
			//Draw android movement buttons
                //Left
		game.batch.draw(move_button, LEFT_X, LEFT_Y, MOVE_BUTTON_WIDTH / 2, MOVE_BUTTON_HEIGHT / 2, MOVE_BUTTON_WIDTH, MOVE_BUTTON_HEIGHT, 1, 1, 180, 0, 0, MOVE_BUTTON_PWIDTH, MOVE_BUTTON_PHEIGHT, false, false);
		        //Down
		game.batch.draw(move_button, DOWN_X, DOWN_Y, MOVE_BUTTON_WIDTH / 2, MOVE_BUTTON_HEIGHT / 2, MOVE_BUTTON_WIDTH, MOVE_BUTTON_HEIGHT, 1, 1, -90, 0, 0, MOVE_BUTTON_PWIDTH, MOVE_BUTTON_PHEIGHT, false, false);
                //Right
        game.batch.draw(move_button, RIGHT_X, RIGHT_Y, MOVE_BUTTON_WIDTH / 2, MOVE_BUTTON_HEIGHT / 2, MOVE_BUTTON_WIDTH, MOVE_BUTTON_HEIGHT, 1, 1, 0, 0, 0, MOVE_BUTTON_PWIDTH, MOVE_BUTTON_PHEIGHT, false, false);
                //Up
        game.batch.draw(move_button, UP_X, UP_Y, MOVE_BUTTON_WIDTH / 2, MOVE_BUTTON_HEIGHT / 2, MOVE_BUTTON_WIDTH, MOVE_BUTTON_HEIGHT, 1, 1, 90, 0, 0, MOVE_BUTTON_PWIDTH, MOVE_BUTTON_PHEIGHT, false, false);

		//Draw  X and Y tracking
		if (GameControl.SHOW_STAT) {
		    //Cursors tracking
            for (int n = 0; n < 10; n++) {
                if (Gdx.input.isTouched(n)) {
                    game.batch.draw(x_line, Gdx.input.getX(n), Gdx.graphics.getHeight() - Gdx.input.getY(n), 1, 50);
                    game.batch.draw(y_line, Gdx.input.getX(n), Gdx.graphics.getHeight() - Gdx.input.getY(n), 50, 1);

                    game.batch.draw(x_line, Gdx.input.getX(n), Gdx.graphics.getHeight() - Gdx.input.getY(n), 1, -50);
                    game.batch.draw(y_line, Gdx.input.getX(n), Gdx.graphics.getHeight() - Gdx.input.getY(n), -50, 1);
					GlyphLayout touch_index = new GlyphLayout(font_info, "= " + n);
                    font_info.draw(game.batch, touch_index,  Gdx.input.getX(n), Gdx.graphics.getHeight() - Gdx.input.getY(n) + 150 + touch_index.height);
                }
            }

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

			game.batch.draw(y_line, STAT_BUTTON_X, PANEL_BUTTON_Y, PANEL_BUTTON_WIDTH, 1);
			game.batch.draw(y_line, STAT_BUTTON_X, PANEL_BUTTON_Y + PANEL_BUTTON_HEIGHT, PANEL_BUTTON_WIDTH, 1);
			game.batch.draw(x_line, STAT_BUTTON_X, PANEL_BUTTON_Y, 1, PANEL_BUTTON_HEIGHT);
			game.batch.draw(x_line, STAT_BUTTON_X + PANEL_BUTTON_WIDTH, PANEL_BUTTON_Y, 1, PANEL_BUTTON_HEIGHT);

			game.batch.draw(y_line, GAMEPAUSE_X, PANEL_BUTTON_Y, PANEL_BUTTON_WIDTH, 1);
			game.batch.draw(y_line, GAMEPAUSE_X, PANEL_BUTTON_Y + PANEL_BUTTON_HEIGHT, PANEL_BUTTON_WIDTH, 1);
			game.batch.draw(x_line, GAMEPAUSE_X, PANEL_BUTTON_Y, 1, PANEL_BUTTON_HEIGHT);
			game.batch.draw(x_line, GAMEPAUSE_X + PANEL_BUTTON_WIDTH, PANEL_BUTTON_Y, 1, PANEL_BUTTON_HEIGHT);
		}
		//Draw font
			//Draw statistics
		if (GameControl.SHOW_STAT) {
			font_info.draw(game.batch, gamespeed, 0, Gdx.graphics.getHeight() * 0.96f + gamespeed.height);
			font_info.draw(game.batch, fps_text, 0, Gdx.graphics.getHeight() * 0.92f + fps_text.height);
			font_info.draw(game.batch, xtracker, 0, Gdx.graphics.getHeight() * 0.88f + xtracker.height);
			font_info.draw(game.batch, ytracker, 0, Gdx.graphics.getHeight() * 0.84f + ytracker.height);
			font_info.draw(game.batch, game_score_text, 0, Gdx.graphics.getHeight() * 0.80f + game_score_text.height);
			font_info.draw(game.batch, advantage_text, game_score_text.width, Gdx.graphics.getHeight() * 0.80f + game_score_text.height);
			font_info.draw(game.batch, health_text, 0, Gdx.graphics.getHeight() * 0.76f + health_text.height);
		}
		else {
			//Draw score
			font_info.draw(game.batch, game_score_text, 0, Gdx.graphics.getHeight() * 0.96f + game_score_text.height);
			font_info.draw(game.batch, advantage_text, game_score_text.width, Gdx.graphics.getHeight() * 0.96f + game_score_text.height);
		}
		game.batch.end();
		//Box 2D part
		//debugRenderer.render(world, camera.combined);
		world.step(1/60f, 6, 2);
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

	//Actions
	private void leftMove() {
		x -= affected_speed * Gdx.graphics.getDeltaTime();
		PLAYER_DIRECTION = -1;
	}

	private void rightMove() {
		x += affected_speed * Gdx.graphics.getDeltaTime();
		PLAYER_DIRECTION = 1;
	}

	private void upMove() {
		y += affected_speed * Gdx.graphics.getDeltaTime();
	}

	private void downMove() {
		y -= affected_speed * Gdx.graphics.getDeltaTime();
	}

	private void doShoot() {
		if (shootTimer >= shoot_cooldown) {
			shootTimer = 0;
			if (PLAYER_DIRECTION == 1) {
				bullets.add(new Bullet(game, x + PLAYER_WIDTH, y + PLAYER_HEIGHT * 0.6f - Bullet.HEIGHT / 2, PLAYER_DIRECTION));
			}
			else if (PLAYER_DIRECTION == -1) {
				bullets.add(new Bullet(game, x - Bullet.WIDTH, y + PLAYER_HEIGHT * 0.6f - Bullet.HEIGHT / 2, PLAYER_DIRECTION));
			}
		}
	}

	//Flags for actions
	public void setLeftMoveFlag(boolean MoveOn) {
		leftMoveFlag = MoveOn;
	}

	public void setRightMoveFlag(boolean MoveOn) {
		rightMoveFlag = MoveOn;
	}

	public void setUpMoveFlag(boolean MoveOn) {
		upMoveFlag = MoveOn;
	}

	public void setDownMoveFlag(boolean MoveOn) {
		downMoveFlag = MoveOn;
	}

	public void setDoShootFlag(boolean ShootOn) {
		doShootFlag = ShootOn;
	}

	
	
	

}
