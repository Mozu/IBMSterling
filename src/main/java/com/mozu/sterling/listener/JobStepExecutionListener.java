package com.mozu.sterling.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Service;

@Service("jobStepExecutionListener")
public class JobStepExecutionListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution){
        Long jobExecutionId =stepExecution.getJobExecutionId();
        String jobName=stepExecution.getJobExecution().getJobInstance().getJobName();
        stepExecution.getExecutionContext().put("jobExecutionId", jobExecutionId);
        stepExecution.getExecutionContext().put("stepExecutionId", stepExecution.getId());
        stepExecution.getExecutionContext().put("jobName", jobName);
    }
 

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

}
