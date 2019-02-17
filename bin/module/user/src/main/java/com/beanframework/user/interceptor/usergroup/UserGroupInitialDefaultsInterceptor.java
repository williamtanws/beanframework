package com.beanframework.user.interceptor.usergroup;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.service.ConfigurationService;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;
import com.beanframework.dynamicfield.service.DynamicFieldTemplateService;
import com.beanframework.user.UserGroupConstants;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;

public class UserGroupInitialDefaultsInterceptor implements InitialDefaultsInterceptor<UserGroup> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserGroupInitialDefaultsInterceptor.class);

	@Autowired
	private DynamicFieldTemplateService dynamicFieldTemplateService;

	@Autowired
	private ConfigurationService configurationService;

	@Value(UserGroupConstants.DYNAMIC_FIELD_TEMPLATE)
	private String DYNAMIC_FIELD_TEMPLATE;

	@Override
	public void onInitialDefaults(UserGroup model, InterceptorContext context) throws InterceptorException {

		try {
			Map<String, Object> configurationProperties = new HashMap<String, Object>();
			configurationProperties.put(Configuration.ID, DYNAMIC_FIELD_TEMPLATE);
			Configuration configuration = configurationService.findOneEntityByProperties(configurationProperties);

			if (configuration != null && StringUtils.isNotBlank(configuration.getValue())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(DynamicFieldTemplate.ID, configuration.getValue());

				DynamicFieldTemplate dynamicFieldTemplate = dynamicFieldTemplateService.findOneEntityByProperties(properties);

				if (dynamicFieldTemplate != null) {

					for (DynamicFieldSlot dynamicFieldSlot : dynamicFieldTemplate.getDynamicFieldSlots()) {
						UserGroupField field = new UserGroupField();
						field.setDynamicField(dynamicFieldSlot.getDynamicField());
						field.setSort(dynamicFieldSlot.getSort());
						field.setUserGroup(model);
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
