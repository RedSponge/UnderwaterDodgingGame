package com.redsponge.underthesea.math;

public class RVector {

    public float x, y;

    public RVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public RVector(RVector v) {
        this(v.x, v.y);
    }

    public RVector() {
        this(0, 0);
    }

    public RVector clone() {
        return new RVector(this.x, this.y);
    }

    public RVector add(RVector v) {
        return this.add(v.x, v.y);
    }

    public RVector add(float x, float y) {
        this.x+=x;
        this.y+=y;
        return this;
    }

    public RVector scl(float x, float y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public RVector scl(RVector v) {
        return this.scl(v.x, v.y);
    }

    public RVector set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public RVector set(RVector v) {
        return this.set(v.x, v.y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
