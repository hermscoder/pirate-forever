package com.hermscoder.entities;

import com.hermscoder.main.Game;
import com.hermscoder.utils.HelpMethods;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.hermscoder.utils.Sprite.PlayerSpriteAtlas;

import static com.hermscoder.utils.Constants.PlayerConstants.*;

public class Player extends Entity {

    private BufferedImage[][] animations;

    private int animationTick, animationIndex, animationSpeed = 15;
    private int playerAction = IDLE;
    private boolean left, up, right, down, jump;
    private float playerSpeed = 1.0f * Game.SCALE;
    private boolean moving = false, attacking = false;

    //Jumping / Gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    //Hitbox offset
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;

    private int[][] lvlData;


    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitBox(x, y, (int) (20 * Game.SCALE), (int) (27*Game.SCALE) );
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int) (hitBox.x - xDrawOffset), (int) (hitBox.y - yDrawOffset), width, height, null);
//        drawHitBox(g);
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
        if(jump)
            jump();
        if(!left && !right && !inAir)
            return;

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
        }
        if (right) {
            xSpeed += playerSpeed;
        }

        if(!inAir) {
            if(!HelpMethods.isEntityOnFloor(hitBox, lvlData))
                inAir = true;

        }

        if(inAir){
            if(HelpMethods.canMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData)) {
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitBox.y = HelpMethods.getEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed);
                if(airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;

                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }
        moving = true;
    }

    private void jump() {
        if(inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0f;
    }

    private void updateXPos(float xSpeed) {
        if(HelpMethods.canMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
            hitBox.x += xSpeed;
        } else {
            hitBox.x = HelpMethods.getEntityXPosNextToWall(hitBox, xSpeed);
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

        if(inAir) {
            if(airSpeed < 0)
                playerAction = JUMP;
            else
                playerAction = FALLING;

        }

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

    public void resetDirBoolean() {
        left = right = up = down = false;
    }

    public void loadLvlData(int [][] lvlData) {
        this.lvlData = lvlData;
        if(!HelpMethods.isEntityOnFloor(hitBox, lvlData)) {
            inAir = true;
        }
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

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
