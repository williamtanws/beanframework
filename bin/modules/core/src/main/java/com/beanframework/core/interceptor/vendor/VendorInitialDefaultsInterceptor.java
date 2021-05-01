package com.beanframework.core.interceptor.vendor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.user.VendorConstants;
import com.beanframework.user.domain.Vendor;
import com.beanframework.user.service.UserService;

public class VendorInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Vendor> {

	protected static Logger LOGGER = LoggerFactory.getLogger(VendorInitialDefaultsInterceptor.class);

	@Autowired
	private UserService userService;

	@Value(VendorConstants.CONFIGURATION_DYNAMIC_FIELD_TEMPLATE)
	private String CONFIGURATION_DYNAMIC_FIELD_TEMPLATE;

	@Override
	public void onInitialDefaults(Vendor model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);
		model.setEnabled(true);
		model.setAccountNonExpired(true);
		model.setAccountNonLocked(true);
		model.setCredentialsNonExpired(true);

		try {
			userService.generateUserFieldsOnInitialDefault(model, CONFIGURATION_DYNAMIC_FIELD_TEMPLATE);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new InterceptorException(e.getMessage(), e);
		}
	}

}
