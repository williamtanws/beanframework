package com.beanframework.core.listener;

import java.io.File;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.flowable.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.beanframework.common.exception.ListenerException;
import com.beanframework.common.registry.AfterRemoveEvent;
import com.beanframework.common.registry.AfterRemoveListener;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.email.EmailConstants;
import com.beanframework.email.domain.Email;
import com.beanframework.menu.domain.Menu;
import com.beanframework.workflow.domain.Workflow;

public class CoreAfterRemoveListener implements AfterRemoveListener {

	@Autowired
	private RepositoryService repositoryService;

	@Value(EmailConstants.EMAIL_ATTACHMENT_LOCATION)
	public String EMAIL_ATTACHMENT_LOCATION;

	@Override
	public void afterRemove(Object detachedModel, AfterRemoveEvent event) throws ListenerException {
		try {
			if (detachedModel instanceof Workflow) {
				Workflow workflow = (Workflow) detachedModel;
				repositoryService.deleteDeployment(workflow.getId());

			} else if (detachedModel instanceof Menu) {

			} else if (detachedModel instanceof Configuration) {

			} else if (detachedModel instanceof Email) {
				Email email = (Email) detachedModel;
				removeEmail(email);
			}
		} catch (IOException e) {
			throw new ListenerException(e.getMessage(), e);
		}
	}

	private void removeEmail(Email model) throws IOException {
		String workingDir = System.getProperty("user.dir");

		File emailAttachmentFolder = new File(workingDir, EMAIL_ATTACHMENT_LOCATION + File.separator + model.getUuid());
		FileUtils.deleteDirectory(emailAttachmentFolder);
	}

}
