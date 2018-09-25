package com.beanframework.user.converter;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.language.domain.Language;
import com.beanframework.language.service.LanguageService;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionLang;
import com.beanframework.user.service.UserPermissionService;

@Component
public class EntityUserPermissionConverter implements Converter<UserPermission, UserPermission> {

	@Autowired
	private UserPermissionService userPermissionService;

	@Autowired
	private LanguageService languageService;

	@Override
	public UserPermission convert(UserPermission source) {

		Optional<UserPermission> prototype = Optional.of(userPermissionService.create());
		if (source.getUuid() != null) {
			Optional<UserPermission> exists = userPermissionService.findEntityByUuid(source.getUuid());
			if (exists.isPresent()) {
				prototype = exists;
			}
		} else if (StringUtils.isNotEmpty(source.getId())) {
			Optional<UserPermission> exists = userPermissionService.findEntityById(source.getId());
			if (exists.isPresent()) {
				prototype = exists;
			}
		}

		return convert(source, prototype.get());
	}

	private UserPermission convert(UserPermission source, UserPermission prototype) {

		prototype.setId(source.getId());
		prototype.setSort(source.getSort());
		prototype.setLastModifiedDate(new Date());

		prototype.getUserPermissionLangs().clear();
		for (UserPermissionLang userPermissionLang : source.getUserPermissionLangs()) {
			if (userPermissionLang.getLanguage().getUuid() != null) {
				Optional<Language> language = languageService.findEntityByUuid(userPermissionLang.getLanguage().getUuid());
				if (language.isPresent()) {
					userPermissionLang.setLanguage(language.get());
					userPermissionLang.setUserPermission(prototype);
					prototype.getUserPermissionLangs().add(userPermissionLang);
				}
			} else if (StringUtils.isNotEmpty(userPermissionLang.getLanguage().getId())) {
				Optional<Language> language = languageService.findEntityById(userPermissionLang.getLanguage().getId());
				if (language.isPresent()) {
					userPermissionLang.setLanguage(language.get());
					userPermissionLang.setUserPermission(prototype);
					prototype.getUserPermissionLangs().add(userPermissionLang);
				}
			}
		}

		return prototype;
	}

}
