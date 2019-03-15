package com.beanframework.cronjob.service;

import java.util.UUID;

import org.quartz.SchedulerException;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.cronjob.domain.Cronjob;

public interface CronjobManagerService {

	void clearAllScheduler() throws SchedulerException;

	void initCronJob() throws Exception;

	void deleteJobByUuid(UUID uuid) throws BusinessException;

	Cronjob findByUuid(UUID uuid) throws Exception;

	void stopAllCronjob() throws SchedulerException;

	void resumeAllCronjob() throws Exception;

	void updateJobAndSaveTrigger(Cronjob cronjob) throws Exception;
}
