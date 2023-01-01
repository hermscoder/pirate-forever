package com.hermscoder.entities;

import com.hermscoder.audio.SoundEffect;
import com.hermscoder.gamestates.Playing;
import com.hermscoder.objects.BareHands;
import com.hermscoder.objects.Weapon;
import com.hermscoder.utils.HelpMethods;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.main.Game.TILES_SIZE;
import static com.hermscoder.utils.Constants.PlayerConstants.*;
import static com.hermscoder.utils.Sprite.PlayerSpriteAtlas;
import static com.hermscoder.utils.Sprite.StatusBar;

public class Player extends Entity {

    private static final int POWER_ATTACK_TICKS = 35;
    private static final int POWER_ATTACK_COST = 60;

//    private final EntityConstants entityConstants;

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

    private int powerBarWidth = (int) (104 * SCALE);
    private int powerBarHeight = (int) (2 * SCALE);
    private int powerBarXStart = (int) (44 * SCALE);
    private int powerBarYStart = (int) (34 * SCALE);
    private int powerWidth = healthBarWidth;
    private int powerMaxValue = 200;
    private int powerValue = powerMaxValue;


    private int flipX = 0;
    private int flipW = 1;
    private boolean attackChecked;
    private int tileY;

    private boolean powerAttackActive;
    private int powerAttackTick;
    private static final int POWER_GROW_SPEED = 20;
    private int powerGrowTick = 0;

    private boolean takingHit;
    private int knockBackDirection;
    private float knockBackXForce = 0.3f * SCALE;
    private float knockBackYForce = -0.9f * SCALE;

