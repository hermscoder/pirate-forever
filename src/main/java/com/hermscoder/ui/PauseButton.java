package com.hermscoder.ui;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class PauseButton {
    protected final int x, y, width, height;
    protected boolean mouseOver, mousePressed;

    // HitBox of the button
    protected Rectangle bounds;

    public PauseButton(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        createBounds();
    }

    public abstract void draw(Graphics g);

    public abstract void onClickAction(MouseEvent e);

    public abstract void update();

    private void createBounds() {
        bounds = new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public final boolean isMouseOver() {
        return mouseOver;
    }

    public final void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public final boolean isMousePressed() {
        return mousePressed;
    }

    public final void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void resetBooleans() {
        mouseOver = mousePressed = false;
    }


}
