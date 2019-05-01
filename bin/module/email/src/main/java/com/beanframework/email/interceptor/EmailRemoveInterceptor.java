package com.beanframework.email.interceptor;

import java.io.File;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.email.EmailConstants;
import com.beanframework.email.domain.Email;

public class EmailRemoveInterceptor extends AbstractRemoveInterceptor<Email> {

	@Value(EmailConstants.EMAIL_ATTACHMENT_LOCATION)
	public String EMAIL_ATTACHMENT_LOCATION;

	@Override
	public void onRemove(Email model, InterceptorContext context) throws InterceptorException {
		try {
			String workingDir = System.getProperty("user.dir");

			File emailAttachmentFolder = new File(workingDir, EMAIL_ATTACHMENT_LOCATION + File.separator + model.getUuid());
			FileUtils.deleteDirectory(emailAttachmentFolder);
		} catch (IOException e) {
			throw new InterceptorException(e.getMessage(), e);
		}
	}

}
