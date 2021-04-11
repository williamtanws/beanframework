package com.beanframework.core.task;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class VerifyEmailService implements JavaDelegate {
    public void execute(DelegateExecution execution) {
        System.out.println("Verify email.");
    }
}
