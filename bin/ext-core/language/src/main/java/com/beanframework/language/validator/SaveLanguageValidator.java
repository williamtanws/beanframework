package com.beanframework.language.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.language.domain.Language;
import com.beanframework.language.service.LanguageService;

@Component
public class SaveLanguageValidator implements Validator {

	@Autowired
	private LanguageService languageService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Language.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		final Language language = (Language) target;

		if (language.getUuid() == null) {
			// Save new
			if (StringUtils.isEmpty(language.getId())) {
				errors.reject(Language.ID, localMessageService.getMessage("module.language.id.required"));
			} else {
				boolean existsLanguage = languageService.isIdExists(language.getId());
				if (existsLanguage) {
					errors.reject(Language.ID, localMessageService.getMessage("module.language.id.exists"));
				}
			}

		} else {
			// Update exists
			if (StringUtils.isNotEmpty(language.getId())) {
				Language existsLanguage = languageService.findById(language.getId());
				if (existsLanguage != null) {
					if (!language.getUuid().equals(existsLanguage.getUuid())) {
						errors.reject(Language.ID, localMessageService.getMessage("module.language.id.exists"));
					}
				}
			}
		}
	}

}
