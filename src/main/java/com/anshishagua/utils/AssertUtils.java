package com.anshishagua.utils;

import java.util.Collection;

/**
 * User: lixiao
 * Date: 2018/5/12
 * Time: 上午11:20
 */

public class AssertUtils {
    public static void collectionNotEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException("Collection is empty");
        }
    }
}