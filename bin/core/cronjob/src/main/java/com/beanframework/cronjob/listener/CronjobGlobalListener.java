package com.beanframework.cronjob.listener;

import java.util.Date;
import java.util.UUID;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobEnum;
import com.beanframework.cronjob.service.QuartzManager;
import com.beanframework.cronjob.service.CronjobManagerService;

@Component
public class CronjobGlobalListener implements JobListener {

	public static final String LISTENER_NAME = "quartJobSchedulingListener";

	@Autowired
	private CronjobManagerService cronjobManagerService;

	@Override
	public String getName() {
		return LISTENER_NAME;
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();

		UUID uuid = (UUID) dataMap.get(QuartzManager.CRONJOB_UUID);

		cronjobManagerService.updateStatus(uuid, CronjobEnum.Status.RUNNING, null, null, new Date(), null);
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();

		UUID uuid = (UUID) dataMap.get(QuartzManager.CRONJOB_UUID);

		cronjobManagerService.updateStatus(uuid, CronjobEnum.Status.ABORTED, CronjobEnum.Result.ERROR, null, null,
				new Date());
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();

		UUID uuid = (UUID) dataMap.get(QuartzManager.CRONJOB_UUID);

		Cronjob cronjob = cronjobManagerService.findByUuid(uuid);

		String message = null;
		CronjobEnum.Status status = null;
		CronjobEnum.Result result = null;

		if (cronjob.getJobTrigger().equals(CronjobEnum.JobTrigger.RUN_ONCE)) {
			status = CronjobEnum.Status.FINISHED;

			if (jobException == null) {
				result = CronjobEnum.Result.SUCCESS;
				message = context.getResult() != null ? context.getResult().toString() : null;
			} else {
				result = CronjobEnum.Result.ERROR;
				message = jobException.getMessage();
			}

		} else {
			status = CronjobEnum.Status.RUNNING;
			
			if (jobException == null) {
				result = CronjobEnum.Result.SUCCESS;
				message = context.getResult() != null ? context.getResult().toString() : null;
			} else {
				result = CronjobEnum.Result.ERROR;
				message = jobException.getMessage();
			}
		}

		cronjobManagerService.updateStatus(uuid, status, result, message, null, new Date());

	}
}
