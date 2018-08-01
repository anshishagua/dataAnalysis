package com.anshishagua.utils;

import scala.util.control.Exception;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * User: lixiao
 * Date: 2018/6/19
 * Time: 上午10:41
 */

public class IOUtils {
    public static void moveFile(String src, String dest) throws IOException {

    }

    public static void createDir(String dir) throws IOException {
        File file = new File(dir);

        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void writeToFile(String file, InputStream inputStream) throws IOException {
        byte [] bytes = new byte[1024];
        int len = 0;

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
             FileOutputStream outputStream = new FileOutputStream(file)) {
            while ((len = bufferedInputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, len);
            }
        }
    }

    public static long writeToFile(String content, String path) throws IOException {
        long bytes = 0;

        try (FileOutputStream outputStream = new FileOutputStream(path);
             FileChannel fileChannel = outputStream.getChannel()) {

            ByteBuffer byteBuffer = ByteBuffer.wrap(content.getBytes(StandardCharsets.UTF_8));

            while (byteBuffer.hasRemaining()) {
                bytes += fileChannel.write(byteBuffer);
            }

            fileChannel.force(true);
        }

        return bytes;
    }

    public static void safeClose(Closeable closeable) {
        if (closeable == null) {
            return;
        }

        try {
            closeable.close();
        } catch (IOException ex) {

        }
    }

    public static void main(String [] args) throws IOException {
        long bytes = writeToFile("aaaaaaaaaaewrwrwer", "/tmp/a.txt");

        System.out.println(bytes);

        CharsetDecoder charsetDecoder;

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

        for (int i = 0; i < 10; ++i) {
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("dd");

                    try {
                        Thread.sleep(1000);
                    } catch (java.lang.Exception ex) {

                    }
                }
            });

            System.out.println(threadPoolExecutor);
        }

        System.out.println(threadPoolExecutor);

    }
}
