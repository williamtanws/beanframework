package com.beanframework.core.job;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.cronjob.service.QuartzManager;

@Component
@DisallowConcurrentExecution
public class EmailJob implements Job {

  protected static final Logger LOGGER = LoggerFactory.getLogger(EmailJob.class);

  public static final String LIMIT = "limit";
  public static final String SENT_EMAIL = "sentEmail";
  public static final String FAILED_EMAIL = "failedEmail";

  @Autowired
  private RuntimeService runtimeService;

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {

    try {
      String limit = (String) context.get(LIMIT);

      Map<String, Object> variables = new HashMap<String, Object>();
      variables.put(LIMIT, limit);

      ProcessInstance processInstance =
          runtimeService.startProcessInstanceByKey("emailProcess", variables);

      int sentEmail = (int) processInstance.getProcessVariables().get(SENT_EMAIL);
      int failedEmail = (int) processInstance.getProcessVariables().get(FAILED_EMAIL);

      context.setResult(
          new MessageFormat("{0} sent, {1} failed").format(new Object[] {sentEmail, failedEmail}));

      if (sentEmail > 0 || failedEmail > 0) {
        context.put(QuartzManager.CRONJOB_NOTIFICATION, Boolean.TRUE);
      }
    } catch (Exception e) {
      context.put(QuartzManager.CRONJOB_NOTIFICATION, Boolean.TRUE);
      LOGGER.error(e.getMessage(), e);
      throw new JobExecutionException(e.getMessage(), e);
    }
  }

}
