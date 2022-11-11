package com.hermscoder.main;

import com.hermscoder.inputs.KeyboardInputs;
import com.hermscoder.inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel {
    private float xDelta = 100, yDelta = 100;
    private float xDirection = 1f, yDirection = 1f;
    private Color color = new Color(150, 20, 90);
    private Random random;

    private int frames = 0;
    private long lastCheck;

    public GamePanel() {
        random = new Random();

        MouseInputs mouseInputs = new MouseInputs(this);

        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void changeXDelta(int value) {
        this.xDelta += value;
    }

    public void changeYDelta(int value) {
        this.yDelta += value;
    }

    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        updateRectangle();
        g.setColor(color);
        g.fillRect((int)xDelta, (int)yDelta, 200, 50);
    }

    private void updateRectangle() {
        xDelta += xDirection;
        if(xDelta > 400 || xDelta < 0) {
            xDirection *= -1;
            color = getRandomColor();
        }
        yDelta += yDirection;
        if(yDelta > 400 || yDelta < 0) {
            yDirection *= -1;
            color = getRandomColor();
        }
    }

    private Color getRandomColor() {
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return new Color(r,g,b);
    }


}
