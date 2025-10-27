package com.squares;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class PlayerInput implements InputProcessor {

    private final Player player;

    public PlayerInput(Player player) {
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.LEFT:  player.movingLeft = true; break;
            case Keys.RIGHT: player.movingRight = true; break;
            case Keys.UP:    player.movingUp = true; break;
            case Keys.DOWN:  player.movingDown = true; break;
            case Keys.A :  player.movingLeft = true; break;
            case Keys.D : player.movingRight = true; break;
            case Keys.W :    player.movingUp = true; break;
            case Keys.S :  player.movingDown = true; break;
            case Keys.SPACE: player.dash(); break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.LEFT:  player.movingLeft = false; break;
            case Keys.RIGHT: player.movingRight = false; break;
            case Keys.UP:    player.movingUp = false; break;
            case Keys.DOWN:  player.movingDown = false; break;
            case Keys.A :  player.movingLeft = false; break;
            case Keys.D : player.movingRight = false; break;
            case Keys.W :    player.movingUp = false; break;
            case Keys.S :  player.movingDown = false; break;
        }
        return true;
    }

    // Unused input methods
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean touchDown(int x, int y, int pointer, int button) { return false; }
    @Override public boolean touchUp(int x, int y, int pointer, int button) { return false; }
    @Override public boolean touchDragged(int x, int y, int pointer) { return false; }
    @Override public boolean mouseMoved(int x, int y) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }

    @Override
    public boolean touchCancelled(int pointer, int button, int x, int y) {
        return false; // not used
}
}
