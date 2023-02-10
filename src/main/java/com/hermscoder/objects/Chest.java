package com.hermscoder.objects;

import com.hermscoder.entities.Player;
import com.hermscoder.main.Game;
import com.hermscoder.objects.type.Interactable;
import com.hermscoder.objects.type.Touchable;
import com.hermscoder.utils.ObjectConstants;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Chest extends Interactable {
    private boolean opened;
    private Consumer<List<Touchable>> callBack;
    private List<Touchable> drops = new ArrayList<>();

    public Chest(int x, int y, int objectType) {
        super(x, y, objectType);
    }

    @Override
    public void onInteract(ObjectManager objectManager, Player player, Consumer<List<Touchable>> callback) {
        if (opened)
            return;
        if (player.getKeysCollected().isEmpty()) {
            //TODO add animation to show that user needs a key to open it
            return;
        }

        player.getKeysCollected().remove(0);

        drops.clear();
        for (int i = 0; i < 2; i++) {
            drops.add(new Potion(
                    (int) (getHitBox().x + getHitBox().width / 2 - (i * getHitBox().width / 2)),
                    (int) (getHitBox().y - getHitBox().height / 2), i + 1));
        }
        int missingFragmentNumber = calculateMapFragmentMissing(player);
        if (missingFragmentNumber > 0) {
            drops.add(new MapFragment((int) (getHitBox().x + getHitBox().width / 2),
                    (int) (getHitBox().y - getHitBox().height / 2), ObjectConstants.MAP_PIECE_1 - 1 + missingFragmentNumber, missingFragmentNumber));
        }

        this.callBack = callback;

        doAnimation = true;
        opened = true;
    }

    private int calculateMapFragmentMissing(Player player) {
        List<Integer> collectedMapFragmentNumbers = player.getMapFragmentsCollected().stream().map(map -> map.getFragmentNumber()).collect(Collectors.toList());
        List<Integer> missingFragments = Arrays.asList(1, 2, 3, 4).stream()
                .filter(missingMapFragmentNumber -> !collectedMapFragmentNumbers.contains(missingMapFragmentNumber))
                .collect(Collectors.toList());

        if (missingFragments.isEmpty())
            return 0;

        return missingFragments.get(0);

    }

    public void update() {
        if (doAnimation) {
            updateAnimationTick();
        }
    }

    @Override
    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(objectConstants.getAnimationImage(0, animationIndex),
                (int) (hitBox.x - xDrawOffset - xLvlOffset),
                (int) (hitBox.y),
                (int) ((objectConstants.getSpriteAtlas().getTileWidth() - 1) * Game.SCALE),
                (int) ((objectConstants.getSpriteAtlas().getTileHeight() - 1) * Game.SCALE), null);
//        drawHitBox(g, xLvlOffset);
    }

    @Override
    public void reset() {
        super.reset();
        opened = false;
    }

    @Override
    public void afterAnimationFinishedAction() {
        doAnimation = false;
        animationIndex = objectConstants.getAnimationSpriteAmount(objectType) - 1;
        callBack.accept(drops);
    }

}
