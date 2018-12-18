package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.user.domain.UserGroup;

public class DtoUserGroupConverter implements DtoConverter<UserGroup, UserGroup> {

	@Autowired
	private DtoUserAuthorityConverter dtoUserAuthorityConverter;

	@Autowired
	private DtoUserGroupLangConverter dtoUserGroupLangConverter;

	@Override
	public UserGroup convert(UserGroup source) {
		return convert(source, new UserGroup());
	}

	public List<UserGroup> convert(List<UserGroup> sources) {
		List<UserGroup> convertedList = new ArrayList<UserGroup>();
		for (UserGroup source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private UserGroup convert(UserGroup source, UserGroup prototype) {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		Hibernate.initialize(source.getUserAuthorities());
		prototype.setUserAuthorities(dtoUserAuthorityConverter.convert(source.getUserAuthorities()));
		Hibernate.initialize(source.getUserGroupFields());
		prototype.setUserGroupFields(dtoUserGroupLangConverter.convert(source.getUserGroupFields()));

//		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
//		sorts.put(Language.SORT, Sort.Direction.ASC);
//		
//		List<Language> languages = modelService.findBySorts(sorts, Language.class);
//		
//		for (Language language : languages) {
//			boolean addNewLanguage = true;
//			for (UserGroupField userGroupLang : source.getUserGroupFields()) {
//				if(userGroupLang.getLanguage().getUuid().equals(language.getUuid())) {
//					addNewLanguage = false;
//				}
//			}
//			
//			if(addNewLanguage) {
//				UserGroupField userGroupLang = new UserGroupField();
//				userGroupLang.setLanguage(language);
//				userGroupLang.setUserGroup(prototype);
//				
//				prototype.getUserGroupFields().add(userGroupLang);
//			}
//		}
//		
//		// Process User Authorities
//		Hibernate.initialize(source.getUserAuthorities());
//		prototype.setUserAuthorities(dtoUserAuthorityConverter.convert(source.getUserAuthorities()));
//		if(prototype.getUserAuthorities().isEmpty()) {
//			
//			Map<String, Sort.Direction> permissionSorts = new HashMap<String, Sort.Direction>();
//			permissionSorts.put(UserPermission.SORT, Sort.Direction.ASC);
//			
//			List<UserPermission> userPermissions = modelService.findBySorts(permissionSorts, UserPermission.class);
//			
//			List<UserRight> userRights = modelService.findBySorts(sorts, UserRight.class);
//			
//			for (UserPermission userPermission : userPermissions) {
//				for (UserRight userRight : userRights) {
//					UserAuthority userAuthority = new UserAuthority();
//					userAuthority.setUserGroup(prototype);
//					userAuthority.setUserPermission(userPermission);
//					userAuthority.setUserRight(userRight);
//					userAuthority.setEnabled(false);
//					
//					prototype.getUserAuthorities().add(userAuthority);
//				}
//			}
//			
//		}

		return prototype;
	}
}
