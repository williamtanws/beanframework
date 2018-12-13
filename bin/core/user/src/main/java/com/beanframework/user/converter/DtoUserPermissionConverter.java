package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.language.domain.Language;
import com.beanframework.language.service.LanguageService;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionLang;
import com.beanframework.user.service.UserPermissionService;

@Component
public class DtoUserPermissionConverter implements DtoConverter<UserPermission, UserPermission> {

	@Autowired
	private UserPermissionService userPermissionService;

	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private DtoUserPermissionLangConverter dtoUserPermissionLangConverter;

	@Override
	public UserPermission convert(UserPermission source) {
		return convert(source, userPermissionService.create());
	}

	public List<UserPermission> convert(List<UserPermission> sources) {
		List<UserPermission> convertedList = new ArrayList<UserPermission>();
		for (UserPermission source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private UserPermission convert(UserPermission source, UserPermission prototype) {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setSort(source.getSort());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		// Process User Permission Lang
		prototype.setUserPermissionLangs(dtoUserPermissionLangConverter.convert(source.getUserPermissionLangs()));
		List<Language> languages = languageService.findByOrderBySortAsc();
		for (Language language : languages) {
			boolean addNewLanguage = true;
			for (UserPermissionLang userPermissionLang : source.getUserPermissionLangs()) {
				if (userPermissionLang.getLanguage().getUuid().equals(language.getUuid())) {
					addNewLanguage = false;
				}
			}

			if (addNewLanguage) {
				UserPermissionLang userPermissionLang = new UserPermissionLang();
				userPermissionLang.setLanguage(language);
				userPermissionLang.setUserPermission(prototype);

				prototype.getUserPermissionLangs().add(userPermissionLang);
			}
		}

		return prototype;
	}

}
