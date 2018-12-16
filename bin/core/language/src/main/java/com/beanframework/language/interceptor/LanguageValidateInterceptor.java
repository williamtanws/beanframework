package com.beanframework.language.interceptor;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.language.domain.Language;

public class LanguageValidateInterceptor implements ValidateInterceptor<Language> {

	@Autowired
	private ModelService modelService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public void onValidate(Language model) throws InterceptorException {

		if (model.getUuid() == null) {
			// Save new
			if (StringUtils.isEmpty(model.getId())) {
				throw new InterceptorException(localMessageService.getMessage("module.language.id.required"));
			} else {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Language.ID, model.getId());

				boolean existsLanguage = modelService.existsByProperties(properties, Language.class);

				if (existsLanguage) {
					throw new InterceptorException(localMessageService.getMessage("module.language.id.exists"));
				}
			}

		} else {
			// Update exists
			if (StringUtils.isNotEmpty(model.getId())) {

				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Language.ID, model.getId());

				Language existsLanguage = modelService.findOneEntityByProperties(properties, Language.class);
				if (existsLanguage != null) {
					if (!model.getUuid().equals(existsLanguage.getUuid())) {
						throw new InterceptorException(localMessageService.getMessage("module.language.id.exists"));
					}
				}
			}
		}
	}
}
