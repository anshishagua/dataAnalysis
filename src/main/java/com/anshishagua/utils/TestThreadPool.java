package com.anshishagua.utils;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: lixiao
 * Date: 2018/5/27
 * Time: 下午10:41
 */

public class TestThreadPool {
    private static int coreSize = 10;
    private static int maxSize = 20;
    private static long keepAliveTime = 10;

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    private static class Task implements Callable<Double> {
        @Override
        public Double call() {
            try {
                System.out.println("Running");
                Thread.sleep(10000);
            } catch (Exception ex) {
                ex.printStackTrace();

                if (ex instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
                return null;

            }

            System.out.println("hell");
            return new Random().nextGaussian();
        }
    }

    public static void main(String [] args) {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(100);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(coreSize, maxSize,
                keepAliveTime, TimeUnit.SECONDS, queue, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("Thread-" + atomicInteger.decrementAndGet());
                thread.setPriority(Thread.NORM_PRIORITY);
                thread.setDaemon(false);

                return thread;
            }
        }, new ThreadPoolExecutor.AbortPolicy());

        Future<Double> future = (Future<Double>) executor.submit(new FutureTask<Double>(new Task()));

        System.out.println("Submitted");

        //future.cancel(true);


        executor.shutdownNow();

        System.out.println(future.isCancelled());

    }
}
