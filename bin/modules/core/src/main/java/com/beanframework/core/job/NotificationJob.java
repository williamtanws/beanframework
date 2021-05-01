package com.beanframework.core.job;

import org.joda.time.DateTime;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.core.facade.NotificationFacade;
import com.beanframework.cronjob.service.QuartzManager;

@Component
@DisallowConcurrentExecution
public class NotificationJob implements Job {

	protected static final Logger LOGGER = LoggerFactory.getLogger(NotificationJob.class);

	public static final String HOURS = "hours";

	@Autowired
	private NotificationFacade notificationFacade;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		try {
			int count = 0;
			if (context.getMergedJobDataMap().get(HOURS) == null || context.getMergedJobDataMap().get(HOURS) == "0") {

				count = notificationFacade.removeAllNotification();

				context.setResult("Removed all " + count + " old notifications");

			} else {

				String hours = (String) context.getMergedJobDataMap().get(HOURS);

				DateTime date = new DateTime();
				date.minusHours(Integer.valueOf(hours));

				count = notificationFacade.removeOldNotificationByToDate(date.toDate());

				context.setResult("Removed " + count + " old notifications more than " + hours + " hours");
			}
			context.put(QuartzManager.CRONJOB_NOTIFICATION, Boolean.TRUE);

		} catch (Exception e) {
			context.put(QuartzManager.CRONJOB_NOTIFICATION, Boolean.TRUE);
			LOGGER.error(e.getMessage(), e);
			throw new JobExecutionException(e.getMessage(), e);
		}
	}

}
