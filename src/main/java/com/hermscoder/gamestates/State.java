package com.hermscoder.gamestates;

import com.hermscoder.main.Game;

public class State {
    protected final Game game;

    public State(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
