package com.anshishagua.utils;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFPivotCache;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: lixiao
 * Date: 2018/7/24
 * Time: 下午2:17
 */

public class ExcelProcessingUtils {
    public static List<File> getImages(String dir) {
        File file = new File(dir);

        if (!file.exists()) {
            return Collections.emptyList();
        }

        List<File> result = new ArrayList<>();

        for (File f : file.listFiles()) {
            result.add(f);
        }

        return result;
    }

    public static void processExcels() throws IOException {
        String dataDir = "/Users/lixiao/Documents/一三四八数据/";
        String imageDir = "/Users/lixiao/Documents/一三四八图片处理/";
        String destFile = "/Users/lixiao/Documents/六、人居环境整治和老旧房改造项目前后对比图.xlsx";

        File file = new File(dataDir);

        for (File group : file.listFiles()) {
            String groupName = group.getName();

            if (groupName.startsWith(".")) {
                continue;
            }

            System.out.println(groupName);
            String personName = null;

            for (File name : group.listFiles()) {
                if (name.getName().startsWith(".")) {
                    continue;
                }

                personName = name.getName();

                List<File> imageFiles = getImages(imageDir + groupName + "/" + personName);

                if (imageFiles.isEmpty()) {
                    System.out.println(personName + " not found");
                    continue;
                }

                File excelFile = null;

                for (File dataFile : name.listFiles()) {
                    if (dataFile.getName().equals("六、人居环境整治和老旧房改造项目前后对比图.xlsx")) {
                        excelFile = dataFile;
                        break;
                    }
                }

                InputStream in = new FileInputStream(excelFile);
                Workbook workbook = new XSSFWorkbook(in);

                Sheet sheet = workbook.getSheetAt(0);

                Drawing patriarch = sheet.createDrawingPatriarch();

                int col = 1;
                int row = 3;
                int count = 0;

                int maxSize = 24;
                imageFiles = imageFiles.subList(0, Math.min(imageFiles.size(), maxSize));

                for (File f : imageFiles) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    BufferedImage bufferedImage = ImageIO.read(f);
                    String extension = ImageUtils.getFileExtension(f.getAbsolutePath());

                    ImageIO.write(bufferedImage, extension, byteArrayOutputStream);

                    row = count / 6;
                    col = count % 6 + 1;

                    if (row < 2) {
                        row += 3;
                    } else {
                        row += 4;
                    }

                    XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) col, row, (short) col + 1, row + 1);
                    patriarch.createPicture(anchor, workbook.addPicture(byteArrayOutputStream.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

                    ++count;
                }

                OutputStream fileOutputStream = new FileOutputStream(destFile);
                workbook.write(fileOutputStream);
                fileOutputStream.close();

                String command = "mv " + destFile + " " + dataDir + groupName + "/" + personName;
                System.out.println(command);
                Process p = Runtime.getRuntime().exec(command);
            }
        }
    }

    public static void processImages() throws IOException {
        String imageDir = "/Users/lixiao/Documents/一三四八图片/";

        String destImageDir = "/Users/lixiao/Documents/一三四八图片处理/";
        File file = new File(imageDir);

        for (File group : file.listFiles()) {
            String groupName = group.getName();

            System.out.println(groupName);

            if (groupName.startsWith(".")) {
                continue;
            }

            for (File name : group.listFiles()) {
                System.out.println(name.getName());

                if (name.getName().startsWith(".")) {
                    continue;
                }

                IOUtils.createDir(destImageDir + groupName + "/" + name.getName());

                int i = 0;

                for (File imageFile : name.listFiles()) {
                    String extension = ImageUtils.getFileExtension(imageFile.getAbsolutePath());
                    ImageUtils.resize(imageFile.getAbsolutePath(),
                            destImageDir + groupName + "/" + name.getName() + "/" + i + "." + extension, 300, 200);
                    ++i;
                }
            }
        }
    }

    public static void main(String [] args) throws IOException {
        String dataDir = "/Users/lixiao/Documents/一三四八数据/";

        //processImages();

        processExcels();
    }

    public static void main3(String [] args) throws IOException {
        String dir = "/Users/lixiao/Desktop/王家友/照片/";
        String destDir = "/Users/lixiao/Desktop/王家友/处理后/";

        File file = new File(dir);

        /*
        int i = 0;
        for (File imageFile : file.listFiles()) {
           String sourceFile = imageFile.getAbsolutePath();
           String extension = ImageUtils.getFileExtension(sourceFile);
           String destFile = destDir + i + "." + extension;

           System.out.println(sourceFile);
           System.out.println(imageFile.isDirectory());

           ImageUtils.resize(sourceFile, destFile, 300, 200);
           ++i;
        }
        */

        file = new File("/Users/lixiao/Desktop/王家友/");

        String excelFile = null;

        for (File f : file.listFiles()) {
            if (f.isFile() && f.getName().endsWith("xlsx")) {
                excelFile = f.getAbsolutePath();
                break;
            }
        }

        if (excelFile == null) {
            System.out.println("No excel file, exit");

            return;
        }

        Workbook workbook = null;
        InputStream in = new FileInputStream(excelFile);
        if (excelFile.endsWith("xls")) {
            workbook = new HSSFWorkbook(in);
        } else if (excelFile.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(in);
        }

        Sheet sheet = workbook.getSheetAt(0);

        System.out.println(sheet.getLastRowNum());

        Drawing patriarch = sheet.createDrawingPatriarch();

        file = new File(destDir);
        int size = file.listFiles().length;

        int col = 1;
        int row = 3;
        int count = 0;

        for (File f : file.listFiles()) {
            //++count;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BufferedImage bufferedImage = ImageIO.read(f);
            String extension = ImageUtils.getFileExtension(f.getAbsolutePath());

            ImageIO.write(bufferedImage, extension, byteArrayOutputStream);

            row = count / 6;
            col = count % 6 + 1;

            if (row < 2) {
                row += 3;
            } else {
                row += 4;
            }

            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) col, row, (short) col + 1, row + 1);
            patriarch.createPicture(anchor, workbook.addPicture(byteArrayOutputStream.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

            /*
            if (count == size / 2) {
                ++row;
                col = 1;
            } else {
                ++col;
            }
            */

            ++count;
        }

        OutputStream fileOutputStream = new FileOutputStream("/Users/lixiao/Desktop/123.xlsx");
        workbook.write(fileOutputStream);
    }

    public static void main2(String [] args) {
        FileOutputStream fileOutputStream = null;
        BufferedImage bufferedImage = null;//图片

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bufferedImage = ImageIO.read(new File("/Users/lixiao/Desktop/husky.jpg"));

            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("out put excel");
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 10, 1, (short) 11, 25);
            patriarch.createPicture(anchor, workbook.addPicture(byteArrayOutputStream.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
            fileOutputStream = new FileOutputStream("/Users/lixiao/Desktop/123.xls");
            workbook.write(fileOutputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            IOUtils.safeClose(fileOutputStream);
        }
    }
}
