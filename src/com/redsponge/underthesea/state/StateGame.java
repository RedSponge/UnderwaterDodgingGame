package com.redsponge.underthesea.state;

import com.redsponge.redutils.gfx.Texture;
import com.redsponge.redutils.util.array.DelayModifiableArray;
import com.redsponge.underthesea.UnderTheSeaGame;
import com.redsponge.underthesea.game.Entity;
import com.redsponge.underthesea.game.Obstacle;
import com.redsponge.underthesea.game.Player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class StateGame extends State {

    private DelayModifiableArray<Entity> entities;
    private int counter;
    private Texture texture;
    private Player p;
    private int score;
    private int highScore;
    private int spawnNewEvery;
    private int difficulty;
    private int talliedScore;

    public StateGame() {
        texture = Texture.load("background");
        File highscore = new File("highscore.redscore");
        if(highscore.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(highscore));
                this.highScore = Integer.parseInt(reader.readLine());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        init();

    }

    private void init() {
        spawnNewEvery = 120;
        entities = new DelayModifiableArray<>();
        p = new Player();
        entities.add(p);

        counter = 0;
        difficulty = 1;
        score = 0;
    }

    @Override
    public void tick() {
        if(p.isDead()) {
            if(UnderTheSeaGame.instance.getInputManager().isKeyJustPressed(KeyEvent.VK_SPACE)) init();
            return;
        }
        if(counter % (120 / difficulty) == 0) entities.add(new Obstacle(p));
        for (Entity entity : entities) {
            entity.tick();
            if(!(entity instanceof Player) && entity.isDead()) {
                score+=entity.pointsWorth();
                if(score > highScore) highScore = score;
                talliedScore += entity.pointsWorth();
                entities.remove(entity);

                if(talliedScore >= difficulty * 100)  {
                    difficulty++;
                    talliedScore = 0;
                }
            }
        }
        counter++;
    }

    @Override
    public void render(Graphics2D g) {

        texture.render(g, 0, 0);

        g.setFont(new Font("Comic sans ms", Font.BOLD, 32));
        g.drawString("Score: " + score, 10, 32);
        g.drawString("High Score: " + highScore, 10, 64);
        g.drawString("Difficulty: " + difficulty, 300, 32);

        for (Entity entity : entities) {
            entity.render(g);
        }
        g.setColor(Color.BLACK);
        if(p.isDead()) {
            g.drawString("YOU DIED!", 120, 400);
            g.drawString("PRESS SPACE TO RESTART", 20, 450);
        }
    }

    @Override
    public String getName() {
        return "game";
    }

    public int getHighScore() {
        return highScore;
    }
}
