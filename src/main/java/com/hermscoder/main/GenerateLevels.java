package com.hermscoder.main;

import com.hermscoder.utils.Constants;
import com.hermscoder.utils.LevelDataXmlHandlerSax;
import com.hermscoder.utils.LoadSave;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class GenerateLevels {


    public static void main(String[] args) {
        generateAllLevelData(args != null && args.length > 0 ? args[0] : null);
    }

    public static void generateAllLevelData(String specificLevel) {
        boolean generateSpecificLevel = specificLevel != null;

        URL url = LoadSave.class.getResource(Constants.LEVEL_DESIGN_FOLDER);
        File file = null;
        try {
            file = new File(url.toURI());
            File[] files = file.listFiles();
            var images = new BufferedImage[files.length];

            String fileName;
            for (int i = 0; i < images.length; i++) {
                int level = i + 1;
                fileName = (specificLevel == null ? level : specificLevel) + ".drawio";
                if (files[i].getName().equals(fileName)) {
                    Color[][] document = getDocument(files[i], i);
                    generateLevelDataImage(document, level);

                    if(generateSpecificLevel)
                        break;
                }
            }
//            return imgs;
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to find level directory");
        }
    }

    private static Color[][] getDocument(File file, int levelIndex) {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {
            SAXParser saxParser = factory.newSAXParser();
            LevelDataXmlHandlerSax handler = new LevelDataXmlHandlerSax(Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE, levelIndex);
            saxParser.parse(file, handler);
            return handler.getLvlData();

        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return new Color[0][];
    }

    public static void generateLevelDataImage(Color[][] lvlData, int level) {
        BufferedImage image = new BufferedImage(lvlData[0].length, lvlData.length, BufferedImage.TYPE_INT_ARGB);

        Color tileColor;
        for (int y = 0; y < lvlData.length; y++) {
            for (int x = 0; x < lvlData[y].length; x++) {
                tileColor = lvlData[y][x] != null ? lvlData[y][x] : new Color(11, 0, 0);
                image.setRGB(x, y, tileColor.getRGB());
            }
        }

        try {
            var outputFile = new File("src/main/resources/levels/" + level + ".png");
            outputFile.mkdirs();

            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
