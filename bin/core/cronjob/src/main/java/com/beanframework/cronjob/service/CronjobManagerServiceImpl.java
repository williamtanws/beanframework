package com.beanframework.cronjob.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

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
	private EntityManagerFactory entityManagerFactory;

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
		List<Cronjob> jobList = cronjobService.findStartupJobIsFalseWithQueueJob();

		for (Cronjob cronjob : jobList) {
			cronjob.setJobTrigger(CronjobEnum.JobTrigger.STOP);
			cronjob.setStatus(CronjobEnum.Status.ABORTED);
			cronjob.setResult(null);
			
			modelService.saveEntity(cronjob, Cronjob.class);
		}
	}

	@Override
	public void updateStatus(UUID uuid, CronjobEnum.Status status, CronjobEnum.Result result, String message,
			Date lastStartExecutedDate, Date lastFinishExecutedDate) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();

			StringBuilder queryBuilder = new StringBuilder();
			Map<String, Object> parameters = new HashMap<String, Object>();
			queryBuilder.append("update Cronjob c set c.status = :status");
			parameters.put("status", status);
			
			queryBuilder.append(", result = :result");
			parameters.put("result", result);
			
			queryBuilder.append(", message = :message");
			parameters.put("message", message);
			
			if (lastStartExecutedDate != null) {
				queryBuilder.append(", lastStartExecutedDate = :lastStartExecutedDate");
				parameters.put("lastStartExecutedDate", lastStartExecutedDate);
			}
			if (lastFinishExecutedDate != null) {
				queryBuilder.append(", lastFinishExecutedDate = :lastFinishExecutedDate");
				parameters.put("lastFinishExecutedDate", lastFinishExecutedDate);
			}
			queryBuilder.append(" where c.uuid = :uuid");
			parameters.put("uuid", uuid);

			Query query = entityManager.createQuery(queryBuilder.toString());
			for (Map.Entry<String, Object> entry : parameters.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}

			query.executeUpdate();

			entityTransaction.commit();
		} catch (Exception e) {
			logger.error(e.toString(), e);
			entityTransaction.rollback();
		} finally {
			entityManager.close();
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

	private void updateJobAndSaveTrigger(Cronjob cronjob) {
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

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			
			Query query = entityManager.createQuery(
					"update Cronjob c set c.status = :status, c.result = :result, c.message = :message, c.jobTrigger = :jobTrigger, c.lastStartExecutedDate = :lastStartExecutedDate, c.lastFinishExecutedDate = :lastFinishExecutedDate where c.uuid = :uuid");
			query.setParameter("uuid", cronjob.getUuid());
			query.setParameter("status", cronjob.getStatus());
			query.setParameter("result", cronjob.getResult());
			query.setParameter("message", cronjob.getMessage());
			query.setParameter("jobTrigger", cronjob.getJobTrigger());
			query.setParameter("lastStartExecutedDate", null);
			query.setParameter("lastFinishExecutedDate", null);
			query.executeUpdate();
			
			entityTransaction.commit();
		} catch (Exception e) {
			logger.error(e.toString(), e);
			entityTransaction.rollback();
		} finally {
			entityManager.close();
		}
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
}
