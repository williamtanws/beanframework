package com.beanframework.menu.interceptor;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.menu.MenuConstants;
import com.beanframework.menu.domain.Menu;

public class MenuValidateInterceptor implements ValidateInterceptor<Menu> {
	
	@Autowired
	private ModelService modelService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public void onValidate(Menu model) throws InterceptorException {
		
		try {
			if (model.getUuid() == null) {
				// Save new
				if (StringUtils.isBlank(model.getId())) {
					throw new InterceptorException(localMessageService.getMessage(MenuConstants.Locale.ID_REQUIRED), this);
				} else {
					Map<String, Object> properties = new HashMap<String, Object>();
					properties.put(Menu.ID, model.getId());
					boolean exists = modelService.existsByProperties(properties, Menu.class);
					if (exists) {
						throw new InterceptorException(localMessageService.getMessage(MenuConstants.Locale.ID_EXISTS),
								this);
					}
				}

			} else {
				// Update exists
				if (StringUtils.isNotEmpty(model.getId())) {
					Map<String, Object> properties = new HashMap<String, Object>();
					properties.put(Menu.ID, model.getId());
					Menu exists = modelService.findOneEntityByProperties(properties, Menu.class);
					if (exists != null) {
						if (!model.getUuid().equals(exists.getUuid())) {
							throw new InterceptorException(localMessageService.getMessage(MenuConstants.Locale.ID_EXISTS),
									this);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}
	}

}
