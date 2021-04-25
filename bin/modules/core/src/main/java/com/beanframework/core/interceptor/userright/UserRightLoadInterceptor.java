package com.beanframework.core.interceptor.userright;

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
import com.beanframework.user.UserRightConstants;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightField;

public class UserRightLoadInterceptor extends AbstractLoadInterceptor<UserRight> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserRightLoadInterceptor.class);

	@Autowired
	private ModelService modelService;

	@Value(UserRightConstants.CONFIGURATION_DYNAMIC_FIELD_TEMPLATE)
	private String CONFIGURATION_DYNAMIC_FIELD_TEMPLATE;

	@Override
	public void onLoad(UserRight model, InterceptorContext context) throws InterceptorException {
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
						for (UserRightField field : model.getFields()) {
							if (field.getDynamicFieldSlot().equals(dynamicFieldSlot)) {
								addField = false;
							}
						}

						if (addField) {
							UserRightField field = new UserRightField();
							field.setDynamicFieldSlot(dynamicFieldSlot);
							field.setUserRight(model);
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
