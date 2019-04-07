package com.beanframework.cronjob.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobEnum;

@Service
public class CronjobManagerServiceImpl implements CronjobManagerService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(CronjobManagerServiceImpl.class);

	@Autowired
	private ModelService modelService;

	@Autowired
	private QuartzManager quartzManager;

	@Autowired
	private CronjobService cronjobService;

	@Override
	public void clearAllScheduler() throws SchedulerException {
		quartzManager.clearAllScheduler();
	}

	@Override
	public void initCronJob() throws BusinessException, Exception {
		LOGGER.info("Clearing existing cronjob from database");
		initStartupJobIsFalseWithQueueJob();
		LOGGER.info("Initializing startup cronjob from database");
		initStartupJobIsTrue();
	}

	private void initStartupJobIsTrue() throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Cronjob.STARTUP, true);

		List<Cronjob> jobList = modelService.findEntityByPropertiesAndSorts(properties, null, null, null, Cronjob.class);

		for (Cronjob cronjob : jobList) {
			cronjob.setJobTrigger(CronjobEnum.JobTrigger.START);
			updateJobAndSaveTrigger(cronjob);
		}
	}

	private void initStartupJobIsFalseWithQueueJob() throws BusinessException {
		List<Cronjob> jobList = cronjobService.findEntityStartupJobIsFalseWithQueueJob();

		for (Cronjob cronjob : jobList) {
			cronjob.setJobTrigger(CronjobEnum.JobTrigger.STOP);
			cronjob.setStatus(CronjobEnum.Status.ABORTED);
			cronjob.setResult(null);

			cronjobService.saveEntity(cronjob);
		}
	}

	@Transactional
	@Override
	public void updateJobAndSaveTrigger(Cronjob cronjob) throws Exception {
		if (cronjob.getJobTrigger().equals(CronjobEnum.JobTrigger.RUN_ONCE) || cronjob.getJobTrigger().equals(CronjobEnum.JobTrigger.START)) {
			try {
				quartzManager.startOrUpdateJob(cronjob);
				cronjob.setStatus(CronjobEnum.Status.RUNNING);
				cronjob.setResult(null);
				cronjob.setMessage(null);

			} catch (Exception e) {
				cronjob.setStatus(CronjobEnum.Status.ABORTED);
				cronjob.setResult(CronjobEnum.Result.ERROR);
				cronjob.setMessage(e.toString());
			}

		} else if (cronjob.getJobTrigger().equals(CronjobEnum.JobTrigger.PAUSE)) {
			try {
				quartzManager.pauseJob(cronjob);
				cronjob.setStatus(CronjobEnum.Status.PAUSED);
				cronjob.setResult(null);
				cronjob.setMessage(null);

			} catch (Exception e) {
				cronjob.setStatus(CronjobEnum.Status.ABORTED);
				cronjob.setResult(CronjobEnum.Result.ERROR);
				cronjob.setMessage(e.toString());
			}
		} else if (cronjob.getJobTrigger().equals(CronjobEnum.JobTrigger.RESUME)) {
			try {
				quartzManager.resumeJob(cronjob);
				cronjob.setStatus(CronjobEnum.Status.CONTINUED);
				cronjob.setResult(null);
				cronjob.setMessage(null);

			} catch (Exception e) {
				cronjob.setStatus(CronjobEnum.Status.ABORTED);
				cronjob.setResult(CronjobEnum.Result.ERROR);
				cronjob.setMessage(e.toString());
			}
		} else if (cronjob.getJobTrigger().equals(CronjobEnum.JobTrigger.STOP)) {
			try {
				quartzManager.deleteJob(cronjob);
				cronjob.setStatus(CronjobEnum.Status.ABORTED);
				cronjob.setResult(CronjobEnum.Result.SUCCESS);
				cronjob.setMessage(null);

			} catch (Exception e) {
				cronjob.setStatus(CronjobEnum.Status.RUNNING);
				cronjob.setResult(CronjobEnum.Result.ERROR);
				cronjob.setMessage(e.toString());
			}
		}

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Cronjob.UUID, cronjob.getUuid());

		Cronjob updateCronjob = cronjobService.findOneEntityByProperties(properties);
		updateCronjob.setStatus(cronjob.getStatus());
		updateCronjob.setResult(cronjob.getResult());
		updateCronjob.setMessage(cronjob.getMessage());
		updateCronjob.setJobTrigger(cronjob.getJobTrigger());
		updateCronjob.setLastStartExecutedDate(null);
		updateCronjob.setLastFinishExecutedDate(null);

		cronjobService.saveEntity(updateCronjob);
	}

	@Override
	public void deleteJobByUuid(UUID uuid) throws BusinessException {

		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Cronjob.UUID, uuid);

			Cronjob cronjob = cronjobService.findOneEntityByProperties(properties);

			quartzManager.deleteJob(cronjob);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Cronjob findByUuid(UUID uuid) throws Exception {

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Cronjob.UUID, uuid);

		Cronjob cronjob = cronjobService.findOneEntityByProperties(properties);

		return cronjob;
	}

	@Override
	public void stopAllCronjob() throws SchedulerException {
		quartzManager.clearAllScheduler();
	}

	@Override
	public void resumeAllCronjob() throws Exception {
		List<Cronjob> jobList = cronjobService.findEntityStartupJobIsFalseWithQueueJob();

		for (Cronjob cronjob : jobList) {
			cronjob.setJobTrigger(CronjobEnum.JobTrigger.RESUME);
			updateJobAndSaveTrigger(cronjob);
		}
	}
}
