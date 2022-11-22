package com.hermscoder.entities;

import com.hermscoder.main.Game;
import com.hermscoder.utils.Constants;

import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.utils.Sprite.CrabbySpriteAtlas;

public class Crabby extends Enemy{
    public Crabby(float x, float y) {
        super(x, y,
                CrabbySpriteAtlas.getTileWidth(SCALE),
                CrabbySpriteAtlas.getTileHeight(SCALE),
                Constants.EnemyConstants.CRABBY);
        initHitBox(x, y, (int) (22 * SCALE),(int) (19 * SCALE));
    }
}
