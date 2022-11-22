package com.hermscoder.utils;

import com.hermscoder.entities.Crabby;
import com.hermscoder.main.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LoadSave {

    public static BufferedImage getSpriteAtlas(String fileName) {
        BufferedImage img = null;

        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return img;
    }

    public static Image getGifImage(String fileName) {
        return new ImageIcon(LoadSave.class.getResource("/" + fileName)).getImage();
    }

    public static ArrayList<Crabby> getCrabs(int levelNumber) {
        BufferedImage img = LoadSave.getSpriteAtlas("level_" + levelNumber + "_data_long.png");
        ArrayList<Crabby> list = new ArrayList<>();
        for(int j = 0; j < img.getHeight(); j ++) {
            for(int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if(value == Constants.EnemyConstants.CRABBY)
                    list.add(new Crabby(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
            }
        }

        return list;
    }

    public static int[][] getLevelData(int levelNumber) {
        BufferedImage img = LoadSave.getSpriteAtlas("level_" + levelNumber + "_data_long.png");
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

        for(int j = 0; j < img.getHeight(); j ++) {
            for(int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if(value >= 48)
                    value = 0;
                lvlData[j][i] = value;
            }
        }
        return lvlData;
    }
}
