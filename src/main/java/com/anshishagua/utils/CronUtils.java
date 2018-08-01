package com.anshishagua.utils;

import org.springframework.scheduling.support.CronSequenceGenerator;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/5/21
 * Time: 上午11:55
 */

public class CronUtils {

    public static void main(String [] args) throws Exception {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t1 run");
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t2 run");
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t3 run");
            }
        });

        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();
        t3.join();
    }
}