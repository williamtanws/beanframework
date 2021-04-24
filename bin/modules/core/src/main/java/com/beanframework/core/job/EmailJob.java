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

		String limit = (String) context.getMergedJobDataMap().get(LIMIT);

		String result = null;

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(LIMIT, limit);

		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("emailProcess", variables);

		result = new MessageFormat("{0} sent, {1} failed").format(new Object[] { processInstance.getProcessVariables().get(SENT_EMAIL), processInstance.getProcessVariables().get(FAILED_EMAIL) });
		context.setResult(result);
	}

}
