package com.beanframework.dynamicfield.interceptor;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.dynamicfield.service.DynamicFieldService;
import com.beanframework.enumuration.domain.Enumeration;

public class DynamicFieldEnumerationRemoveInterceptor extends AbstractRemoveInterceptor<Enumeration> {

	@Autowired
	private DynamicFieldService dynamicFieldService;

	@Override
	public void onRemove(Enumeration model, InterceptorContext context) throws InterceptorException {

		try {
			dynamicFieldService.removeEnumerationByUuid(model.getUuid());
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}

	}
}
