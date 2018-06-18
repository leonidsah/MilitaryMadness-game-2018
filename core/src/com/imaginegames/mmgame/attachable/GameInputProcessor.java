package com.imaginegames.mmgame.attachable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.imaginegames.mmgame.screens.GameScreen;

/*
        if (Gdx.graphics.getHeight() - screenY > 500) {
            System.out.println("Real Y > 500 | " + (Gdx.graphics.getHeight() - screenY) + "         Current screenY:" + screenY);
        }
        else System.out.println("Real Y < 500 |  " + (Gdx.graphics.getHeight() - screenY) + "         Current screenY:" + screenY);
 */

public class GameInputProcessor extends InputAdapter {

    GameScreen scr;

    public GameInputProcessor(GameScreen screen) {
        this.scr = screen;
    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        /*
        //Left
        if (scr.left.isOnButton(screenX, screenY)) {
            scr.setLeftMoveFlag(true);
        }

        //Right
        if (scr.right.isOnButton(screenX, screenY)) {
            scr.setRightMoveFlag(true);
        }

        //Up
        if (scr.up.isOnButton(screenX, screenY)) {
            scr.setUpMoveFlag(true);
        }

        //Down

        if (scr.down.isOnButton(screenX, screenY)) {
            scr.setDownMoveFlag(true);
        }

        //Shoot
        if (scr.shoot.isOnButton(screenX, screenY)) {
            scr.setDoShootFlag(true);
        }
        return true;
        */
        return false;
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        /*
        //Left
        if (scr.left.isOnButton(screenX, screenY)) {
            scr.setLeftMoveFlag(false);
        }

        //Right
        if (scr.right.isOnButton(screenX, screenY)) {
            scr.setRightMoveFlag(false);
        }

        //Up
        if (scr.up.isOnButton(screenX, screenY)) {
            scr.setUpMoveFlag(false);
        }

        //Down

        if (scr.down.isOnButton(screenX, screenY)) {
            scr.setDownMoveFlag(false);
        }

        //Shoot
        if (scr.shoot.isOnButton(screenX, screenY)) {
            scr.setDoShootFlag(false);
        }
        return true;
        */
        return false;
    }

    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer) {
        return true;

    }


}
