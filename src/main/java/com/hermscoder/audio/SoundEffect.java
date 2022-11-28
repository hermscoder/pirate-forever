package com.hermscoder.audio;

public enum SoundEffect {
    DIE(0, "die"),
    JUMP(1, "jump"),
    GAME_OVER(2, "game_over"),
    LEVEL_COMPLETED(3, "level_completed"),
    ATTACK_1(4, "attack_1"),
    ATTACK_2(5, "attack_2"),
    ATTACK_3(6, "attack_3");

    private final int index;
    private final String fileName;

    SoundEffect(int index, String fileName) {
        this.index = index;
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public int getIndex() {
        return index;
    }
}
