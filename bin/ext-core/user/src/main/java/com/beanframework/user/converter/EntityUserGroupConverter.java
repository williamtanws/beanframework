package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

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
public class EntityUserGroupConverter implements Converter<UserGroup, UserGroup> {

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private LanguageService languageService;

	@Autowired
	private UserPermissionService userPermissionService;

	@Autowired
	private UserRightService userRightService;

	@Override
	public UserGroup convert(UserGroup source) {

		Optional<UserGroup> prototype = Optional.of(userGroupService.create());
		if (source.getUuid() != null) {
			Optional<UserGroup> exists = userGroupService.findEntityByUuid(source.getUuid());
			if(exists.isPresent()) {
				prototype = exists;
			}
		}
		else if (StringUtils.isNotEmpty(source.getId())) {
			Optional<UserGroup> exists = userGroupService.findEntityById(source.getId());
			if(exists.isPresent()) {
				prototype = exists;
			}
		}

		return convert(source, prototype.get());
	}

	private UserGroup convert(UserGroup source, UserGroup prototype) {

		prototype.setId(source.getId());
		prototype.setLastModifiedDate(new Date());

		prototype.getUserGroupLangs().clear();
		for (UserGroupLang userGroupLang : source.getUserGroupLangs()) {
			if (userGroupLang.getLanguage().getUuid() != null) {
				Optional<Language> language = languageService.findEntityByUuid(userGroupLang.getLanguage().getUuid());
				if (language.isPresent()) {
					userGroupLang.setLanguage(language.get());
					userGroupLang.setUserGroup(prototype);
					prototype.getUserGroupLangs().add(userGroupLang);
				}
			} else if (StringUtils.isNotEmpty(userGroupLang.getLanguage().getId())) {
				Optional<Language> language = languageService.findEntityById(userGroupLang.getLanguage().getId());
				if (language.isPresent()) {
					userGroupLang.setLanguage(language.get());
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
				
				Optional<UserPermission> userPermission = userPermissionService.findEntityByUuid(sourceUserAuthority.getUserPermission().getUuid());
				Optional<UserRight> userRight = userRightService.findEntityByUuid(sourceUserAuthority.getUserRight().getUuid());
				
				UserAuthority userAuthority = new UserAuthority();
				userAuthority.setUserGroup(prototype);
				userAuthority.setUserPermission(userPermission.get());
				userAuthority.setUserRight(userRight.get());
				userAuthority.setEnabled(sourceUserAuthority.getEnabled());

				newAuthorities.add(userAuthority);
			}
		}
		prototype.getUserAuthorities().addAll(newAuthorities);

		return prototype;
	}
}
