package com.hermscoder.ui.mapview;

public abstract class UIAnimated {
    protected int animationTick;
    protected int animationIndex;
    protected boolean doAnimation;
    protected boolean active = true;
    protected boolean startedAnimated;
    protected int animationSpeed;
    protected int animationSpriteAmount;


    protected UIAnimated(int animationSpeed, int animationSpriteAmount, boolean startedAnimated) {
        this.startedAnimated = startedAnimated;
        this.animationSpeed = animationSpeed;
        this.animationSpriteAmount = animationSpriteAmount;
    }

    public void afterAnimationFinishedAction() {

    }

    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;

            if (animationIndex >= animationSpriteAmount) {
                animationIndex = 0;
                afterAnimationFinishedAction();
            }
        }
    }

    public void reset() {
        animationIndex = 0;
        animationTick = 0;
        active = true;

        doAnimation = startedAnimated;

    }
}
