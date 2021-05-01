package com.beanframework.core.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.beanframework.common.service.ModelService;
import com.beanframework.core.job.EmailJob;
import com.beanframework.email.domain.Email;
import com.beanframework.email.domain.EmailEnum.Status;

@Component("processEmailTask")
public class ProcessEmailTask implements JavaDelegate {

	protected static final Logger LOGGER = LoggerFactory.getLogger(ProcessEmailTask.class);

	public static final String PROCESSING_EMAILS = "processingEmails";

	@Autowired
	private ModelService modelService;

	public void execute(DelegateExecution execution) {
		int EMAIL_PROCESS_NUMBER = 10;

		String limit = (String) execution.getVariable(EmailJob.LIMIT);
		if (limit != null) {
			EMAIL_PROCESS_NUMBER = Integer.valueOf(limit);
		}

		try {
			// Count old processing email
			Map<String, Object> oldProperties = new HashMap<String, Object>();
			oldProperties.put(Email.STATUS, Status.PROCESSING);

			Map<String, Sort.Direction> oldSorts = new HashMap<String, Sort.Direction>();
			oldSorts.put(Email.CREATED_DATE, Sort.Direction.ASC);

			List<Email> oldEmails = modelService.findByPropertiesBySortByResult(oldProperties, oldSorts, null, null, Email.class);
			int count = oldEmails.size();

			int numberOfPendingEmails = 0;

			if (count < EMAIL_PROCESS_NUMBER) {
				numberOfPendingEmails = EMAIL_PROCESS_NUMBER - count;
			}

			// Change number of pending email deducted by old email to processing email
			if (numberOfPendingEmails > 0) {

				Map<String, Object> pendingProperties = new HashMap<String, Object>();
				pendingProperties.put(Email.STATUS, Status.PUBLISH);

				Map<String, Sort.Direction> pendingSorts = new HashMap<String, Sort.Direction>();
				pendingSorts.put(Email.CREATED_DATE, Sort.Direction.ASC);

				List<Email> pendingEmails = modelService.findByPropertiesBySortByResult(pendingProperties, pendingSorts, null, null, Email.class);

				// Change pending email to processing email
				for (int i = 0; i < pendingEmails.size(); i++) {
					pendingEmails.get(i).setStatus(Status.PROCESSING);
					modelService.saveEntity(pendingEmails.get(i));
				}
				modelService.flush();
			}

			// Get all processing email
			Map<String, Object> processingProperties = new HashMap<String, Object>();
			processingProperties.put(Email.STATUS, Status.PROCESSING);

			Map<String, Sort.Direction> processingSorts = new HashMap<String, Sort.Direction>();
			processingSorts.put(Email.CREATED_DATE, Sort.Direction.ASC);

			List<Email> processingEmails = null;
			if (EMAIL_PROCESS_NUMBER == 0) {
				processingEmails = modelService.findByPropertiesBySortByResult(processingProperties, processingSorts, null, null, Email.class);
			} else {
				processingEmails = modelService.findByPropertiesBySortByResult(processingProperties, processingSorts, null, EMAIL_PROCESS_NUMBER, Email.class);
			}

			if (processingEmails.size() > 0) {
				execution.setVariable(PROCESSING_EMAILS, processingEmails);
				execution.setTransientVariable("send", true);
			} else {
				execution.setVariable(EmailJob.SENT_EMAIL, 0);
				execution.setVariable(EmailJob.FAILED_EMAIL, 0);
				execution.setTransientVariable("send", false);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