    private Weapon currentWeapon;


    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height, PLAYER);
        this.playing = playing;
        this.state = IDLE;
        loadAnimations();
        initHitBox(entityConstants.getHitBoxWidth(), entityConstants.getHitBoxHeight());

        changeWeapon(new BareHands((int) x, (int) y, this));
    }

    public void setSpawn(Point spawn) {
        this.x = spawn.x;
        this.y = spawn.y;
        hitBox.x = x;
        hitBox.y = y;
        resetAttackBox();
    }

    public void update() {
        updateHealthBar();
        updatePowerBar();
        if (currentHealth <= 0) {
            if (state != DEAD) {
                state = DEAD;
                animationTick = 0;
                animationIndex = 0;
                playing.setPlayerDying(true);
                playing.getGame().getAudioPlayer().playEffect(SoundEffect.DIE);
            } else if (checkAnimationFinished(DEAD)) {
                playing.setGameOver(true);
                playing.getGame().getAudioPlayer().stopSong();
                playing.getGame().getAudioPlayer().playEffect(SoundEffect.GAME_OVER);
            } else {
                updateAnimationTick();
                updatePosition();
            }
            return;
        }

        updateAttackBox();
        updatePosition();
        if (moving) {
            checkPotionTouched();
            checkSpikesTouched();
            checkWeaponTouched();
            tileY = (int) (hitBox.y / TILES_SIZE);
            if (powerAttackActive) {
                powerAttackTick++;
                if (powerAttackTick >= POWER_ATTACK_TICKS)
                    stopPowerAttack();
            }
        }
        if (attacking || powerAttackActive) {
            checkAttack();
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

    private void checkWeaponTouched() {
        playing.checkWeaponTouched(this);
    }


    private void checkAttack() {
        if (attackChecked || animationIndex != 1)
            return;

        attackChecked = true;

        //to make sure every update we will be attacking
        if (powerAttackActive)
            attackChecked = false;

        playing.checkEnemyHit(attackBox);
        playing.checkObjectHit(attackBox);
        playing.getGame().getAudioPlayer().playAttackSound();
    }

    private void updateAttackBox() {
        if (right && left) {
            attackBox.x = hitBox.x + (hitBox.width * flipW);
        } else if (right || (powerAttackActive && flipW == FACING_RIGHT)) {
            attackBox.x = hitBox.x + hitBox.width;
        } else if (left || (powerAttackActive && flipW == FACING_LEFT)) {
            attackBox.x = hitBox.x - hitBox.width;
        }
        attackBox.y = hitBox.y + (10 * SCALE);
    }

    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
    }

    private void updatePowerBar() {
        powerWidth = (int) ((powerValue / (float) powerMaxValue) * powerBarWidth);
        powerGrowTick++;
        if (powerGrowTick >= POWER_GROW_SPEED) {
            powerGrowTick = 0;
            changePower(1);
        }
    }

    public void render(Graphics g, int xLevelOffset) {
        g.drawImage(animations[state][animationIndex], (int) (hitBox.x - xDrawOffset) - xLevelOffset + flipX, (int) (hitBox.y - yDrawOffset), width * flipW, height, null);
        if (currentWeapon != null) {
            currentWeapon.draw(g, xLevelOffset);
        }
//        drawHitBox(g, xLevelOffset);
//        currentWeapon.drawAttackBox(g, xLevelOffset);
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

        //filling power rectangle
        g.setColor(Color.YELLOW);
        g.fillRect(powerBarXStart + statusBarX, powerBarYStart + statusBarY, powerWidth, powerBarHeight);
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
        float xSpeed = 0;

        if (state != DEAD) {
            if (jump)
                jump();
            if (!inAir)
                if (!powerAttackActive)
                    if (!takingHit)
                        if ((!left && !right) || (right && left))
                            return;


            if (left && !right) {
                xSpeed -= walkSpeed;
                flipX = width;
                flipW = FACING_LEFT;
            }
            if (right && !left) {
                xSpeed += walkSpeed;
                flipX = 0;
                flipW = FACING_RIGHT;
            }

            if (powerAttackActive) {
                if ((!left && !right) || (left && right))
                    xSpeed = walkSpeed * flipW;
                xSpeed *= 3;
            }

            if (takingHit) {
                if (animationIndex == 0) {
                    inAir = true;
                    airSpeed = knockBackYForce;
                }
                xSpeed += knockBackDirection * knockBackXForce;
                resetAttackBox();
            }
        }


        if (!inAir && !HelpMethods.isEntityOnFloor(hitBox, lvlData)) {
            inAir = true;
        }

        if (inAir && !powerAttackActive) {
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
        playing.getGame().getAudioPlayer().playEffect(SoundEffect.JUMP);
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
            if (powerAttackActive)
                stopPowerAttack();
            hitBox.x = HelpMethods.getEntityXPosNextToWall(hitBox, xSpeed);
        }
    }

    private void stopPowerAttack() {
        powerAttackActive = false;
        powerAttackTick = 0;
    }

    public void hit(Rectangle2D.Float hitterHitBox, int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0) {
            kill();
        } else {
            takingHit = true;
            knockBackDirection = hitterHitBox.x - hitBox.x < 0 ? FACING_RIGHT : FACING_LEFT;
        }
    }

    public void heal(int value) {
        currentHealth += value;
        if (currentHealth >= maxHealth) {
            currentHealth = maxHealth;
        }
    }

    public void kill() {
        currentHealth = 0;
    }

    public void changePower(int value) {
        powerValue += value;
        if (powerValue >= powerMaxValue)
            powerValue = powerMaxValue;
        else if (powerValue <= 0)
            powerValue = 0;
    }

    public void updateAnimationTick() {
        animationTick++;
        if (animationTick >= entityConstants.getAnimationSpeed()) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= entityConstants.getSpriteAmount(state)) {
                animationIndex = 0;
//                attacking = false;
//                attackChecked = false;
                afterAnimationFinishedAction(state);
            }
        }
    }

    private void setAnimation() {
        int startAnimation = state;

        if (currentHealth <= 0) {
            return;
        } else {
            if (takingHit) {
                state = HIT;
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

                if (powerAttackActive) {
                    state = ATTACK_JUMP_1;
                    animationIndex = 1;
                    animationTick = 0;
                    return;
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


        }


        //if there was a change of action. we need to reset the animation tick so we can display the full animation
        if (startAnimation != state) {
            resetAnimationTick();
        }
    }

    public void powerAttack() {
        if (powerAttackActive)
            return;
        if (powerValue >= POWER_ATTACK_COST) {
            powerAttackActive = true;
            changePower(-POWER_ATTACK_COST);
        }
    }


    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!HelpMethods.isEntityOnFloor(hitBox, lvlData)) {
            inAir = true;
        }
    }

    private boolean checkAnimationFinished(int state) {
        return this.state == state
                && animationIndex == (entityConstants.getSpriteAmount(state) - 1)
                && animationTick >= (entityConstants.getAnimationSpeed() - 1);
    }

    @Override
    public void afterAnimationFinishedAction(int state) {
        switch (state) {
            case ATTACK_1:
                attacking = false;
                attackChecked = false;
                break;
            case HIT:
                takingHit = false;
                break;
            case DEAD:
                break;
        }
    }

    private void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    public void resetDirBoolean() {
        left = right = up = down = false;
    }

    public void resetAll() {
        resetDirBoolean();
        inAir = false;
        attacking = false;
        moving = false;
        jump = false;
        airSpeed = 0f;
        state = IDLE;
        currentHealth = maxHealth;

        hitBox.x = x;
        hitBox.y = y;

        changeWeapon(new BareHands((int) x, (int) y, this));

        if (!HelpMethods.isEntityOnFloor(hitBox, lvlData)) {
            inAir = true;
        }
    }

    private void resetAttackBox() {
        if (flipW == FACING_RIGHT) {
            attackBox.x = hitBox.x + hitBox.width;
        } else {
            attackBox.x = hitBox.x - hitBox.width;
        }
    }

    public void changeWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
        attackBox = currentWeapon.getAttackBox();
        resetAttackBox();
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

    public int getTileY() {
        return this.tileY;
    }

    public float getxDrawOffset() {
        return xDrawOffset;
    }

    public float getyDrawOffset() {
        return yDrawOffset;
    }

    public int getFlipX() {
        return flipX;
    }

    public int getFlipW() {
        return flipW;
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }


}
