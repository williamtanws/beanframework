package com.beanframework.cronjob.service;

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
	
	public static final String CRONJOB_UUID = "uuid";

	@SuppressWarnings("unchecked")
	public void startOrUpdateJob(Cronjob job)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SchedulerException {

		Class<? extends Job> jobClass = null;

		String className = job.getJobClass();
		Object objectClass = Class.forName(className).newInstance();
		jobClass = (Class<? extends Job>) objectClass.getClass();

		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		// Get trigger key
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		if (null == trigger) {// There is no task

			// Create a task
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(job.getJobName(), job.getJobGroup()).build();

			// The JobDataMap can be used to hold any number of (serializable)
			// objects which you wish
			// to have made available to the job instance when it executes.
			// JobDataMap is an implementation
			// of the Java Map interface, and has some added convenience methods
			// for storing and retrieving data of primitive types.
			
			jobDetail.getJobDataMap().put(CRONJOB_UUID, job.getUuid());
			
			if(job.getCronjobDatas() != null){
				for (CronjobData param : job.getCronjobDatas()) {
					jobDetail.getJobDataMap().put(param.getName(), param.getValue());
				}
			}
			
			if(job.getJobTrigger().equals(CronjobEnum.JobTrigger.RUN_ONCE)){
				
				Trigger runOnceTrigger;
				if(job.getTriggerStartDate() != null){
					runOnceTrigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).startAt(job.getTriggerStartDate()).build();
				}
				else{
					runOnceTrigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).startNow().build();
				}
				scheduler.scheduleJob(jobDetail, runOnceTrigger);
			}
			else if(StringUtils.isNotEmpty(job.getCronExpression())){
				// Expression Builder Scheduler
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
	
				// According to the new cronExpression expression to build a new trigger
				if(job.getTriggerStartDate() != null){
					trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).startAt(job.getTriggerStartDate())
							.withSchedule(scheduleBuilder).build();
				}
				else{
					trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())
							.withSchedule(scheduleBuilder).build();
				}
				
				scheduler.scheduleJob(jobDetail, trigger);
			}
			

		} else {// Presence task

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
		JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
		scheduler.pauseJob(jobKey);
	}

	public void resumeJob(Cronjob job) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
		scheduler.resumeJob(jobKey);
	}

	public boolean deleteJob(Cronjob job) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());

		return scheduler.deleteJob(jobKey);
	}
	
	public void clearAllScheduler() throws SchedulerException{
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		scheduler.clear();
	}

}
