package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserPermission;


public class DtoUserPermissionConverter implements DtoConverter<UserPermission, UserPermission> {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private DtoUserPermissionFieldConverter dtoUserPermissionLangConverter;

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
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());	
		
		prototype.setSort(source.getSort());
		prototype.setUserPermissionFields(dtoUserPermissionLangConverter.convert(source.getUserPermissionFields()));
		
//		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
//		sorts.put(Language.SORT, Sort.Direction.ASC);
//		
//		List<Language> languages = modelService.findBySorts(sorts, Language.class);
//		
//		for (Language language : languages) {
//			boolean addNewLanguage = true;
//			for (UserPermissionField userPermissionLang : source.getUserPermissionFields()) {
//				if (userPermissionLang.getLanguage().getUuid().equals(language.getUuid())) {
//					addNewLanguage = false;
//				}
//			}
//
//			if (addNewLanguage) {
//				UserPermissionField userPermissionLang = new UserPermissionField();
//				userPermissionLang.setLanguage(language);
//				userPermissionLang.setUserPermission(prototype);
//
//				prototype.getUserPermissionFields().add(userPermissionLang);
//			}
//		}

		return prototype;
	}

}
