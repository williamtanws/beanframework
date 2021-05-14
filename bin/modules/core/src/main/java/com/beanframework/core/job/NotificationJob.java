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
  public static final String DAYS = "days";

  @Autowired
  private NotificationFacade notificationFacade;

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {

    try {
      int count = 0;
      if (context.getMergedJobDataMap().get(HOURS) == null
          && context.getMergedJobDataMap().get(DAYS) == null) {

        count = notificationFacade.removeAllNotification();

        context.setResult("Removed all " + count + " old notifications");

      } else {

        DateTime date = new DateTime();
        if (context.getMergedJobDataMap().get(HOURS) != null) {
          String hours = (String) context.getMergedJobDataMap().get(HOURS);
          date.minusHours(Integer.valueOf(hours));
          count = notificationFacade.removeOldNotificationByToDate(date.toDate());
          context
              .setResult("Removed " + count + " old notifications more than " + hours + " hour(s)");
        } else if (context.getMergedJobDataMap().get(DAYS) != null) {
          String days = (String) context.getMergedJobDataMap().get(DAYS);
          date.minusDays(Integer.valueOf(days));
          count = notificationFacade.removeOldNotificationByToDate(date.toDate());
          context
              .setResult("Removed " + count + " old notifications more than " + days + " day(s)");
        }

      }
      context.put(QuartzManager.CRONJOB_NOTIFICATION, Boolean.TRUE);

    } catch (Exception e) {
      context.put(QuartzManager.CRONJOB_NOTIFICATION, Boolean.TRUE);
      LOGGER.error(e.getMessage(), e);
      throw new JobExecutionException(e.getMessage(), e);
    }
  }

}
