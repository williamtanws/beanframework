package com.beanframework.employee.interceptor;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.service.ConfigurationService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;
import com.beanframework.dynamicfield.service.DynamicFieldTemplateService;
import com.beanframework.employee.EmployeeConstants;
import com.beanframework.employee.domain.Employee;
import com.beanframework.user.domain.UserField;

public class EmployeeInitialDefaultsInterceptor implements InitialDefaultsInterceptor<Employee> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EmployeeInitialDefaultsInterceptor.class);

	@Autowired
	private DynamicFieldTemplateService dynamicFieldTemplateService;

	@Autowired
	private ConfigurationService configurationService;

	@Value(EmployeeConstants.DYNAMIC_FIELD_TEMPLATE)
	private String DYNAMIC_FIELD_TEMPLATE;

	@Override
	public void onInitialDefaults(Employee model) throws InterceptorException {
		model.setEnabled(true);
		model.setAccountNonExpired(true);
		model.setAccountNonLocked(true);
		model.setCredentialsNonExpired(true);

		try {
			Map<String, Object> configurationProperties = new HashMap<String, Object>();
			configurationProperties.put(Configuration.ID, DYNAMIC_FIELD_TEMPLATE);
			Configuration configuration = configurationService.findOneEntityByProperties(configurationProperties);

			if (configuration != null && StringUtils.isNotBlank(configuration.getValue())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(DynamicFieldTemplate.ID, configuration.getValue());

				DynamicFieldTemplate dynamicFieldTemplate = dynamicFieldTemplateService.findOneEntityByProperties(properties);

				if (dynamicFieldTemplate != null) {

					for (DynamicField dynamicField : dynamicFieldTemplate.getDynamicFields()) {
						UserField field = new UserField();
						field.setDynamicField(dynamicField);
						field.setUser(model);
						model.getFields().add(field);
					}
				}
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new InterceptorException(e.getMessage(), e);
		}
	}

}
