package com.beanframework.core.interceptor.usergroup;

import org.apache.commons.lang3.StringUtils;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.user.domain.UserGroup;

public class UserGroupPrepareInterceptor extends AbstractPrepareInterceptor<UserGroup> {

	@Override
	public void onPrepare(UserGroup model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);

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
