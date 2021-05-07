package com.beanframework.core.interceptor.usergroup;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.UserGroup;

public class UserGroupPrepareInterceptor extends AbstractPrepareInterceptor<UserGroup> {

	@Value(UserConstants.Admin.DEFAULT_GROUP)
	private String defaultAdminGroup;

	@Value(UserConstants.Employee.DEFAULT_GROUP)
	private String defaultEmployeeGroup;

	@Value(UserConstants.Customer.DEFAULT_GROUP)
	private String defaultCustomerGroup;

	@Value(UserConstants.Vendor.DEFAULT_GROUP)
	private String defaultVendorGroup;

	@Override
	public void onPrepare(UserGroup model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);

		if (context.getOldModel() != null) {
			UserGroup oldUserGroup = (UserGroup) context.getOldModel();
			if (oldUserGroup.getId().equals(defaultAdminGroup) && model.getId().equals(defaultAdminGroup) == Boolean.FALSE 
					| oldUserGroup.getId().equals(defaultEmployeeGroup) && model.getId().equals(defaultEmployeeGroup) == Boolean.FALSE 
					| oldUserGroup.getId().equals(defaultCustomerGroup) && model.getId().equals(defaultCustomerGroup) == Boolean.FALSE 
					| oldUserGroup.getId().equals(defaultVendorGroup) && model.getId().equals(defaultVendorGroup) == Boolean.FALSE) {
				throw new InterceptorException("Default group ID is not allowed to change.");
			}
		}

		for (int i = 0; i < model.getAttributes().size(); i++) {
			if (StringUtils.isBlank(model.getAttributes().get(i).getValue())) {
				model.getAttributes().get(i).setValue(null);
			}
		}
	}

}
