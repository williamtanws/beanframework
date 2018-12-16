package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.language.domain.Language;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupLang;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;


public class DtoUserGroupConverter implements DtoConverter<UserGroup, UserGroup> {
	
	@Autowired
	private ModelService modelService;
	
	@Autowired
	private DtoUserAuthorityConverter dtoUserAuthorityConverter;
	
	@Autowired
	private DtoUserGroupLangConverter dtoUserGroupLangConverter;

	@Override
	public UserGroup convert(UserGroup source) {
		return convert(source, modelService.create(UserGroup.class));
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
		
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Language.SORT, Sort.Direction.ASC);
		
		List<Language> languages = modelService.findBySorts(sorts, Language.class);
		
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
			
			Map<String, Sort.Direction> permissionSorts = new HashMap<String, Sort.Direction>();
			permissionSorts.put(UserPermission.SORT, Sort.Direction.ASC);
			
			List<UserPermission> userPermissions = modelService.findBySorts(permissionSorts, UserPermission.class);
			
			List<UserRight> userRights = modelService.findBySorts(sorts, UserRight.class);
			
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
