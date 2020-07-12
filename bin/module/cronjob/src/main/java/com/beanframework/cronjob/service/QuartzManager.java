package com.beanframework.cronjob.service;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobData;
import com.beanframework.cronjob.domain.CronjobEnum;

@Component
public class QuartzManager {

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	public static final String CRONJOB_UUID = "cronjobUuid";
	public static final String CRONJOB_ID = "cronjobId";

	@SuppressWarnings("unchecked")
	public void startOrUpdateJob(Cronjob job) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SchedulerException {

		Class<? extends Job> jobClass = null;

		String className = job.getJobClass();
		Object objectClass = Class.forName(className).newInstance();
		jobClass = (Class<? extends Job>) objectClass.getClass();

		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		// Get trigger key
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getName(), job.getJobGroup());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		if (null == trigger) {// There is no task

			// Create a task
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(job.getName(), job.getJobGroup()).build();

			// The JobDataMap can be used to hold any number of (serializable)
			// objects which you wish
			// to have made available to the job instance when it executes.
			// JobDataMap is an implementation
			// of the Java Map interface, and has some added convenience methods
			// for storing and retrieving data of primitive types.

			jobDetail.getJobDataMap().put(CRONJOB_UUID, job.getUuid());
			jobDetail.getJobDataMap().put(CRONJOB_ID, job.getId());

			if (job.getCronjobDatas() != null) {
				for (CronjobData param : job.getCronjobDatas()) {
					jobDetail.getJobDataMap().put(param.getName(), param.getValue());
				}
			}

			if (job.getJobTrigger().equals(CronjobEnum.JobTrigger.RUN_ONCE)) {

				Trigger runOnceTrigger;
				if (job.getTriggerStartDate() == null) {
					runOnceTrigger = TriggerBuilder.newTrigger().withIdentity(job.getName(), job.getJobGroup()).startNow().build();
				} else {
					runOnceTrigger = TriggerBuilder.newTrigger().withIdentity(job.getName(), job.getJobGroup()).startAt(job.getTriggerStartDate()).build();
				}
				scheduler.scheduleJob(jobDetail, runOnceTrigger);
			} else if (StringUtils.isNotBlank(job.getCronExpression())) {
				// Expression Builder Scheduler
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

				// According to the new cronExpression expression to build a new trigger
				if (job.getTriggerStartDate() != null) {
					
					// TriggerStartDate in the past
					if (job.getTriggerStartDate().compareTo(new Date()) <= 0) { 

						// Add 1 day to the original start time

						Calendar oldStartDate = Calendar.getInstance();
						oldStartDate.setTime(job.getTriggerStartDate());
						
						Calendar newStartDate = Calendar.getInstance();
						newStartDate.setTime(new Date());
						newStartDate.set(Calendar.HOUR, oldStartDate.get(Calendar.HOUR));
						newStartDate.set(Calendar.MINUTE, oldStartDate.get(Calendar.MINUTE));
						newStartDate.set(Calendar.SECOND, oldStartDate.get(Calendar.SECOND));
						newStartDate.set(Calendar.MILLISECOND, oldStartDate.get(Calendar.MILLISECOND));
						newStartDate.add(Calendar.DATE, 1);

						trigger = TriggerBuilder.newTrigger().withIdentity(job.getName(), job.getJobGroup()).startAt(newStartDate.getTime()).withSchedule(scheduleBuilder).build();
					} else {
						trigger = TriggerBuilder.newTrigger().withIdentity(job.getName(), job.getJobGroup()).startAt(job.getTriggerStartDate()).withSchedule(scheduleBuilder).build();
					}
				} else {
					trigger = TriggerBuilder.newTrigger().withIdentity(job.getName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
				}

				scheduler.scheduleJob(jobDetail, trigger);
			}

		} else {
			// Presence task
			// Trigger already exists, then update the corresponding timer
			// setting
			// Expression Builder Scheduler
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

			// According to the new cronExpression expression rebuild trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// According to the new trigger re-set job execution
			scheduler.rescheduleJob(triggerKey, trigger);
		}
//		Replaced by global listener
//		//Adding the listener
//		scheduler.getListenerManager().addJobListener(new QuartJobSchedulingListener());
	}

	public void pauseJob(Cronjob job) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(job.getName(), job.getJobGroup());
		scheduler.pauseJob(jobKey);
	}

	public void resumeJob(Cronjob job) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(job.getName(), job.getJobGroup());
		scheduler.resumeJob(jobKey);
	}

	public boolean deleteJob(Cronjob job) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(job.getName(), job.getJobGroup());

		return scheduler.deleteJob(jobKey);
	}

	public void clearAllScheduler() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		scheduler.clear();
	}

}
