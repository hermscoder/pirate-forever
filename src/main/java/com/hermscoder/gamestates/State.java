package com.hermscoder.gamestates;

import com.hermscoder.audio.Song;
import com.hermscoder.main.Game;
import com.hermscoder.ui.MenuButton;

import java.awt.event.MouseEvent;

public class State {
    protected final Game game;

    public State(Game game) {
        this.game = game;
    }

    public boolean isIn(MouseEvent e, MenuButton menuButton) {
        return menuButton.getBounds().contains(e.getX(), e.getY());
    }

    public Game getGame() {
        return game;
    }

    public void setGameState(GameState state) {
        switch (state) {
            case MENU:
                game.getAudioPlayer().playSong(Song.MENU_1);
            case PLAYING:
                game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getCurrentLevel().getIndex());
        }
        GameState.state = state;
    }
}
