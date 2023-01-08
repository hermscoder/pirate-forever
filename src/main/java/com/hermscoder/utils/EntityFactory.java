package com.hermscoder.utils;

import com.hermscoder.entities.Crabby;
import com.hermscoder.entities.Entity;
import com.hermscoder.entities.Shark;

public class EntityFactory {
    public static Entity newEntity(int x, int y, int objectType) {
        switch (objectType) {
            case Constants.CrabbyConstants.CRABBY:
                return new Crabby(x, y);
            case Constants.SharkConstants.SHARK:
                return new Shark(x, y);
            default:
                throw new RuntimeException("Object of type: " + objectType + " not configured in object factory.");
        }
    }
}
