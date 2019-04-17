package com.beanframework.email.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.email.domain.Email;

public class EmailListLoadInterceptor extends AbstractLoadInterceptor<Email> {

	@Override
	public Email onLoad(Email model, InterceptorContext context) throws InterceptorException {
		Email prototype = new Email();
		loadCommonProperties(model, prototype, context);
		prototype.setName(model.getName());
		prototype.setToRecipients(model.getToRecipients());
		prototype.setCcRecipients(model.getCcRecipients());
		prototype.setBccRecipients(model.getBccRecipients());
		prototype.setSubject(model.getSubject());
		prototype.setText(model.getText());
		prototype.setHtml(model.getHtml());
		prototype.setStatus(model.getStatus());
		prototype.setResult(model.getResult());
		prototype.setMessage(model.getMessage());
		
		return prototype;
	}

}
