package com.beanframework.template.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class TemplateJob implements Job {

	protected static final Logger LOGGER = LoggerFactory.getLogger(TemplateJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
	}
}
