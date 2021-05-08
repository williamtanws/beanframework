package com.beanframework.trainingcore.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.cronjob.service.QuartzManager;

@Component
@DisallowConcurrentExecution
public class TrainingNotificationJob implements Job {

	protected static final Logger LOGGER = LoggerFactory.getLogger(TrainingNotificationJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		try {

			context.put(QuartzManager.CRONJOB_NOTIFICATION, Boolean.TRUE);
		} catch (Exception e) {
			context.put(QuartzManager.CRONJOB_NOTIFICATION, Boolean.TRUE);
			LOGGER.error(e.getMessage(), e);
			throw new JobExecutionException(e.getMessage(), e);
		}
	}

}
