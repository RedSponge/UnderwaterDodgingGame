package com.redsponge.underthesea.math;

public class Util {

    public static float secondsSince(long time) {
        return (System.nanoTime() - time) / 1000000000f;
    }

}
