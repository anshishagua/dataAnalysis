package com.anshishagua.job;

import java.util.Map;

/**
 * User: lixiao
 * Date: 2018/6/14
 * Time: 下午5:16
 */

public interface JobHandler {
    void prepare(JobConfiguration jobConfiguration);
    <T> JobResult<T> execute(Map<String, Object> params) throws JobExecutionException;
    void handleExecutionException();
    void cleanup();
    void addUpStreamJob(JobConfiguration jobConfiguration);
}