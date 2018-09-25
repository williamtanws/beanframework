package com.beanframework.catalog.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.catalog.domain.Catalog;
import com.beanframework.catalog.service.CatalogService;
import com.beanframework.common.service.LocaleMessageService;

@Component
public class SaveCatalogValidator implements Validator {

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
		final Catalog catalog = (Catalog) target;

		if (catalog.getUuid() == null) {
			// Save new
			if (StringUtils.isEmpty(catalog.getId())) {
				errors.reject(Catalog.ID, localMessageService.getMessage("module.catalog.id.required"));
			} else {
				boolean existsCatalog = catalogService.isIdExists(catalog.getId());
				if (existsCatalog) {
					errors.reject(Catalog.ID, localMessageService.getMessage("module.catalog.id.exists"));
				}
			}

		} else {
			// Update exists
			if (StringUtils.isNotEmpty(catalog.getId())) {
				Catalog existsCatalog = catalogService.findById(catalog.getId());
				if (existsCatalog != null) {
					if (!catalog.getUuid().equals(existsCatalog.getUuid())) {
						errors.reject(Catalog.ID, localMessageService.getMessage("module.catalog.id.exists"));
					}
				}
			}
		}
	}

}
