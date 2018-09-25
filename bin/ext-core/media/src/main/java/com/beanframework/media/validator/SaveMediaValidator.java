package com.beanframework.media.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.domain.AbstractDomain;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.media.domain.Media;
import com.beanframework.media.service.MediaService;

@Component
public class SaveMediaValidator implements Validator {

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
		final Media Media = (Media) target;

		if (Media.getUuid() == null) {
			// Save new
			if (StringUtils.isEmpty(Media.getId())) {
				errors.reject(AbstractDomain.ID, localMessageService.getMessage("module.media.id.required"));
			} else {
				boolean existsMedia = mediaService.isIdExists(Media.getId());
				if (existsMedia) {
					errors.reject(AbstractDomain.ID, localMessageService.getMessage("module.media.id.exists"));
				}
			}

		} else {
			// Update exists
			if (StringUtils.isNotEmpty(Media.getId())) {
				Media existsMedia = mediaService.findById(Media.getId());
				if (existsMedia != null) {
					if (!Media.getUuid().equals(existsMedia.getUuid())) {
						errors.reject(AbstractDomain.ID, localMessageService.getMessage("module.media.id.exists"));
					}
				}
			}
		}
	}

}
