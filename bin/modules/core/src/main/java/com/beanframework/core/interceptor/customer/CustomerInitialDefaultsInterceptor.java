package com.beanframework.core.interceptor.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.user.CustomerConstants;
import com.beanframework.user.domain.Customer;
import com.beanframework.user.service.UserService;

public class CustomerInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Customer> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CustomerInitialDefaultsInterceptor.class);

	@Autowired
	private UserService userService;

	@Value(CustomerConstants.CONFIGURATION_DYNAMIC_FIELD_TEMPLATE)
	private String CONFIGURATION_DYNAMIC_FIELD_TEMPLATE;

	@Override
	public void onInitialDefaults(Customer model, InterceptorContext context) throws InterceptorException {
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
