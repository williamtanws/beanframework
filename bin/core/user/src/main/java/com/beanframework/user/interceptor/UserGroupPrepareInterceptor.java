package com.beanframework.user.interceptor;

import org.apache.commons.lang3.StringUtils;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.user.domain.UserGroup;

public class UserGroupPrepareInterceptor implements PrepareInterceptor<UserGroup> {

	@Override
	public void onPrepare(UserGroup model) throws InterceptorException {

		if (model.getUserAuthorities() != null) {
			for (int i = 0; i < model.getUserAuthorities().size(); i++) {
				if (model.getUserAuthorities().get(i).getEnabled() == null) {
					model.getUserAuthorities().get(i).setEnabled(Boolean.FALSE);
				}
			}
		}

		for (int i = 0; i < model.getFields().size(); i++) {
			if (StringUtils.isBlank(model.getFields().get(i).getValue())) {
				model.getFields().get(i).setValue(null);
			}
		}
	}

}
