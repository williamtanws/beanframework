package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.language.domain.Language;
import com.beanframework.language.service.LanguageService;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupLang;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.service.UserGroupService;
import com.beanframework.user.service.UserPermissionService;
import com.beanframework.user.service.UserRightService;

@Component
public class DtoUserGroupConverter implements DtoConverter<UserGroup, UserGroup> {

	@Autowired
	private UserGroupService userGroupService;
	
	@Autowired
	private UserPermissionService userPermissionService;
	
	@Autowired
	private UserRightService userRightService;
	
	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private DtoUserAuthorityConverter dtoUserAuthorityConverter;
	
	@Autowired
	private DtoUserGroupLangConverter dtoUserGroupLangConverter;

	@Override
	public UserGroup convert(UserGroup source) {
		return convert(source, userGroupService.create());
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

		// Process User Group Lang
		prototype.setUserGroupLangs(dtoUserGroupLangConverter.convert(source.getUserGroupLangs()));
		List<Language> languages = languageService.findByOrderBySortAsc();
		for (Language language : languages) {
			boolean addNewLanguage = true;
			for (UserGroupLang userGroupLang : source.getUserGroupLangs()) {
				if(userGroupLang.getLanguage().getUuid().equals(language.getUuid())) {
					addNewLanguage = false;
				}
			}
			
			if(addNewLanguage) {
				UserGroupLang userGroupLang = new UserGroupLang();
				userGroupLang.setLanguage(language);
				userGroupLang.setUserGroup(prototype);
				
				prototype.getUserGroupLangs().add(userGroupLang);
			}
		}
		
		// Process User Authorities
		Hibernate.initialize(source.getUserAuthorities());
		prototype.setUserAuthorities(dtoUserAuthorityConverter.convert(source.getUserAuthorities()));
		if(prototype.getUserAuthorities().isEmpty()) {
			List<UserPermission> userPermissions = userPermissionService.findEntityAllByOrderBySortAsc();
			List<UserRight> userRights = userRightService.findEntityAllByOrderBySortAsc();
			
			for (UserPermission userPermission : userPermissions) {
				for (UserRight userRight : userRights) {
					UserAuthority userAuthority = new UserAuthority();
					userAuthority.setUserGroup(prototype);
					userAuthority.setUserPermission(userPermission);
					userAuthority.setUserRight(userRight);
					userAuthority.setEnabled(false);
					
					prototype.getUserAuthorities().add(userAuthority);
				}
			}
			
		}

		return prototype;
	}
}
