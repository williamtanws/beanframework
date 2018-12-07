package com.beanframework.history.validator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.history.HistoryConstants;
import com.beanframework.history.domain.History;
import com.beanframework.history.service.HistoryService;

@Component
public class DeleteHistoryValidator implements Validator {

	@Autowired
	private HistoryService historyService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return History.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		
		UUID uuid = (UUID) target;

		History userGroup = historyService.findByUuid(uuid);
		if(userGroup == null) {
			errors.reject(History.ID, localMessageService.getMessage(HistoryConstants.Locale.UUID_NOT_EXISTS));
		}
	}

}
