package com.hermscoder.data;

import com.hermscoder.objects.MapFragment;
import com.hermscoder.utils.Constants;

import java.util.*;

public class PlayerStats {
    private int lastLevelIndexCompleted = 0;
    private boolean finishedLevelZero = false;

    private Set<MapFragment> mapFragmentListCollected = new TreeSet<>();
    private final Map<Integer, Long[]> levelTimes = new HashMap<>();

    public PlayerStats() {
        for (var i = 0; i < Constants.NUMBER_OF_LEVELS; i++) {
            levelTimes.put(i, new Long[] {0L, 0L, 0L, 0L});
        }
    }
    public void addLevelTime(int currentLevelIndex, long timeInMillis) {
        long temp;
        Long[] times = levelTimes.get(currentLevelIndex);
        for (var i = 0; i < times.length; i++) {
            if(times[i] <= timeInMillis) {
                temp = times[i];
                times[i] = timeInMillis;
                timeInMillis = temp;
            }

        }

    }

    public boolean isTimeARecord(int currentLevelIndex, long timeInMillis) {
        for (Long timeRecord : levelTimes.get(currentLevelIndex)) {
            if(timeInMillis > timeRecord) {
                return true;
            }
        }
        return false;
    }

    public void setLevelAsCompleted(int currentLevelIndex) {
        if(currentLevelIndex == 0)
            finishedLevelZero = true;

        if(currentLevelIndex > lastLevelIndexCompleted) {
            lastLevelIndexCompleted = currentLevelIndex;
        }
    }

    public int getLastLevelIndexCompleted() {
        return lastLevelIndexCompleted;
    }

    public Set<MapFragment> getMapFragmentListCollected() {
        return mapFragmentListCollected;
    }

    public void addMapFragmentsToCollection(List<MapFragment> mapFragmentList) {
        mapFragmentListCollected.addAll(mapFragmentList);
    }

    public Long[] getLevelTimes(int levelIndex) {
        return levelTimes.get(levelIndex);
    }

    public boolean finishedLevelZero() {
        return finishedLevelZero;
    }

    public void setFinishedLevelZero(boolean finishedLevelZero) {
        this.finishedLevelZero = finishedLevelZero;
    }
}
