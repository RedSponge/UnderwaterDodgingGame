package com.redsponge.underthesea.game;

import com.redsponge.underthesea.math.RVector;

import java.awt.Graphics2D;
import java.util.Vector;

public abstract class Entity {

    protected RVector pos = new RVector();

    public abstract void tick();

    public abstract void render(Graphics2D g);

    public abstract boolean isDead();

    public abstract int pointsWorth();
}
