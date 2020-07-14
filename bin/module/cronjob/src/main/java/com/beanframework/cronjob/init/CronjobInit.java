package com.beanframework.cronjob.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.beanframework.cronjob.CronjobConstants;
import com.beanframework.cronjob.service.CronjobManagerService;

@Component
public class CronjobInit implements ApplicationRunner {

	protected static final Logger LOGGER = LoggerFactory.getLogger(CronjobInit.class);

	@Autowired
	private CronjobManagerService cronjobManagerService;

	@Value(CronjobConstants.QUARTZ_TASK_ENABLED)
	private boolean enableQuartzTasks;

	@Override
	public void run(ApplicationArguments args) throws Exception {
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
