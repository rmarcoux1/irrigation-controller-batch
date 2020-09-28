package com.projects.irrigationcontrollerbatch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author Ryan G. Marcoux
 */
@Component
public class MyJobListener implements JobExecutionListener {

    private JobExecution isActive;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        synchronized(jobExecution) {
            if(isActive!=null && isActive.isRunning()) {
                jobExecution.stop();
            } else {
                isActive=jobExecution;
            }
        }
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        synchronized(jobExecution) {
            if(jobExecution==isActive) {
                isActive=null;
            }
        }
    }
}
