package com.beanframework.core.interceptor.vendor;

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
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;
import com.beanframework.user.VendorConstants;
import com.beanframework.user.domain.UserField;
import com.beanframework.user.domain.Vendor;

public class VendorInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Vendor> {

	protected static Logger LOGGER = LoggerFactory.getLogger(VendorInitialDefaultsInterceptor.class);

	@Autowired
	private ModelService modelService;

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
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Configuration.ID, CONFIGURATION_DYNAMIC_FIELD_TEMPLATE);
			Configuration configuration = modelService.findOneByProperties(properties, Configuration.class);

			if (configuration != null && StringUtils.isNotBlank(configuration.getValue())) {
				properties = new HashMap<String, Object>();
				properties.put(DynamicFieldTemplate.ID, configuration.getValue());

				DynamicFieldTemplate dynamicFieldTemplate = modelService.findOneByProperties(properties, DynamicFieldTemplate.class);

				if (dynamicFieldTemplate != null) {

					for (UUID dynamicFieldSlot : dynamicFieldTemplate.getDynamicFieldSlots()) {
						UserField field = new UserField();
						field.setDynamicFieldSlot(dynamicFieldSlot);
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
