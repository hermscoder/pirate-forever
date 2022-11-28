package com.hermscoder.ui;

import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static com.hermscoder.utils.Sprite.SoundButtonsSpriteAtlas;

public class SoundButton extends PauseButton {
    private BufferedImage[][] soundImgs;
    private boolean muted;
    private int rowIndex, columnIndex;
    private final Runnable onClickAction;

    public SoundButton(int x, int y, int width, int height, Runnable onClickAction) {
        super(x, y, width, height);
        this.onClickAction = onClickAction;
        loadSoundImages();
    }

    private void loadSoundImages() {
        BufferedImage temp = LoadSave.getSpriteAtlas(SoundButtonsSpriteAtlas.getFilename());
        soundImgs = new BufferedImage[SoundButtonsSpriteAtlas.getHeightInSprites()][SoundButtonsSpriteAtlas.getWidthInSprites()];
        for (int j = 0; j < soundImgs.length; j++) {
            for (int i = 0; i < soundImgs[j].length; i++) {
                soundImgs[j][i] = temp.getSubimage(
                        i * SoundButtonsSpriteAtlas.getTileWidth(),
                        j * SoundButtonsSpriteAtlas.getTileHeight(),
                        SoundButtonsSpriteAtlas.getTileWidth(),
                        SoundButtonsSpriteAtlas.getTileHeight());
            }
        }
    }

    @Override
    public void update() {
        if (muted)
            rowIndex = 1;
        else
            rowIndex = 0;

        columnIndex = 0;
        if (mouseOver)
            columnIndex = 1;
        if (mousePressed)
            columnIndex = 2;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(soundImgs[rowIndex][columnIndex], x, y, width, height, null);
    }

    @Override
    public void onClickAction(MouseEvent e) {
        if (mousePressed) {
            muted = !muted;
            onClickAction.run();
        }
    }
}
