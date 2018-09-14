package com.redsponge.underthesea.input;

import com.redsponge.underthesea.UnderTheSeaGame;

import java.awt.event.KeyEvent;

public enum Controls {

    RIGHT(KeyEvent.VK_RIGHT),
    LEFT(KeyEvent.VK_LEFT),
    UP(KeyEvent.VK_UP),
    DOWN(KeyEvent.VK_DOWN);

    private int[] keys;

    Controls(int... keys) {
        this.keys = keys;
    }

    public boolean isPressed() {
        for (int key : keys) {
            if(UnderTheSeaGame.instance.getInputManager().isKeyHeld(key)) {
                return true;
            }
        }
        return false;
    }

    public boolean isJustPressed() {
        for (int key : keys) {
            if(UnderTheSeaGame.instance.getInputManager().isKeyJustPressed(key)) {
                return true;
            }
        }
        return false;
    }

    public boolean isReleased() {
        for (int key : keys) {
            if(UnderTheSeaGame.instance.getInputManager().isKeyHeld(key)) {
                return false;
            }
        }
        return true;
    }

    public boolean isJustReleased() {
        for (int key : keys) {
            if(UnderTheSeaGame.instance.getInputManager().isKeyHeld(key)) {
                return false;
            }
        }
        for (int key : keys) {
            if(UnderTheSeaGame.instance.getInputManager().isKeyJustReleased(key)) {
                return true;
            }
        }
        return false;
    }

}
