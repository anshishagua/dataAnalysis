package com.anshishagua.utils;

/**
 * User: lixiao
 * Date: 2018/5/19
 * Time: 下午1:52
 */

public class CharacterUtils {
    public static boolean isChinese(char ch) {
        return ch >= 0x4E00 && ch <= 0x9FA5;
    }

    public static boolean isDigit(char ch) {
        return true;
    }

    public static void main(String [] args) {
        System.out.println(isChinese('你'));
    }
}
