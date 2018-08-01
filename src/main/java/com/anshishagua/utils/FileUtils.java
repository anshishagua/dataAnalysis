package com.anshishagua.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * User: lixiao
 * Date: 2018/8/1
 * Time: 下午4:21
 */

public class FileUtils {
    public static String getExtension(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return null;
        }

        return fileName.substring(fileName.lastIndexOf("\\.") + 1);
    }
}