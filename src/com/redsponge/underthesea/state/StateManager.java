package com.redsponge.underthesea.state;

import java.awt.Graphics2D;
import java.util.HashMap;

public class StateManager {

    private State current;
    private HashMap<String, State> states;

    public StateManager() {
        states = new HashMap<>();
    }

    public void registerState(State s) {
        states.put(s.getName(), s);
    }

    public State getState(String name) {
        return states.get(name);
    }

    public void setCurrentState(String s) {
        current = states.get(s);
    }

    public void tick() {
        current.tick();
    }

    public void render(Graphics2D g) {
        current.render(g);
    }
}
