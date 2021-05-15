package com.beanframework.cronjob.listener;

import java.text.MessageFormat;
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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import com.beanframework.common.service.ModelService;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobEnum;
import com.beanframework.cronjob.event.CronjobEvent;
import com.beanframework.cronjob.service.CronjobQuartzManager;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.OverRiddenUser;
import com.beanframework.user.service.UserService;
import net.bytebuddy.utility.RandomString;

@Component
public class CronjobGlobalListener extends JobListenerSupport implements ApplicationContextAware {

  protected static final Logger LOGGER = LoggerFactory.getLogger(CronjobGlobalListener.class);

  public static final String LISTENER_NAME = "quartJobSchedulingListener";

  @Autowired
  private ModelService modelService;

  @Autowired
  private UserService userService;

  @Autowired
  private OverRiddenUser overRiddenUser;

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  private EntityManagerFactory entityManagerFactory;

  private ApplicationContext applicationContext;

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

    Cronjob cronjob = null;
    try {
      JobDataMap dataMap = context.getJobDetail().getJobDataMap();

      UUID uuid = (UUID) dataMap.get(CronjobQuartzManager.CRONJOB_UUID);

      cronjob = modelService.findOneByUuid(uuid, Cronjob.class);
      if (cronjob != null) {

        // Set user session and thread
        User currentUser = modelService.findOneByUuid(cronjob.getUser(), User.class);
        if (currentUser != null) {
          userService.setCurrentUser(currentUser);

          RandomString randomThreadName = new RandomString(9);
          Thread.currentThread().setName(randomThreadName.toString());
          overRiddenUser.getUserThreadMap().put(Thread.currentThread().getName(), currentUser);
        }


        cronjob.setStatus(CronjobEnum.Status.RUNNING);
        cronjob.setLastStartExecutedDate(new Date());
        modelService.saveEntity(cronjob);
      }
    } catch (Exception e) {
      e.printStackTrace();
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Override
  public void jobExecutionVetoed(JobExecutionContext context) {

    Cronjob cronjob = null;
    try {
      JobDataMap dataMap = context.getJobDetail().getJobDataMap();

      UUID uuid = (UUID) dataMap.get(CronjobQuartzManager.CRONJOB_UUID);

      cronjob = modelService.findOneByUuid(uuid, Cronjob.class);
      cronjob.setStatus(CronjobEnum.Status.ABORTED);
      cronjob.setResult(null);
      cronjob.setLastFinishExecutedDate(new Date());

      modelService.saveEntity(cronjob);

    } catch (Exception e) {
      e.printStackTrace();
      LOGGER.error(e.getMessage(), e);
    } finally {
      overRiddenUser.getUserThreadMap().remove(Thread.currentThread().getName());
    }
  }

  @Override
  public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
    EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager
        .unbindResource(entityManagerFactory);
    EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager());

    JobDataMap dataMap = context.getJobDetail().getJobDataMap();

    UUID uuid = (UUID) dataMap.get(CronjobQuartzManager.CRONJOB_UUID);

    Cronjob cronjob = null;
    CronjobEnum.Status status = null;
    CronjobEnum.Result result = null;
    String message = null;

    try {
      cronjob = modelService.findOneByUuid(uuid, Cronjob.class);

      if (cronjob != null) {
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

        modelService.saveEntity(cronjob);
      }

    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      jobException.addSuppressed(e);
    } finally {
      if (context.get(CronjobQuartzManager.CRONJOB_NOTIFICATION) == Boolean.TRUE) {
        applicationEventPublisher.publishEvent(new CronjobEvent(cronjob,
            MessageFormat.format("{0}: status={1}, result={2}, message={3}", cronjob.getName(),
                status.toString(), result.toString(), message.toString())));
      }
      overRiddenUser.getUserThreadMap().remove(Thread.currentThread().getName());
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
    if (this.applicationContext == null)
      throw new RuntimeException("applicationContext is null");
  }
}
