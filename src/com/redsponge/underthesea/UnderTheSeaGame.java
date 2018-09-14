package com.redsponge.underthesea;

import com.redsponge.redutils.GraphicsApp;
import com.redsponge.redutils.annotation.Instance;
import com.redsponge.underthesea.state.StateGame;
import com.redsponge.underthesea.state.StateManager;

public class UnderTheSeaGame extends GraphicsApp {

    @Instance
    public static UnderTheSeaGame instance;

    public UnderTheSeaGame() {
        super("Under The Sea Game!", 500, 500, 60, 60);
        start();
    }

    private StateManager stateManager;

    @Override
    public void init() {
        stateManager = new StateManager();
        stateManager.registerState(new StateGame());
        stateManager.setCurrentState("game");
    }

    @Override
    public void tick() {
        stateManager.tick();
    }

    @Override
    public void render() {
        stateManager.render(g);
    }

    public StateManager getStateManager() {
        return stateManager;
    }
}
