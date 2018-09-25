package com.beanframework.logging.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.logging.service.LoggingService;

@Component
@DisallowConcurrentExecution
public class LoggingCleanLimitedLogJob implements Job {

	@Autowired
	private LoggingService logService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		int limited = context.getMergedJobDataMap().getInt("limit");
		int deletedLogs = logService.deleteByLimit(limited);
		context.setResult("Cleaned "+deletedLogs+" log records");
	}
}
