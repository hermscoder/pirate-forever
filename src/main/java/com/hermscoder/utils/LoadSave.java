package com.hermscoder.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;


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
        return ResourceLoader.getGifImage(fileName);
    }

    public static BufferedImage[] getAllLevels() {
        URL url = LoadSave.class.getResource(Constants.LEVELS_FOLDER);
        File file = null;
        try {
            file = new File(url.toURI());
            File[] files = file.listFiles();
            BufferedImage[] imgs = new BufferedImage[files.length];

            for (int i = 0; i < imgs.length; i++) {
                for (int j = 0; j < files.length; j++) {
                    if (files[j].getName().equals((i + 1) + ".png")) {
                        imgs[j] = ImageIO.read(files[i]);
                    }
                }
            }
            return imgs;
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to find level directory");
        } catch (IOException e) {
            throw new RuntimeException("Failed to find level image");
        }
    }


    public static BufferedImage[] getAllLevels2() {
        return ResourceLoader.getAllImagesFromFolder(Constants.LEVELS_FOLDER);
    }

}
