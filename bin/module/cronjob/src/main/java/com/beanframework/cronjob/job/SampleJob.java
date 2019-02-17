package com.beanframework.cronjob.job;

import java.util.Map.Entry;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class SampleJob implements Job {

	protected static final Logger LOGGER = LoggerFactory.getLogger(SampleJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.info("Executing com.beanframework.job.SampleJob");

		StringBuilder result = new StringBuilder();
		result.append("This is sample job result. ");

		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		for (Entry<String, Object> entry : dataMap.entrySet()) {
			result.append("Job Data Name: " + entry.getKey() + ", Job Data Value" + entry.getValue() + ". ");
		}

		context.setResult(result.toString());

	}
}
