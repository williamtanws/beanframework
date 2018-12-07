package com.beanframework.cronjob.service;

import java.util.Date;
import java.util.UUID;

import org.quartz.SchedulerException;

import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobEnum;

public interface CronjobManagerService {

	void clearAllScheduler() throws SchedulerException;
	
	void initCronJob();

	void updateStatus(UUID uuid, CronjobEnum.Status status, CronjobEnum.Result result, String message, Date lastStartExecutedDate, Date lastFinishExecutedDate);

	void trigger(Cronjob cronjob);

	void deleteJobByUuid(UUID uuid) throws SchedulerException;

	Cronjob findByUuid(UUID uuid);

	
}
