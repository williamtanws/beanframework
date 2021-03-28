package com.beanframework.cronjob.listener;

import java.util.Date;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.beanframework.common.service.ModelService;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobEnum;
import com.beanframework.cronjob.service.QuartzManager;

@Component
public class CronjobGlobalListener extends JobListenerSupport implements ApplicationContextAware {

	protected static final Logger LOGGER = LoggerFactory.getLogger(CronjobGlobalListener.class);

	public static final String LISTENER_NAME = "quartJobSchedulingListener";

	@Autowired
	private ModelService modelService;

	EntityManagerFactory entityManagerFactory;

	@Override
	public String getName() {
		return LISTENER_NAME;
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		entityManagerFactory = applicationContext.getBean(EntityManagerFactory.class);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityManagerHolder emHolder = new EntityManagerHolder(entityManager);
		TransactionSynchronizationManager.bindResource(entityManagerFactory, emHolder);

		try {
			JobDataMap dataMap = context.getJobDetail().getJobDataMap();

			UUID uuid = (UUID) dataMap.get(QuartzManager.CRONJOB_UUID);

			Cronjob cronjob = modelService.findOneByUuid(uuid, Cronjob.class);
			if (cronjob != null) {
				cronjob.setStatus(CronjobEnum.Status.RUNNING);
				cronjob.setLastStartExecutedDate(new Date());
				cronjob.setLastModifiedBy(null);

				modelService.saveEntity(cronjob);
			}
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

			Cronjob cronjob = modelService.findOneByUuid(uuid, Cronjob.class);
			cronjob.setStatus(CronjobEnum.Status.ABORTED);
			cronjob.setResult(CronjobEnum.Result.ERROR);
			cronjob.setLastFinishExecutedDate(new Date());
			cronjob.setLastModifiedBy(null);

			modelService.saveEntity(cronjob);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager.unbindResource(entityManagerFactory);
        EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager());
        
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();

		UUID uuid = (UUID) dataMap.get(QuartzManager.CRONJOB_UUID);

		try {
			Cronjob cronjob = modelService.findOneByUuid(uuid, Cronjob.class);

			if (cronjob != null) {
				String message = null;
				CronjobEnum.Status status = null;
				CronjobEnum.Result result = null;

				if (CronjobEnum.JobTrigger.RUN_ONCE.equals(cronjob.getJobTrigger())) {
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

				modelService.saveEntity(cronjob);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
			jobException.addSuppressed(e);
		}
	}

	ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		if (this.applicationContext == null)
			throw new RuntimeException("applicationContext is null");
	}
}
