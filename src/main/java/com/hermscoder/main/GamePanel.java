package com.hermscoder.main;

import com.hermscoder.inputs.KeyboardInputs;
import com.hermscoder.inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

import static com.hermscoder.main.Game.GAME_HEIGHT;
import static com.hermscoder.main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {

    private Game game;

    public GamePanel(Game game) {
        MouseInputs mouseInputs = new MouseInputs(this);

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

        this.game = game;
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("size : " + GAME_WIDTH + "x" + GAME_HEIGHT);
    }


    public void updateGame() {
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}
