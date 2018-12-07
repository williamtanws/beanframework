package com.beanframework.backoffice.validator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.language.LanguageConstants;
import com.beanframework.language.domain.Language;
import com.beanframework.language.service.LanguageFacade;

@Component
public class DeleteBackofficeModuleLanguageValidator implements Validator {

	@Autowired
	private LanguageFacade languageFacade;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return UUID.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		
		UUID uuid = (UUID) target;

		Language language = languageFacade.findByUuid(uuid);
		if(language == null) {
			errors.reject(Language.ID, localMessageService.getMessage(LanguageConstants.Locale.UUID_NOT_EXISTS));
		}
	}

}
