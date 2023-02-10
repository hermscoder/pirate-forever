package com.hermscoder.entities;

import com.hermscoder.audio.SoundEffect;
import com.hermscoder.gamestates.Playing;
import com.hermscoder.objects.BareHands;
import com.hermscoder.objects.Key;
import com.hermscoder.objects.MapFragment;
import com.hermscoder.objects.Weapon;
import com.hermscoder.utils.HelpMethods;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.main.Game.TILES_SIZE;
import static com.hermscoder.utils.Constants.PlayerConstants.*;
import static com.hermscoder.utils.Sprite.PlayerSpriteAtlas;

public class Player extends Entity {

    private static final int DASH_TICKS = 35;
    private static final int POWER_DASH_COST = 10;
    private static final int POWER_ATTACK_TICKS = 35;
    private static final int POWER_ATTACK_COST = 60;

    private final Playing playing;
    private BufferedImage[][] animations;

    private boolean left;
    private boolean right;
    private boolean jump;

    private int lastKeyPressed;
    private int lastKeyReleased;
    private long lastTimeKeyReleasedInMillis;

    private boolean moving = false;
    private boolean attacking = false;

    //Jumping / Gravity
    private final float jumpSpeed = -2.25f * SCALE;
    private final float fallSpeedAfterCollision = 0.5f * SCALE;

    private int[][] lvlData;

    private int powerMaxValue = 200;
    private int powerValue = powerMaxValue;

    private int flipX = 0;
    private int flipW = 1;
    private boolean attackChecked;
    private int tileY;

    private boolean dashActive;
    private int dashTick;

    private boolean powerAttackActive;
    private int powerAttackTick;
    private static final int POWER_GROW_SPEED = 20;
    private int powerGrowTick = 0;

    private boolean takingHit;
    private int knockBackDirection;
    private float knockBackXForce = 0.3f * SCALE;
    private float knockBackYForce = -0.9f * SCALE;

    private boolean interacting;

    private Weapon currentWeapon;

