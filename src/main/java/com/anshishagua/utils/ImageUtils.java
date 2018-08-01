package com.anshishagua.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * User: lixiao
 * Date: 2018/7/24
 * Time: 下午2:17
 */

public class ImageUtils {
    public static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static void resize(File sourceImageFile, String destImageFile, int width, int height) throws IOException {
        resize(sourceImageFile.getAbsolutePath(), destImageFile, width, height);
    }

    public static void resize(String destImageFile, InputStream inputStream, int width, int height) throws IOException {
        String extension = destImageFile.substring(destImageFile.lastIndexOf(".") + 1);

        BufferedImage src = ImageIO.read(inputStream);

        Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        ImageIO.write(bufferedImage, extension, new File(destImageFile));
    }

    public static void resize(String sourceImageFile, String destImageFile, int width, int height) throws IOException {
        String extension = sourceImageFile.substring(sourceImageFile.lastIndexOf(".") + 1);

        BufferedImage src = ImageIO.read(new File(sourceImageFile));

        Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        ImageIO.write(bufferedImage, extension, new File(destImageFile));
    }

    public static void main(String [] args) throws IOException {
        String source = "/Users/lixiao/Desktop/center.png";
        String dest = "/Users/lixiao/Desktop/center2.png";

        resize(source, dest, 100, 100);
    }
}