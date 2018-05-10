package com.anshishagua.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * User: lixiao
 * Date: 2018/5/4
 * Time: 下午11:08
 */

public class CollectionUtils {
    public static boolean hasMultiplyElement(Collection<?> collection) {
        return collection != null && collection.size() >= 2;
    }

    public static boolean hasSingleElement(Iterable<?> collection) {
        if (collection == null) {
            return false;
        }

        Iterator<?> iterator = collection.iterator();

        return iterator.hasNext() && iterator.next() != null && !iterator.hasNext();
    }

    public static void main(String [] args) {
        System.out.println(hasSingleElement(Arrays.asList(1)));

        Set<String> set = new HashSet<>();
        set.add("aaa");

        System.out.println(hasSingleElement(set));
    }
}
