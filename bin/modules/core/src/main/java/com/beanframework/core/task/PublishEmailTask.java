package com.beanframework.core.task;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class PublishEmailTask implements JavaDelegate {
    public void execute(DelegateExecution execution) {
        System.out.println("Generate email.");
        execution.setTransientVariable("send", true);
    }
}
