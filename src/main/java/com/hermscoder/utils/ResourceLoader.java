package com.hermscoder.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ResourceLoader {

    public static BufferedImage getImage(String fileName) {
        BufferedImage image = null;
        try {
            InputStream[] allFilesFromFolder = getAllFilesFromFolderJAR(fileName);
            if (allFilesFromFolder == null || allFilesFromFolder.length == 0) {
                allFilesFromFolder = getAllFilesFromFolderFileSystem(fileName);
            }
            image = ImageIO.read(allFilesFromFolder[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static Image getGifImage(String fileName) {
        Image image = null;
        try {
            InputStream[] allFilesFromFolder = getAllFilesFromFolderJAR(fileName);
            if (allFilesFromFolder == null || allFilesFromFolder.length == 0) {
                allFilesFromFolder = getAllFilesFromFolderFileSystem(fileName);
            }
            image = new ImageIcon(allFilesFromFolder[0].readAllBytes()).getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static BufferedImage[] getAllImagesFromFolder(String folderName) {
        BufferedImage[] images = new BufferedImage[0];
        // read all files from a resources folder
        try {
            InputStream[] allFilesFromFolder = getAllFilesFromFolderJAR(folderName);

            if (allFilesFromFolder == null || allFilesFromFolder.length == 0) {
                allFilesFromFolder = getAllFilesFromFolderFileSystem(folderName);
            }
            images = new BufferedImage[allFilesFromFolder.length];

            for (int i = 0; i < allFilesFromFolder.length; i++) {
                images[i] = ImageIO.read(allFilesFromFolder[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return images;
    }

    private static InputStream[] getAllFilesFromFolderJAR(String folderName) {
        InputStream[] files = new InputStream[0];
        // read all files from a resources folder
        // get paths from src/main/resources/json
        List<Path> pathsFromResource = getAllFilePathsFromFolderJAR(folderName);

        files = new InputStream[pathsFromResource.size()];

        Path[] paths;
        if (pathsFromResource.size() == 1) {
            paths = new Path[]{pathsFromResource.get(0)};
        } else {
            paths = new Path[pathsFromResource.size()];
            for (int i = 0; i < pathsFromResource.size(); i++) {

                for (int j = 0; j < pathsFromResource.size(); j++) {

                    if (pathsFromResource.get(j).toString().endsWith("/" + (i + 1) + ".png")) {
                        paths[i] = pathsFromResource.get(j);
                    }
                }
            }
        }

        for (int i = 0; i < paths.length; i++) {
            String filePathInJAR = paths[i].toString();
            // Windows will returns /json/file1.json, cut the first /
            // the correct path should be json/file1.json
            if (filePathInJAR.startsWith("/")) {
                filePathInJAR = filePathInJAR.substring(1, filePathInJAR.length());
            }

            // read a file from resource folder
            files[i] = getFileFromResourceAsStream(filePathInJAR);
        }
        return files;
    }


    private static InputStream[] getAllFilesFromFolderFileSystem(String folderName) {
        InputStream[] result;

        URL url = LoadSave.class.getClassLoader().getResource(folderName);
        File file = null;
        try {
            file = new File(url.toURI());

            File[] files;
            if (file.isFile()) {
                result = new InputStream[]{file.toURI().toURL().openStream()};
            } else {
                files = file.listFiles();
                result = new InputStream[files.length];

                for (int i = 0; i < result.length; i++) {
                    for (int j = 0; j < files.length; j++) {
                        if (files[j].getName().equals((i + 1) + ".png")) {
                            result[j] = files[i].toURI().toURL().openStream();
                        }
                    }
                }
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to find level directory");
        } catch (IOException e) {
            throw new RuntimeException("Failed to find level image");
        }

        return result;
    }

    private static InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = ResourceLoader.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    private static List<Path> getAllFilePathsFromFolderJAR(String folder) {
        List<Path> result = new ArrayList<>();
        try {
            // get path of the current running JAR
            String jarPath = ResourceLoader.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()
                    .getPath();

            // file walks JAR
            URI uri = URI.create("jar:file:" + jarPath.replace(" ", "%20"));
            System.out.println("jar:file:" + jarPath);
            try (FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
                result = Files.walk(fs.getPath(folder))
                        .filter(Files::isRegularFile)
                        .collect(Collectors.toList());
            }
        } catch (ProviderNotFoundException e) {
            //ignore, because it means that we are not running application from a JAR
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    // print a file
    private static BufferedImage getBufferedImage(File file) {

        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;

    }
}
