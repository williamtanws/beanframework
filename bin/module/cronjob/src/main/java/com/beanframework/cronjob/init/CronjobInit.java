package com.beanframework.cronjob.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.beanframework.cronjob.CronjobConstants;
import com.beanframework.cronjob.service.CronjobManagerService;

@Component
public class CronjobInit implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private CronjobManagerService cronjobManagerService;

	@Value(CronjobConstants.QUARTZ_TASKS_ENABLED)
	private boolean enableQuartzTasks;

	protected static final Logger LOGGER = LoggerFactory.getLogger(CronjobInit.class);

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		try {
			LOGGER.info("Clearing all cronjobs scheduler instances.");
			cronjobManagerService.clearAllScheduler();
			if (enableQuartzTasks) {
				cronjobManagerService.initCronJob();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

}
