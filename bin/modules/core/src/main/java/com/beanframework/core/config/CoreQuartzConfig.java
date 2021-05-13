package com.beanframework.core.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import com.beanframework.cronjob.CronjobConstants;
import com.beanframework.cronjob.context.AutowiringSpringBeanJobFactory;
import com.beanframework.cronjob.listener.CronjobGlobalListener;

@Configuration
@ConditionalOnProperty(name = CronjobConstants.PROPERTY_CONDITION_QUARTZ_ENABLED)
public class CoreQuartzConfig {

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private QuartzProperties quartzProperties;

  @Autowired
  private DataSource dataSource;

  @Bean
  public CronjobGlobalListener globalJobListener() {
    return new CronjobGlobalListener();
  }

  @Bean
  public SchedulerFactoryBean schedulerFactory(PlatformTransactionManager transactionManager)
      throws Exception {
    SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
    Properties properties = new Properties();
    properties.putAll(quartzProperties.getProperties());
    schedulerFactory.setQuartzProperties(properties);
    schedulerFactory.setOverwriteExistingJobs(true);
    schedulerFactory.setDataSource(dataSource);

    // Custom job factory of spring with DI support for @Autowired
    AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
    jobFactory.setApplicationContext(applicationContext);
    schedulerFactory.setJobFactory(jobFactory);
    schedulerFactory.setGlobalJobListeners(globalJobListener());
    schedulerFactory.setTransactionManager(transactionManager);
    schedulerFactory.setAutoStartup(true);

    return schedulerFactory;
  }

}
