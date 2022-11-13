package com.hermscoder.entities;

import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.hermscoder.utils.Sprite.PlayerSpriteAtlas;

import static com.hermscoder.utils.Constants.PlayerConstants.*;

public class Player extends Entity {

    private BufferedImage[][] animations;

    private int animationTick, animationIndex, animationSpeed = 15;
    private int playerAction = IDLE;
    private boolean left, up, right, down;
    private float playerSpeed = 2.0f;
    private boolean moving = false, attacking = false;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int) x, (int) y, width, height, null);
    }

    private void loadAnimations() {

        BufferedImage img = LoadSave.getSpriteAtlas(PlayerSpriteAtlas.getFilename());
        animations = new BufferedImage[PlayerSpriteAtlas.getHeightInSprites()][PlayerSpriteAtlas.getWidthInSprites()];
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(
                        i * PlayerSpriteAtlas.getTileWidth(),
                        j * PlayerSpriteAtlas.getTileHeight(),
                        PlayerSpriteAtlas.getTileWidth(),
                        PlayerSpriteAtlas.getTileHeight());
            }
        }

    }

    private void updatePosition() {
        moving = false;

        if (left && !right) {
            x -= playerSpeed;
            moving = true;
        } else if (right && !left) {
            x += playerSpeed;
            moving = true;
        }

        if (up && !down) {
            y -= playerSpeed;
            moving = true;
        } else if (down && !up) {
            y += playerSpeed;
            moving = true;
        }
    }

    public void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= getSpriteAmount(playerAction)) {
                animationIndex = 0;
                attacking = false;
            }
        }
    }

    private void setAnimation() {
        int startAction = playerAction;

        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;

        if (attacking)
            playerAction = ATTACK_1;

        //if there was a change of action. we need to reset the animation tick so we can display the full animation
        if (startAction != playerAction) {
            resetAnimationTick();
        }
    }

    private void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void resetDirBoolean() {
        left = right = up = down = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }
}
