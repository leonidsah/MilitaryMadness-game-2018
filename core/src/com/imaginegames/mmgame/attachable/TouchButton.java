package com.imaginegames.mmgame.attachable;

import com.badlogic.gdx.Gdx;
public class TouchButton {

    private float x, y, width, height;

    //This class needed only for GameInputProcessor and other InputProcessors.

    public TouchButton(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isOnButton(int screenX, int screenY) {
        return screenX >= x && screenX < x + width && Gdx.graphics.getHeight() - screenY >= y &&
                Gdx.graphics.getHeight() - screenY < y + height;
    }

}
