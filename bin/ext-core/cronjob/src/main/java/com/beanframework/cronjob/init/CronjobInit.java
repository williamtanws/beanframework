package com.beanframework.cronjob.init;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.beanframework.cronjob.service.CronjobManagerService;

@Component
public class CronjobInit implements ApplicationListener<ApplicationReadyEvent> {
	
	@Autowired
	private CronjobManagerService cronjobManagerService;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		try {
			logger.info("Clear all cronjob scheduler instances.");
			cronjobManagerService.clearAllScheduler();
			logger.info("Initialize cronjob based on job exists in database.");
			cronjobManagerService.initCronJob();
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
