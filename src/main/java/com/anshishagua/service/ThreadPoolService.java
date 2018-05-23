package com.anshishagua.service;

import com.anshishagua.compute.Task;
import com.anshishagua.compute.TaskExecution;
import com.anshishagua.object.SQLGenerateResult;
import com.anshishagua.object.SQLTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * User: lixiao
 * Date: 2018/5/11
 * Time: 上午10:36
 */

@Service
public class ThreadPoolService {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadPoolService.class);
    public static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors() + 1;

    private ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public void submit(TaskExecution taskExecution) {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;

        LOG.info("Thread pool active count: {}, pool size: {}, queue size: {}",
                threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getPoolSize(),
                threadPoolExecutor.getQueue().size());

        executorService.submit(new SQLTask(taskExecution));
    }

    public void submit(Task task, LocalDateTime scheduledExecutionTime, SQLGenerateResult sqlGenerateResult) {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;

        LOG.info("Thread pool active count: {}, pool size: {}, queue size: {}",
                threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getPoolSize(),
                threadPoolExecutor.getQueue().size());

        //executorService.submit(null);
    }
}