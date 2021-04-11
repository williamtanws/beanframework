package com.beanframework.core.task;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class SendEmailService implements JavaDelegate {
    public void execute(DelegateExecution execution) {
        System.out.println("Send email.");
    }
}