package com.hermscoder.audio;

import com.hermscoder.utils.Constants;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.logging.Logger;

public class AudioPlayer {

    private static final Logger logger = Logger.getLogger(AudioPlayer.class.getName());

    private Clip[] songs, effects;

    private Song currentSong = Song.MENU_1;
    private float volume = 1f;
    private boolean songMute, effectMute;
    private Random rand = new Random();

    public AudioPlayer() {
        loadSongs();
        loadEffects();
        playSong(currentSong);
        toggleSongMute();
    }

    private void loadSongs() {
        songs = new Clip[Song.values().length];
        for (Song song : Song.values()) {
            songs[song.getIndex()] = getClip(song.getFileName());
        }
    }

    private void loadEffects() {
        effects = new Clip[SoundEffect.values().length];
        for (SoundEffect soundEffect : SoundEffect.values()) {
            effects[soundEffect.getIndex()] = getClip(soundEffect.getFileName());
        }
        updateEffectVolume();
    }

    private Clip getClip(String fileName) {
        URL url = getClass().getResource(Constants.AUDIO_FOLDER + "/" + fileName + ".wav");

        AudioInputStream audioInputStream;

        try {
            audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip c = AudioSystem.getClip();
            c.open(audioInputStream);
            return c;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            logger.severe("Failed to load clip: " + fileName);
        }
        return null;
    }

    public void toggleSongMute() {
        this.songMute = !songMute;
        for (Clip song : songs) {
            BooleanControl booleanControl = (BooleanControl) song.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }
    }

    public void toggleEffectMute() {
        this.effectMute = !effectMute;
        for (Clip effect : effects) {
            BooleanControl booleanControl = (BooleanControl) effect.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectMute);
        }
        if (!effectMute)
            playEffect(SoundEffect.JUMP);
    }

    public void setVolume(float volume) {
        this.volume = volume;
        updateSongVolume();
        updateEffectVolume();
    }

    public void stopSong() {
        if (songs[currentSong.getIndex()].isActive())
            songs[currentSong.getIndex()].stop();
    }

    public void setLevelSong(int levelIndex) {
        if ((levelIndex % 2) == 0)
            playSong(Song.LEVEL_2);
        else
            playSong(Song.LEVEL_1);
    }

    public void levelCompleted() {
        stopSong();
        playEffect(SoundEffect.LEVEL_COMPLETED);
    }

    public void playAttackSound() {
        int start = SoundEffect.ATTACK_1.getIndex();
        start += rand.nextInt(3);
        playEffect(SoundEffect.values()[start]);

    }

    public void playEffect(SoundEffect effect) {
        effects[effect.getIndex()].setMicrosecondPosition(0L);
        effects[effect.getIndex()].start();
    }

    public void playSong(Song newSong) {
        stopSong();

        currentSong = newSong;
        updateSongVolume();
        songs[currentSong.getIndex()].setMicrosecondPosition(0L);
        songs[currentSong.getIndex()].loop(Clip.LOOP_CONTINUOUSLY);
    }

    private void updateSongVolume() {
        FloatControl gainControl = (FloatControl) songs[currentSong.getIndex()].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();
        gainControl.setValue(gain);
    }

    private void updateEffectVolume() {
        for (Clip effect : effects) {
            FloatControl gainControl = (FloatControl) effect.getControl(FloatControl.Type.MASTER_GAIN);

            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }

}
