package com.redsponge.underthesea.game;

import com.redsponge.redutils.gfx.Texture;
import com.redsponge.underthesea.math.RVector;
import com.redsponge.underthesea.math.Util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Obstacle extends Entity {

    private int speed;
    private float wiggliness;
    private float rotatiness;
    private float rotation;
    private RVector vel;

    private long creationTime;
    private Rectangle boundingBox;
    private boolean dead;

    private ObstacleType type;

    private Player player;


    public Obstacle(Player player) {
        this.player = player;
        this.type = ObstacleType.randomType();
        creationTime = System.nanoTime();

        pos.set(500, (float) Math.random()*200+200);

        speed = 3;
        wiggliness = (float) Math.random();
        rotatiness = (0.5f - wiggliness) / type.weight;

        double scale = Math.min(1, Math.random() + 0.5f);

        speed /= scale;

        boundingBox = new Rectangle(1000, 1000, (int) (type.width * scale), (int) (type.height * scale));

        vel = new RVector();
    }

    @Override
    public void tick() {
        vel.set(0, 0);

        vel.add(-speed, (float) Math.sin(Util.secondsSince(creationTime)) * wiggliness);

        pos.add(vel);

        boundingBox.x = (int) pos.x;
        boundingBox.y = (int) pos.y;

        rotation += rotatiness * (60 / 1000f);

        if(pos.x + boundingBox.width < -10) dead = true;

        checkPlayerHit();
    }

    private void checkPlayerHit() {
        if(player.boundingBox.intersects(this.boundingBox)) {
            player.dead = true;
            try {
                player.saveHighscore();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public int pointsWorth() {
        return type.points;
    }

    @Override
    public void render(Graphics2D g) {
        double x = boundingBox.x;
        double y = boundingBox.y;
        double w = boundingBox.width;
        double h = boundingBox.height;

        double scl = 10f;
        x -= scl;
        w += scl * 2;
        y -= scl;
        h += scl * 2;

        type.texture.render(g, (int) (x + (w / 2)), (int) (y + (h / 2)), (int) w, (int) h, true, (double) rotation);
//        g.setColor(new Color(0, 0, 0, 100));
//        g.fillRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }

    enum ObstacleType {
        BARREL(Texture.load("barrel"), 64, 64, 10, 1),
        ANCHOR(Texture.load("anchor"), 64, 64, 10, 10);

        private static final Random rand = new Random();
        public static final ObstacleType[] ALL = new ObstacleType[] {BARREL, ANCHOR};

        private Texture texture;
        private int width, height;
        private int points;
        private int weight;

        ObstacleType(Texture texture, int width, int height, int points, int weight) {
            this.texture = texture;
            this.width = width;
            this.height = height;
            this.points = points;
            this.weight = weight;
        }

        public static ObstacleType randomType() {
            return ALL[rand.nextInt(ALL.length)];
        }
    }
}
