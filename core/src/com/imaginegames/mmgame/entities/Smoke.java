package com.imaginegames.mmgame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.imaginegames.mmgame.GameControl;

public class Smoke {

    private GameControl game;
    public static final int PWIDTH = 128;
    public static final int PHEIGHT = 256;
    public float WIDTH;
    public float HEIGHT;
    private static final float ANIMATION_SPEED = 0.04f;
    public static final float SCALE = 1f;

    private static Animation<?> ANIMATION;
    private float stateTime;
    private float x, y;

    public boolean remove = false;

    public Smoke (GameControl game, float x, float y, float smoke_size) {
        this.game = game;
        this.x = x;
        this.y = y;
        WIDTH = PWIDTH * smoke_size * SCALE;
        HEIGHT = PHEIGHT * smoke_size * SCALE;
        stateTime = 0;
        TextureRegion[][] animation_sheet = TextureRegion.split(game.assetManager.get("smoke_sheet.png", Texture.class), PWIDTH, PHEIGHT);

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