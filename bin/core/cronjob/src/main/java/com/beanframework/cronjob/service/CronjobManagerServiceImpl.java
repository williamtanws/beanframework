package com.beanframework.cronjob.service;

import java.util.Date;
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

	Logger logger = LoggerFactory.getLogger(CronjobManagerServiceImpl.class);
	
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

		initStartupJobIsTrue();
		initStartupJobIsFalseWithQueueJob();
	}
	
	private void initStartupJobIsTrue() throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Cronjob.STARTUP, true);
		
		List<Cronjob> jobList = modelService.findDtoByProperties(properties, Cronjob.class);
		
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
			
			modelService.saveEntity(cronjob, Cronjob.class);
		}
	}

	@Transactional
	@Override
	public void trigger(Cronjob cronjob) throws Exception {
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Cronjob.UUID, cronjob.getUuid());
		
		Cronjob updateCronjob = modelService.findOneEntityByProperties(properties, Cronjob.class);

		updateCronjob.setJobTrigger(cronjob.getJobTrigger());
		updateCronjob.setTriggerStartDate(cronjob.getTriggerStartDate());
		updateCronjob.setLastTriggeredDate(new Date());

		updateJobAndSaveTrigger(updateCronjob);
	}

	private void updateJobAndSaveTrigger(Cronjob cronjob) throws Exception {
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
		
		Cronjob updateCronjob = modelService.findOneEntityByProperties(properties, Cronjob.class);
		updateCronjob.setStatus(cronjob.getStatus());
		updateCronjob.setResult(cronjob.getResult());
		updateCronjob.setMessage(cronjob.getMessage());
		updateCronjob.setJobTrigger(cronjob.getJobTrigger());
		updateCronjob.setLastStartExecutedDate(null);
		updateCronjob.setLastFinishExecutedDate(null);

		modelService.saveEntity(updateCronjob, Cronjob.class);
	}

	@Override
	public void deleteJobByUuid(UUID uuid) throws BusinessException {
		
		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Cronjob.UUID, uuid);
			
			Cronjob cronjob = modelService.findOneEntityByProperties(properties, Cronjob.class);

			quartzManager.deleteJob(cronjob);

			
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Cronjob findByUuid(UUID uuid) throws Exception {
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Cronjob.UUID, uuid);
		
		Cronjob cronjob = modelService.findOneDtoByProperties(properties, Cronjob.class);

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
