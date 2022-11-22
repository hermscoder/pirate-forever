package com.hermscoder.entities;

import com.hermscoder.main.Game;
import com.hermscoder.utils.Constants;

import static com.hermscoder.utils.Sprite.CrabbySpriteAtlas;

public class Crabby extends Enemy{
    public Crabby(float x, float y) {
        super(x, y,
                CrabbySpriteAtlas.getTileWidth(Game.SCALE),
                CrabbySpriteAtlas.getTileHeight(Game.SCALE),
                Constants.EnemyConstants.CRABBY);
    }
}
