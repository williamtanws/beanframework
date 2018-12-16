package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.language.domain.Language;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupLang;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;


public class EntityUserGroupConverter implements EntityConverter<UserGroup, UserGroup> {
	
	@Autowired
	private ModelService modelService;

	@Override
	public UserGroup convert(UserGroup source) {

		UserGroup prototype = modelService.create(UserGroup.class);
		if (source.getUuid() != null) {
			
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(UserGroup.UUID, source.getUuid());
			UserGroup existingUserGroup = modelService.findOneEntityByProperties(properties, UserGroup.class);
			
			if(existingUserGroup != null) {
				prototype = existingUserGroup;
			}
		}
		else if (StringUtils.isNotEmpty(source.getId())) {
			
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(UserGroup.ID, source.getId());
			UserGroup existingUserGroup = modelService.findOneEntityByProperties(properties, UserGroup.class);
			
			if(existingUserGroup != null) {
				prototype = existingUserGroup;
			}
		}

		return convert(source, prototype);
	}

	private UserGroup convert(UserGroup source, UserGroup prototype) {

		prototype.setId(source.getId());
		prototype.setLastModifiedDate(new Date());

		prototype.getUserGroupLangs().clear();
		for (UserGroupLang userGroupLang : source.getUserGroupLangs()) {
			if (userGroupLang.getLanguage().getUuid() != null) {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Language.UUID, userGroupLang.getLanguage().getUuid());
				
				Language language = modelService.findOneEntityByProperties(properties, Language.class);
				
				if (language != null) {
					userGroupLang.setLanguage(language);
					userGroupLang.setUserGroup(prototype);
					prototype.getUserGroupLangs().add(userGroupLang);
				}
			} else if (StringUtils.isNotEmpty(userGroupLang.getLanguage().getId())) {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Language.ID, userGroupLang.getLanguage().getId());
				
				Language language = modelService.findOneEntityByProperties(properties, Language.class);
				
				if (language != null) {
					userGroupLang.setLanguage(language);
					userGroupLang.setUserGroup(prototype);
					prototype.getUserGroupLangs().add(userGroupLang);
				}
			}
		}

		// Process User Authorities
		Hibernate.initialize(prototype.getUserAuthorities());
		for (UserAuthority userAuthority : prototype.getUserAuthorities()) {
			Hibernate.initialize(userAuthority.getUserPermission());
			Hibernate.initialize(userAuthority.getUserRight());
		}
		if (prototype.getUserAuthorities().isEmpty()) {
			
			Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
			sorts.put(UserPermission.SORT, Sort.Direction.ASC);
			
			List<UserPermission> userPermissions = modelService.findBySorts(sorts, UserGroup.class);
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
		List<UserAuthority> newAuthorities = new ArrayList<UserAuthority>();
		for (UserAuthority sourceUserAuthority : source.getUserAuthorities()) {
			
			boolean addNewAuthority = true;
			for (int i = 0; i < prototype.getUserAuthorities().size(); i++) {
				if (sourceUserAuthority.getUserPermission().getUuid()
						.equals(prototype.getUserAuthorities().get(i).getUserPermission().getUuid())
						&& sourceUserAuthority.getUserRight().getUuid()
								.equals(prototype.getUserAuthorities().get(i).getUserRight().getUuid())) {
					prototype.getUserAuthorities().get(i).setEnabled(sourceUserAuthority.getEnabled());
					addNewAuthority = false;
				}
			}
			
			if (addNewAuthority) {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserPermission.UUID, sourceUserAuthority.getUserPermission().getUuid());
				UserPermission userPermission = modelService.findOneEntityByProperties(properties, UserPermission.class);
				
				properties = new HashMap<String, Object>();
				properties.put(UserPermission.UUID, sourceUserAuthority.getUserRight().getUuid());
				UserRight userRight = modelService.findOneEntityByProperties(properties, UserRight.class);
				
				UserAuthority userAuthority = new UserAuthority();
				userAuthority.setUserGroup(prototype);
				userAuthority.setUserPermission(userPermission);
				userAuthority.setUserRight(userRight);
				userAuthority.setEnabled(sourceUserAuthority.getEnabled());

				newAuthorities.add(userAuthority);
			}
		}
		prototype.getUserAuthorities().addAll(newAuthorities);

		return prototype;
	}
}
