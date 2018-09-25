package com.beanframework.language.validator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.language.LanguageConstants;
import com.beanframework.language.domain.Language;
import com.beanframework.language.service.LanguageService;

@Component
public class DeleteLanguageValidator implements Validator {

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
		
		UUID uuid = (UUID) target;

		Language userGroup = languageService.findByUuid(uuid);
		if(userGroup == null) {
			errors.reject(Language.ID, localMessageService.getMessage(LanguageConstants.Locale.UUID_NOT_EXISTS));
		}
	}

}
