package com.beanframework.cronjob.listener;

import java.util.Date;
import java.util.UUID;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.service.ModelService;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobEnum;
import com.beanframework.cronjob.service.QuartzManager;

@Component
public class CronjobGlobalListener implements JobListener {

	protected static final Logger LOGGER = LoggerFactory.getLogger(CronjobGlobalListener.class);

	public static final String LISTENER_NAME = "quartJobSchedulingListener";

	@Autowired
	private ModelService modelService;

	@Override
	public String getName() {
		return LISTENER_NAME;
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		try {
			JobDataMap dataMap = context.getJobDetail().getJobDataMap();

			UUID uuid = (UUID) dataMap.get(QuartzManager.CRONJOB_UUID);

			Cronjob cronjob = modelService.findOneEntityByUuid(uuid, Cronjob.class);
			cronjob.setStatus(CronjobEnum.Status.RUNNING);
			cronjob.setLastStartExecutedDate(new Date());
			cronjob.setLastModifiedBy(null);

			modelService.saveEntity(cronjob, Cronjob.class);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		try {
			JobDataMap dataMap = context.getJobDetail().getJobDataMap();

			UUID uuid = (UUID) dataMap.get(QuartzManager.CRONJOB_UUID);

			Cronjob cronjob = modelService.findOneEntityByUuid(uuid, Cronjob.class);
			cronjob.setStatus(CronjobEnum.Status.ABORTED);
			cronjob.setResult(CronjobEnum.Result.ERROR);
			cronjob.setLastFinishExecutedDate(new Date());
			cronjob.setLastModifiedBy(null);

			modelService.saveEntity(cronjob, Cronjob.class);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();

		UUID uuid = (UUID) dataMap.get(QuartzManager.CRONJOB_UUID);

		try {
			Cronjob cronjob = modelService.findOneEntityByUuid(uuid, Cronjob.class);

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

			cronjob.setStatus(status);
			cronjob.setResult(result);
			cronjob.setMessage(message);
			cronjob.setLastFinishExecutedDate(new Date());
			cronjob.setLastModifiedBy(null);

			modelService.saveEntity(cronjob, Cronjob.class);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
			jobException.addSuppressed(e);
		}
	}
}
