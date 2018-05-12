package com.imaginegames.mmgame.attachable;

import com.badlogic.gdx.Gdx;
public class TouchButton {

    private float x, y, width, height;


    public TouchButton(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isTouchedButton(int screenX, int screenY) {
        if (screenX >= x && screenX < x + width && Gdx.graphics.getHeight() - screenY >= y &&
                Gdx.graphics.getHeight() - screenY < y + height) {
            return true;
        }
        else return false;
    }
}
