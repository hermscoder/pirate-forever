package com.hermscoder.objects;

import com.hermscoder.entities.Player;
import com.hermscoder.main.Game;
import com.hermscoder.utils.ObjectConstants;

import java.awt.*;

public class BareHands extends Weapon {

    public BareHands(int x, int y, Player player) {
        super(x, y, ObjectConstants.BARE_HANDS);
        this.player = player;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(objectConstants.getAnimationImage(player.getState(), player.getAnimationIndex()),
                (int) (player.getHitBox().x - player.getxDrawOffset() - xLvlOffset + player.getFlipX()),
                (int) (player.getHitBox().y - player.getyDrawOffset()),
                objectConstants.getSpriteAtlas().getTileWidth(Game.SCALE) * player.getFlipW(),
                objectConstants.getSpriteAtlas().getTileHeight(Game.SCALE), null);
//        drawAttackBox(g, xLvlOffset);

    }
}
