package com.beanframework.catalog.validator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.catalog.CatalogConstants;
import com.beanframework.catalog.domain.Catalog;
import com.beanframework.catalog.service.CatalogService;
import com.beanframework.common.service.LocaleMessageService;

@Component
public class DeleteCatalogValidator implements Validator {

	@Autowired
	private CatalogService catalogService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Catalog.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		
		UUID uuid = (UUID) target;

		Catalog userGroup = catalogService.findByUuid(uuid);
		if(userGroup == null) {
			errors.reject(Catalog.ID, localMessageService.getMessage(CatalogConstants.Locale.UUID_NOT_EXISTS));
		}
	}

}
