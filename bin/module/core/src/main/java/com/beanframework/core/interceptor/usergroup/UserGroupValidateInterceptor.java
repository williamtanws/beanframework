package com.beanframework.core.interceptor.usergroup;

import org.springframework.beans.factory.annotation.Value;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.UserGroup;

public class UserGroupValidateInterceptor extends AbstractValidateInterceptor<UserGroup> {

	@Value(UserConstants.Admin.DEFAULT_GROUP)
	private String defaultAdminGroup;

	@Value(UserConstants.Employee.DEFAULT_GROUP)
	private String defaultEmployeeGroup;

	@Value(UserConstants.Customer.DEFAULT_GROUP)
	private String defaultCustomerGroup;

	@Value(UserConstants.Vendor.DEFAULT_GROUP)
	private String defaultVendorGroup;

	@Override
	public void onValidate(UserGroup model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

		if(model.getUuid() == null && context.oldModel != null) {
			if(model.getId().equals(defaultAdminGroup) | model.getId().equals(defaultEmployeeGroup) | model.getId().equals(defaultCustomerGroup) | model.getId().equals(defaultVendorGroup)) {
				throw new InterceptorException("Default group is not allowed to add.");
			}
		}
	}

}
