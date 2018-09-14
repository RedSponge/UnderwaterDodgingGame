package com.redsponge.underthesea.game;

import com.redsponge.redutils.gfx.Texture;
import com.redsponge.underthesea.UnderTheSeaGame;
import com.redsponge.underthesea.input.Controls;
import com.redsponge.underthesea.math.RVector;
import com.redsponge.underthesea.state.StateGame;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Player extends Entity {

    public Rectangle boundingBox;
    public boolean dead;
    private RVector vel;
    private Texture texture;

    public Player() {
        pos.set(100, 300);
        vel = new RVector();
        boundingBox = new Rectangle((int) pos.x - 10, (int) pos.y - 10, 64, 32);
        texture = Texture.load("player");
    }

    @Override
    public void tick() {
        //UPDATE POSITION
        vel.set(-1, 0);

        handleKeyInput();
        pos.add(vel);

        if(pos.y < 200) pos.y = 200;
        if(pos.y > 400) pos.y = 400;
        if(pos.x < 100) pos.x = 100;


        //DONE WITH POSITION
        updateBoundingBox();

    }

    private void handleKeyInput() {
        if(Controls.UP.isPressed()) vel.y -= 3;
        if(Controls.DOWN.isPressed()) vel.y += 3;
        if(Controls.RIGHT.isPressed()) vel.x += 3;
        if(Controls.LEFT.isPressed()) vel.x -= 3;
    }

    private void updateBoundingBox() {
        boundingBox.x = (int) pos.x;
        boundingBox.y = (int) pos.y;
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public int pointsWorth() {
        return -10;
    }

    @Override
    public void render(Graphics2D g) {
        texture.render(g, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }

    public void saveHighscore() throws IOException {
        File highscore = new File("highscore.redscore");
        if(!highscore.exists()) highscore.createNewFile();
        PrintWriter pw = new PrintWriter(highscore);
        pw.println(((StateGame)UnderTheSeaGame.instance.getStateManager().getState("game")).getHighScore());
        pw.close();
    }
}
