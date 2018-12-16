package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.language.domain.Language;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionLang;


public class DtoUserPermissionConverter implements DtoConverter<UserPermission, UserPermission> {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private DtoUserPermissionLangConverter dtoUserPermissionLangConverter;

	@Override
	public UserPermission convert(UserPermission source) {
		return convert(source, modelService.create(UserPermission.class));
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
		
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Language.SORT, Sort.Direction.ASC);
		
		List<Language> languages = modelService.findBySorts(sorts, Language.class);
		
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
