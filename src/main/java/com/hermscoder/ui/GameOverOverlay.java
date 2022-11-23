package com.hermscoder.ui;

import com.hermscoder.gamestates.GameState;
import com.hermscoder.gamestates.Playing;

import java.awt.*;
import java.awt.event.KeyEvent;

import static com.hermscoder.main.Game.*;

public class GameOverOverlay {
    private final Playing playing;

    public GameOverOverlay(Playing playing) {
        this.playing = playing;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        g.setColor(Color.white);
        g.drawString("Game Over", GAME_WIDTH / 2, 150);
        g.drawString("Press esc to enter Main Menu!", GAME_WIDTH / 2, 300);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            GameState.state = GameState.MENU;
        }
    }
}
