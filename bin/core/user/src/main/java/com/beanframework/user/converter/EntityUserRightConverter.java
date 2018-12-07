package com.beanframework.user.converter;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.language.domain.Language;
import com.beanframework.language.service.LanguageService;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightLang;
import com.beanframework.user.service.UserRightService;

@Component
public class EntityUserRightConverter implements Converter<UserRight, UserRight> {

	@Autowired
	private UserRightService userRightService;

	@Autowired
	private LanguageService languageService;

	@Override
	public UserRight convert(UserRight source) {

		Optional<UserRight> prototype = Optional.of(userRightService.create());
		if (source.getUuid() != null) {
			Optional<UserRight> exists = userRightService.findEntityByUuid(source.getUuid());
			if (exists.isPresent()) {
				prototype = exists;
			}
		} else if (StringUtils.isNotEmpty(source.getId())) {
			Optional<UserRight> exists = userRightService.findEntityById(source.getId());
			if (exists.isPresent()) {
				prototype = exists;
			}
		}

		return convert(source, prototype.get());
	}

	private UserRight convert(UserRight source, UserRight prototype) {

		prototype.setId(source.getId());
		prototype.setSort(source.getSort());
		prototype.setLastModifiedDate(new Date());

		prototype.getUserRightLangs().clear();
		for (UserRightLang userRightLang : source.getUserRightLangs()) {
			if (userRightLang.getLanguage().getUuid() != null) {
				Optional<Language> language = languageService.findEntityByUuid(userRightLang.getLanguage().getUuid());
				if (language.isPresent()) {
					userRightLang.setLanguage(language.get());
					userRightLang.setUserRight(prototype);
					prototype.getUserRightLangs().add(userRightLang);
				}
			} else if (StringUtils.isNotEmpty(userRightLang.getLanguage().getId())) {
				Optional<Language> language = languageService.findEntityById(userRightLang.getLanguage().getId());
				if (language.isPresent()) {
					userRightLang.setLanguage(language.get());
					userRightLang.setUserRight(prototype);
					prototype.getUserRightLangs().add(userRightLang);
				}
			}
		}

		return prototype;
	}

}
