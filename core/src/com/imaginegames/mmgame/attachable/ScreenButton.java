package com.imaginegames.mmgame.attachable;

import com.badlogic.gdx.Gdx;
public class ScreenButton {

    private float x, y, width, height;
    private boolean isPressedBefore = false;


    public ScreenButton(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isOnButton(int pointer) {
        if (Gdx.input.getX(pointer) >= x && Gdx.input.getX(pointer) < x + width && Gdx.graphics.getHeight() - Gdx.input.getY(pointer) >= y &&
                Gdx.graphics.getHeight() - Gdx.input.getY(pointer) < y + height) {
            return true;
        }
        else return false;
    }

    public boolean isHoldButton(int pointer) {
        if (this.isOnButton(pointer) && Gdx.input.isTouched(pointer)) {
            isPressedBefore = true;
            return true;
        }
        else return false;
    }

    public boolean isReleasedButton(int pointer) {
        if (!isHoldButton(pointer) && isPressedBefore && isOnButton(pointer)) {
            isPressedBefore = false;
            return true;
        }
        else return false;
    }
}
