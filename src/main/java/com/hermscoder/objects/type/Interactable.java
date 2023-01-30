package com.hermscoder.objects.type;

import com.hermscoder.entities.Player;
import com.hermscoder.objects.GameObject;
import com.hermscoder.objects.ObjectManager;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public abstract class Interactable extends GameObject {

    protected Interactable(int x, int y, int objectType) {
        super(x, y, objectType);
    }

    public abstract void onInteract(ObjectManager player, Player player1, Consumer<List<Touchable>> callback);

    public abstract void update();

    public abstract void draw(Graphics g, int xLvlOffset);
}
