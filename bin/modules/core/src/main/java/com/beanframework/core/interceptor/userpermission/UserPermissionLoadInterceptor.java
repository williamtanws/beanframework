package com.beanframework.core.interceptor.userpermission;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;
import com.beanframework.user.UserPermissionConstants;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionField;

public class UserPermissionLoadInterceptor extends AbstractLoadInterceptor<UserPermission> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserPermissionLoadInterceptor.class);

	@Autowired
	private ModelService modelService;

	@Value(UserPermissionConstants.CONFIGURATION_DYNAMIC_FIELD_TEMPLATE)
	private String CONFIGURATION_DYNAMIC_FIELD_TEMPLATE;

	@Override
	public void onLoad(UserPermission model, InterceptorContext context) throws InterceptorException {
		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Configuration.ID, CONFIGURATION_DYNAMIC_FIELD_TEMPLATE);
			Configuration configuration = modelService.findOneByProperties(properties, Configuration.class);

			if (configuration != null && StringUtils.isNotBlank(configuration.getValue())) {
				properties = new HashMap<String, Object>();
				properties.put(DynamicFieldTemplate.ID, configuration.getValue());

				DynamicFieldTemplate dynamicFieldTemplate = modelService.findOneByProperties(properties, DynamicFieldTemplate.class);

				if (dynamicFieldTemplate != null) {

					for (UUID dynamicFieldSlot : dynamicFieldTemplate.getDynamicFieldSlots()) {

						boolean addField = true;
						for (UserPermissionField field : model.getFields()) {
							if (field.getDynamicFieldSlot().equals(dynamicFieldSlot)) {
								addField = false;
							}
						}

						if (addField) {
							UserPermissionField field = new UserPermissionField();
							field.setDynamicFieldSlot(dynamicFieldSlot);
							field.setUserPermission(model);
							model.getFields().add(field);
						}
					}
				}
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new InterceptorException(e.getMessage(), e);
		}
	}
}
