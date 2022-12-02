package com.hermscoder.utils;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.awt.*;

public class LevelDataXmlHandlerSax extends DefaultHandler {

    private final int tileHeight, tileWidth, lvlIndex;
    private Color[][] lvlData;
    private boolean isTileData = false;
    private boolean firstTag = true;
    private Integer red, green, blue;

    public LevelDataXmlHandlerSax(int tileHeight, int tileWidth, int lvlIndex) {
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.lvlIndex = lvlIndex;
    }

    @Override
    public void startDocument() {
//        System.out.println("Start Document");
    }

    @Override
    public void endDocument() {
//        System.out.println("End Document");
    }

    @Override
    public void startElement(
            String uri,
            String localName,
            String qName,
            Attributes attributes) {

        if (qName.equalsIgnoreCase("object")) {
            if (firstTag) {
                initializeProperties(attributes);
                return;
            }
            // get tag's attribute by name
            String redStr = attributes.getValue("red");
            if (redStr != null) {
                String blueStr = attributes.getValue("blue");
                String greenStr = attributes.getValue("green");

                isTileData = true;
                this.red = Integer.valueOf(redStr);
                this.green = Integer.valueOf(greenStr);
                this.blue = Integer.valueOf(blueStr);
            }

        }

        if (qName.equalsIgnoreCase("mxGeometry")) {
            if (isTileData) {
                Double yDouble = Double.parseDouble(getOrDefault(attributes.getValue("y"), "0"));
                Double xDouble = Double.parseDouble(getOrDefault(attributes.getValue("x"), "0"));
                int x = (xDouble.intValue() / tileWidth);
                int y = (yDouble.intValue() / tileHeight);
                lvlData[y][x] = new Color(red, green, blue);
            }
        }

    }

    private void initializeProperties(Attributes attributes) {
        String height = attributes.getValue("height");
        String width = attributes.getValue("width");
        lvlData = new Color[Integer.valueOf(height) / tileHeight][Integer.valueOf(width) / tileWidth];
        firstTag = false;
    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName) {

        //end of tag
        if (qName.equalsIgnoreCase("object")) {
            isTileData = false;
            this.red = null;
            this.green = null;
            this.blue = null;
        }

    }

    private String getOrDefault(String value, String defaultValue) {
        if (value == null)
            return defaultValue;
        return value;
    }

    public Color[][] getLvlData() {
        return lvlData;
    }
}