    private List<Key> keysCollected = new ArrayList<>();
    private List<MapFragment> mapFragmentsCollected = new ArrayList<>();

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
        updatePowerValue();
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
            checkObjectsTouched();
            tileY = (int) (hitBox.y / TILES_SIZE);
            if (powerAttackActive) {
                powerAttackTick++;
                if (powerAttackTick >= POWER_ATTACK_TICKS)
                    stopPowerAttack();
            }
            if (dashActive) {
                dashTick++;
                if (dashTick >= DASH_TICKS) {
                    stopDash();
                }
            }
        }
        if (attacking || powerAttackActive) {
            checkAttack();
        }

        if (interacting) {
            checkInteractableObjects();
            interacting = false;
        }

        updateAnimationTick();
        setAnimation();
    }

    private void checkObjectsTouched() {
        playing.checkObjectsTouched(this);
    }

    private void checkAttack() {
        if (attackChecked || animationIndex != 1)
            return;

        attackChecked = true;

        //to make sure every update we will be attacking
        if (powerAttackActive)
            attackChecked = false;

        playing.checkEnemyHit(attackBox, currentWeapon.getDamageValue());
        playing.checkObjectHit(attackBox);
        playing.getGame().getAudioPlayer().playAttackSound();
    }

    private void checkInteractableObjects() {
        playing.checkObjectsInteracted(this);
    }

    private void updateAttackBox() {
        if (right && left) {
            attackBox.x = hitBox.x + (hitBox.width * flipW);
        } else if (right || ((dashActive || powerAttackActive) && flipW == FACING_RIGHT)) {
            attackBox.x = hitBox.x + hitBox.width;
        } else if (left || ((dashActive || powerAttackActive) && flipW == FACING_LEFT)) {
            attackBox.x = hitBox.x - hitBox.width;
        }
        attackBox.y = hitBox.y + (10 * SCALE);
    }

    private void updatePowerValue() {
        powerGrowTick++;
        if (powerGrowTick >= POWER_GROW_SPEED) {
            powerGrowTick = 0;
            changePower(1);
        }
    }

    @Override
    public void draw(Graphics g, int xLevelOffset) {
        g.drawImage(animations[state][animationIndex], (int) (hitBox.x - xDrawOffset) - xLevelOffset + flipX, (int) (hitBox.y - yDrawOffset), width * flipW, height, null);
        if (currentWeapon != null) {
            currentWeapon.draw(g, xLevelOffset);
        }
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
        float xSpeed = 0;

        if (state != DEAD) {
            if (jump)
                jump();
            if (!inAir)
                if (!powerAttackActive)
                    if (!dashActive)
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

            if (dashActive) {
                if ((!left && !right) || (left && right))
                    xSpeed = walkSpeed * flipW;

                xSpeed *= 3 - (3 * (dashTick / DASH_TICKS));
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
            if (dashActive) {
                stopDash();
            }
            hitBox.x = HelpMethods.getEntityXPosNextToWall(hitBox, xSpeed);
        }
    }

    public void stopDash() {
        dashActive = false;
        dashTick = 0;
        lastKeyReleased = -1;
        lastKeyPressed = -1;
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

                if (dashActive) {
                    if (attacking) {
                        if (inAir) {
                            state = ATTACK_JUMP_1;
                            animationIndex = 0;
                        } else {
                            state = ATTACK_JUMP_2;
                            animationIndex = 1;
                        }
                    } else {
                        state = ATTACK_JUMP_2;
                        animationIndex = 0;
                    }
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

    public void interact() {
        if (interacting)
            return;
        interacting = true;
    }

    public void powerAttack() {
        if (powerAttackActive)
            return;
        if (powerValue >= POWER_ATTACK_COST) {
            powerAttackActive = true;
            changePower(-POWER_ATTACK_COST);
        }
    }

    public void dash() {
        if (dashActive)
            return;

        if (powerValue >= POWER_DASH_COST) {
            dashActive = true;
            changePower(-POWER_DASH_COST);
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
        left = right = false;
    }

    public void resetAll() {
        resetDirBoolean();
        inAir = false;
        attacking = false;
        moving = false;
        jump = false;
        interacting = false;
        airSpeed = 0f;
        state = IDLE;
        currentHealth = maxHealth;

        hitBox.x = x;
        hitBox.y = y;

        keysCollected.clear();
        mapFragmentsCollected.clear();

        changeWeapon(new BareHands((int) x, (int) y, this));

        if (!HelpMethods.isEntityOnFloor(hitBox, lvlData)) {
            inAir = true;
        }
    }


    public void changeWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
        attackBox = currentWeapon.getAttackBox();
        resetAttackBox();
    }


    public void addKeyToCollection(Key key) {
        keysCollected.add(key);
    }

    public void addMapFragmentToCollection(MapFragment mapFragment) {
        mapFragmentsCollected.add(mapFragment);
    }


    private void resetAttackBox() {
        if (flipW == FACING_RIGHT) {
            attackBox.x = hitBox.x + hitBox.width;
        } else {
            attackBox.x = hitBox.x - hitBox.width;
        }
    }

    public boolean isDashActive() {
        return dashActive;
    }


    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean checkIfDoublePressed(int keyCode, long acceptableTimeWhenLastPressed) {
        return lastKeyPressed == keyCode && lastKeyReleased == keyCode
                && System.currentTimeMillis() - lastTimeKeyReleasedInMillis <= acceptableTimeWhenLastPressed;
    }

    public void changeLastKeyPressed(int lastKeyPressed) {
        this.lastKeyPressed = lastKeyPressed;
    }

    public void changeLastKeyReleasedAndResetTimer(int lastKeyReleased) {
        this.lastKeyReleased = lastKeyReleased;
        lastTimeKeyReleasedInMillis = System.currentTimeMillis();
    }


    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
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

    public int getPowerValue() {
        return powerValue;
    }

    public List<Key> getKeysCollected() {
        return keysCollected;
    }

    public List<MapFragment> getMapFragmentsCollected() {
        return mapFragmentsCollected;
    }

    public int getPowerMaxValue() {
        return powerMaxValue;
    }
}
