package com.beanframework.vendor.interceptor;

import org.hibernate.Hibernate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;
import com.beanframework.vendor.domain.Vendor;
import com.beanframework.user.domain.UserField;

public class VendorInitializeInterceptor implements InitializeInterceptor<Vendor> {

	@Override
	public void onInitialize(Vendor model, InterceptorContext context) throws InterceptorException {
		Hibernate.initialize(model.getUserGroups());

		Hibernate.initialize(model.getFields());
		for (UserField field : model.getFields()) {
			Hibernate.initialize(field.getDynamicField().getEnumerations());
		}

	}
}
