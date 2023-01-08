package com.hermscoder.utils;

import com.hermscoder.objects.*;

public class ObjectFactory {
    public static GameObject newGameObject(int x, int y, int objectType) {
        switch (objectType) {
            case ObjectConstants.BLUE_POTION, ObjectConstants.RED_POTION:
                return new Potion(x, y, objectType);
            case ObjectConstants.BOX, ObjectConstants.BARREL:
                return new Container(x, y, objectType);
            case ObjectConstants.KEY:
                return new Key(x, y, objectType);
            case ObjectConstants.SWORD, ObjectConstants.FIRE_SWORD:
                return new Sword(x, y, objectType);
            case ObjectConstants.SPIKE_TRAP:
                return new Spike(x, y, objectType);
            case ObjectConstants.CANNON_LEFT, ObjectConstants.CANNON_RIGHT:
                return new Cannon(x, y, objectType);
            default:
                throw new RuntimeException("Object of type: " + objectType + " not configured in object factory.");
        }
    }
}
