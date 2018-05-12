package com.imaginegames.mmgame.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.imaginegames.mmgame.GameControl;

public class CosmonauticsDay {

	private GameControl game;
	
	private Texture space_light_back, space_light_front, flying_rocket, stars1, stars2, lion_stars;;
	
	private float STARS1_WIDTH = 1100 * 0.825f;
	private float STARS1_HEIGHT = 850 * 0.825f;
	private float STARS2_WIDTH = 1100 * 0.825f;
	private float STARS2_HEIGHT = 850 * 0.825f;
	private float LION_STARS_WIDTH = 1100 * 0.825f;
	private float LION_STARS_HEIGHT = 850 * 0.825f;
    private float FLYING_ROCKET_WIDTH = 489 * 0.825f;
    private float FLYING_ROCKET_HEIGHT = 391 * 0.825f;
    private float STARS_X = (Gdx.graphics.getWidth() - STARS1_WIDTH) / 2;
    private float flying_rocket_y;
    
    private float rocketStateTime;
    private float stars1_alpha;
	private float stars2_alpha;
	private float starsStateTime;
	
	public CosmonauticsDay(GameControl game) {
		this.game = game;
		space_light_back = game.assetManager.get("events/space_light_back.png", Texture.class);
		space_light_front = game.assetManager.get("events/space_light_front.png", Texture.class);
		stars1 = game.assetManager.get("events/stars1.png", Texture.class);
		stars2 = game.assetManager.get("events/stars2.png", Texture.class);
		lion_stars = game.assetManager.get("events/lion_stars.png", Texture.class);
		flying_rocket = game.assetManager.get("events/flying_rocket.png", Texture.class);
		
		flying_rocket_y = (Gdx.graphics.getHeight() - FLYING_ROCKET_HEIGHT) / 2;
		stars1_alpha = 0.9f;
		stars2_alpha = 0.1f;
	}
	
	public void render(float delta, SpriteBatch batch) {
		
		rocketStateTime += delta;
		starsStateTime += delta;

		if (starsStateTime <= 3) {
			stars1_alpha -= 0.25f * delta;
			stars2_alpha += 0.25f * delta;
		}
		else if (starsStateTime > 3 && starsStateTime <= 6) {
			stars1_alpha += 0.25f * delta;
			stars2_alpha -= 0.25f * delta;
		}
		else if (starsStateTime > 6) {
			stars1_alpha = 0.9f;
			stars2_alpha = 0.1f;
			starsStateTime = 0;
		}

        if (rocketStateTime <= 2) {
            flying_rocket_y += 25 * delta;
        }
        else if (rocketStateTime > 2 && rocketStateTime <= 4) {
            flying_rocket_y -= 25 * delta;
        }
        else if (rocketStateTime > 4) {
            rocketStateTime = 0;
        }
		
		//Background
		batch.draw(lion_stars, 150, 40, LION_STARS_WIDTH, LION_STARS_HEIGHT);
        batch.draw(space_light_back, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.setColor(1, 1, 1, stars1_alpha);
		batch.draw(stars1, STARS_X, 0, STARS1_WIDTH, STARS1_HEIGHT);
		batch.setColor(1, 1, 1, stars2_alpha);
		batch.draw(stars2, STARS_X, 0, STARS2_WIDTH, STARS2_HEIGHT);
		batch.setColor(1, 1, 1, 1);

        batch.draw(flying_rocket, Gdx.graphics.getWidth() * 0.9f - FLYING_ROCKET_WIDTH, flying_rocket_y, FLYING_ROCKET_WIDTH, FLYING_ROCKET_HEIGHT);
        batch.draw(space_light_front, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
	}
}
