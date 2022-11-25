package com.hermscoder.entities;

import com.hermscoder.gamestates.Playing;
import com.hermscoder.utils.Constants;
import com.hermscoder.utils.EntityConstants;
import com.hermscoder.utils.HelpMethods;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.utils.Constants.PlayerConstants.*;
import static com.hermscoder.utils.Sprite.PlayerSpriteAtlas;
import static com.hermscoder.utils.Sprite.StatusBar;

public class Player extends Entity {

    private final EntityConstants entityConstants;

    private final Playing playing;
    private BufferedImage[][] animations;

    private boolean left, up, right, down, jump;
    private boolean moving = false, attacking = false;

    //Jumping / Gravity
    private float jumpSpeed = -2.25f * SCALE;
    private float fallSpeedAfterCollision = 0.5f * SCALE;


    //Hitbox offset
    private float xDrawOffset = 21 * SCALE;
    private float yDrawOffset = 4 * SCALE;

    private int[][] lvlData;

    //StatusBar UI
    private BufferedImage statusBarImg;

    private int statusBarX = (int) (10 * SCALE);
    private int statusBarY = (int) (10 * SCALE);

    private int healthBarWidth = (int) (150 * SCALE);
    private int healthBarHeight = (int) (4 * SCALE);
    private int healthBarXStart = (int) (34 * SCALE);
    private int healthBarYStart = (int) (14 * SCALE);

    private int healthWidth = healthBarWidth;


    private int flipX = 0;
    private int flipW = 1;
    private boolean attackChecked;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        this.entityConstants = Constants.getEntityConstants(PLAYER);
        this.state = IDLE;
        this.maxHealth = entityConstants.getMaxHealth();
        this.currentHealth = maxHealth;
        this.walkSpeed = 1.0f * SCALE;
        loadAnimations();
        initHitBox(20,27);
        initAttackBox();
    }

    public void setSpawn(Point spawn) {
        this.x = spawn.x;
        this.y = spawn.y;
        hitBox.x = x;
        hitBox.y = y;
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (20 * SCALE), (int) (20 * SCALE));
    }

    public void update() {
        updateHealthBar();
        if (currentHealth <= 0 && state == DEAD) {
            if(checkAnimationIsLastFrame(DEAD)) {
                playing.setGameOver(true);
                return;
            }
        } else {
            updateAttackBox();
            updatePosition();
            if(moving) {
                checkPotionTouched();
                checkSpikesTouched();
            }
            if (attacking) {
                checkAttack();
            }
        }

        updateAnimationTick();
        setAnimation();
    }

    private void checkSpikesTouched() {
        playing.checkSpikesTouched(this);
    }

    private void checkPotionTouched() {
        playing.checkPotionTouched(hitBox);
    }

    private void checkAttack() {
        if (attackChecked || animationIndex != 1)
            return;

        attackChecked = true;
        playing.checkEnemyHit(attackBox);
        playing.checkObjectHit(attackBox);
    }

    private void updateAttackBox() {
        if (right) {
            attackBox.x = hitBox.x + hitBox.width + (int) (10 * SCALE);
        } else if (left) {
            attackBox.x = hitBox.x - hitBox.width - (int) (10 * SCALE);
        }

        attackBox.y = hitBox.y + (10 * SCALE);
    }

    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
    }

    public void render(Graphics g, int xLevelOffset) {
        g.drawImage(animations[state][animationIndex], (int) (hitBox.x - xDrawOffset) - xLevelOffset + flipX, (int) (hitBox.y - yDrawOffset), width * flipW, height, null);
//        drawHitBox(g, xLevelOffset);
//        drawAttackBox(g, xLevelOffset);
        drawUi(g);
    }

    private void drawUi(Graphics g) {
        g.drawImage(statusBarImg,
                statusBarX,
                statusBarY,
                StatusBar.getTileWidth(SCALE),
                StatusBar.getTileHeight(SCALE),
                null);
        //filling health rectangle
        g.setColor(Color.RED);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
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

        statusBarImg = LoadSave.getSpriteAtlas(StatusBar.getFilename());

    }

    private void updatePosition() {
        moving = false;
        if (jump)
            jump();
        if (!inAir)
            if ((!left && !right) || (right && left))
                return;

        float xSpeed = 0;

        if (left) {
            xSpeed -= walkSpeed;
            flipX = width;
            flipW = -1;
        }
        if (right) {
            xSpeed += walkSpeed;
            flipX = 0;
            flipW = 1;
        }

        if (!inAir) {
            if (!HelpMethods.isEntityOnFloor(hitBox, lvlData))
                inAir = true;

        }

        if (inAir) {
            if (HelpMethods.canMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData)) {
                hitBox.y += airSpeed;
                airSpeed += entityConstants.getGravity();
                updateXPos(xSpeed);
            } else {
                hitBox.y = HelpMethods.getEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed);
                if (airSpeed > 0)
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
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0f;
    }

    private void updateXPos(float xSpeed) {
        if (HelpMethods.canMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
            hitBox.x += xSpeed;
        } else {
            hitBox.x = HelpMethods.getEntityXPosNextToWall(hitBox, xSpeed);
        }
    }

    public void changeHealth(int value) {
        currentHealth += value;
        if (currentHealth <= 0) {
            kill();
            //gameOver();
        } else if (currentHealth >= maxHealth) {
            currentHealth = maxHealth;
        }
    }

    public void kill() {
        currentHealth = 0;
    }

    public void changePower(int value) {
        System.out.println("added " + value + "of power!");
    }

    public void updateAnimationTick() {
        animationTick++;
        if (animationTick >= entityConstants.getAnimationSpeed()) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= entityConstants.getSpriteAmount(state)) {
                animationIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }

    private void setAnimation() {
        int startAnimation = state;

        if(currentHealth <= 0) {
            state = DEAD;
            return;
        } else {
            if (moving)
                state = RUNNING;
            else
                state = IDLE;

            if (inAir) {
                if (airSpeed < 0)
                    state = JUMP;
                else
                    state = FALLING;

            }

            if (attacking) {
                state = ATTACK_1;
                // if we were already attacking we jump to animation index 1 to make the animation more responsive
                if (startAnimation != ATTACK_1) {
                    animationIndex = 1;
                    animationTick = 0;
                    return;
                }
            }
        }



        //if there was a change of action. we need to reset the animation tick so we can display the full animation
        if (startAnimation != state) {
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

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!HelpMethods.isEntityOnFloor(hitBox, lvlData)) {
            inAir = true;
        }
    }

    private boolean checkAnimationIsLastFrame(int state) {
        return animationIndex + 1 == entityConstants.getSpriteAmount(state);
    }

    public void resetAll() {
        resetDirBoolean();
        inAir = false;
        attacking = false;
        moving = false;
        state = IDLE;
        currentHealth = maxHealth;

        hitBox.x = x;
        hitBox.y = y;

        if (!HelpMethods.isEntityOnFloor(hitBox, lvlData)) {
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
