package com.beanframework.user.interceptor;

import org.apache.commons.lang3.StringUtils;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.user.domain.UserRight;

public class UserRightPrepareInterceptor implements PrepareInterceptor<UserRight> {

	@Override
	public void onPrepare(UserRight model) throws InterceptorException {		
		for (int i = 0; i < model.getFields().size(); i++) {
			if (StringUtils.isBlank(model.getFields().get(i).getValue())) {
				model.getFields().get(i).setValue(null);
			}
		}
	}
}
