package com.beanframework.media.validator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.media.MediaConstants;
import com.beanframework.media.domain.Media;
import com.beanframework.media.service.MediaService;

@Component
public class DeleteMediaValidator implements Validator {

	@Autowired
	private MediaService mediaService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Media.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		
		UUID uuid = (UUID) target;

		Media userGroup = mediaService.findByUuid(uuid);
		if(userGroup == null) {
			errors.reject(Media.ID, localMessageService.getMessage(MediaConstants.Locale.UUID_NOT_EXISTS));
		}
	}

}
