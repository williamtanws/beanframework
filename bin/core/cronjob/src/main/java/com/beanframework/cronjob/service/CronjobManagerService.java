package com.beanframework.cronjob.service;

import java.util.Date;
import java.util.UUID;

import org.quartz.SchedulerException;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobEnum;

public interface CronjobManagerService {

	void clearAllScheduler() throws SchedulerException;
	
	void initCronJob() throws Exception;

	void updateStatus(UUID uuid, CronjobEnum.Status status, CronjobEnum.Result result, String message, Date lastStartExecutedDate, Date lastFinishExecutedDate);

	void trigger(Cronjob cronjob) throws Exception;

	void deleteJobByUuid(UUID uuid) throws BusinessException;

	Cronjob findByUuid(UUID uuid) throws Exception;

	
}
