package com.imaginegames.mmgame.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.imaginegames.mmgame.GameControl;
import com.imaginegames.mmgame.entities.Smoke;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class VictoryDay {

    private GameControl game;
    private static final float MIN_SMOKE_SPAWN_TIME = 0.2f;
    private static final float MAX_SMOKE_SPAWN_TIME = 0.6f;

    private static final float MIN_SMOKE_SCALE = 0.25f;
    private static final float MAX_SMOKE_SCALE = 1f;

    private float smoke_spawn_timer, smoke_spawn_timer2;
	private ArrayList<Smoke> smokes, smokes_to_remove;
	private Random random;

	private Texture blank;

	public VictoryDay(GameControl game) {
	    this.game = game;
	    blank = game.assetManager.get("blank.png", Texture.class);
	    random = new Random();
		smokes = new ArrayList<>();
		smokes_to_remove = new ArrayList<>();
		smoke_spawn_timer = random.nextFloat() * (MAX_SMOKE_SPAWN_TIME - MIN_SMOKE_SPAWN_TIME) + MIN_SMOKE_SPAWN_TIME;

        smoke_spawn_timer2 = random.nextFloat() * (MAX_SMOKE_SPAWN_TIME - MIN_SMOKE_SPAWN_TIME) + MIN_SMOKE_SPAWN_TIME;
	}

	public void render(float delta, SpriteBatch batch) {
	    //Background
        batch.setColor(0.4f, 0.4f, 0.4f, 1f);
        batch.draw(blank, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(1f, 1f, 1f, 1f);

	    //Spawn smokes
        smoke_spawn_timer -= delta;
        smoke_spawn_timer2 -= delta;
        if (smoke_spawn_timer <= 0) {
            smokes.add(new Smoke(game, random.nextInt(Gdx.graphics.getWidth() - (int)Math.ceil(Smoke.PWIDTH * Smoke.SCALE
                    * (random.nextFloat() * (MAX_SMOKE_SCALE - MIN_SMOKE_SCALE) + MIN_SMOKE_SCALE))), 0, random.nextFloat() * (MAX_SMOKE_SCALE - MIN_SMOKE_SCALE) + MIN_SMOKE_SCALE));
            smoke_spawn_timer = random.nextFloat() * (MAX_SMOKE_SPAWN_TIME - MIN_SMOKE_SPAWN_TIME) + MIN_SMOKE_SPAWN_TIME * GameControl.GAMESPEED;
        }

        if (smoke_spawn_timer2 <= 0) {
            smokes.add(new Smoke(game, random.nextInt(Gdx.graphics.getWidth() - (int)Math.ceil(Smoke.PWIDTH * Smoke.SCALE
                    * (random.nextFloat() * (MAX_SMOKE_SCALE - MIN_SMOKE_SCALE) + MIN_SMOKE_SCALE))), 0, random.nextFloat() * (MAX_SMOKE_SCALE - MIN_SMOKE_SCALE) + MIN_SMOKE_SCALE));
            smoke_spawn_timer2 = random.nextFloat() * (MAX_SMOKE_SPAWN_TIME - MIN_SMOKE_SPAWN_TIME) + MIN_SMOKE_SPAWN_TIME * GameControl.GAMESPEED;
        }

        //Update smokes
        for (Smoke smoke : smokes) {
            smoke.update(delta);
            if (smoke.remove) {
                smokes_to_remove.add(smoke);
            }
        }
        smokes.removeAll(smokes_to_remove);

        //Render smokes
        for (Smoke smoke: smokes) {
            smoke.render(game.batch);
        }
	}

}
