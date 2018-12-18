package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserGroup;

public class EntityUserGroupConverter implements EntityConverter<UserGroup, UserGroup> {

	@Autowired
	private ModelService modelService;

	@Override
	public UserGroup convert(UserGroup source) throws ConverterException {

		UserGroup prototype;
		try {
			
			if (source.getUuid() != null) {

				UserGroup exists = modelService.findOneEntityByUuid(source.getUuid(), UserGroup.class);

				if (exists != null) {
					prototype = exists;
				}
				else {
					prototype = modelService.create(UserGroup.class);
				}
			}
			else {
				prototype = modelService.create(UserGroup.class);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

		return convert(source, prototype);
	}

	public List<UserGroup> convert(List<UserGroup> sources) throws ConverterException {
		List<UserGroup> convertedList = new ArrayList<UserGroup>();
		try {
			for (UserGroup source : sources) {
				convertedList.add(convert(source));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), this);
		}
		return convertedList;
	}

	private UserGroup convert(UserGroup source, UserGroup prototype) {

		prototype.setId(source.getId());
		prototype.setLastModifiedDate(new Date());

		if (source.getUserAuthorities() != null) {
			prototype.setUserAuthorities(source.getUserAuthorities());
		}

		if (source.getUserGroupFields() != null) {
			prototype.setUserGroupFields(source.getUserGroupFields());
		}

//		prototype.getUserGroupFields().clear();
//		for (UserGroupField userGroupLang : source.getUserGroupFields()) {
//			if (userGroupLang.getLanguage().getUuid() != null) {
//				
//				Map<String, Object> properties = new HashMap<String, Object>();
//				properties.put(Language.UUID, userGroupLang.getLanguage().getUuid());
//				
//				Language language = modelService.findOneEntityByProperties(properties, Language.class);
//				
//				if (language != null) {
//					userGroupLang.setLanguage(language);
//					userGroupLang.setUserGroup(prototype);
//					prototype.getUserGroupFields().add(userGroupLang);
//				}
//			} else if (StringUtils.isNotEmpty(userGroupLang.getLanguage().getId())) {
//				
//				Map<String, Object> properties = new HashMap<String, Object>();
//				properties.put(Language.ID, userGroupLang.getLanguage().getId());
//				
//				Language language = modelService.findOneEntityByProperties(properties, Language.class);
//				
//				if (language != null) {
//					userGroupLang.setLanguage(language);
//					userGroupLang.setUserGroup(prototype);
//					prototype.getUserGroupFields().add(userGroupLang);
//				}
//			}
//		}
//
//		// Process User Authorities
//		Hibernate.initialize(prototype.getUserAuthorities());
//		for (UserAuthority userAuthority : prototype.getUserAuthorities()) {
//			Hibernate.initialize(userAuthority.getUserPermission());
//			Hibernate.initialize(userAuthority.getUserRight());
//		}
//		if (prototype.getUserAuthorities().isEmpty()) {
//			
//			Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
//			sorts.put(UserPermission.SORT, Sort.Direction.ASC);
//			
//			List<UserPermission> userPermissions = modelService.findBySorts(sorts, UserGroup.class);
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
//		}
//		List<UserAuthority> newAuthorities = new ArrayList<UserAuthority>();
//		for (UserAuthority sourceUserAuthority : source.getUserAuthorities()) {
//			
//			boolean addNewAuthority = true;
//			for (int i = 0; i < prototype.getUserAuthorities().size(); i++) {
//				if (sourceUserAuthority.getUserPermission().getUuid()
//						.equals(prototype.getUserAuthorities().get(i).getUserPermission().getUuid())
//						&& sourceUserAuthority.getUserRight().getUuid()
//								.equals(prototype.getUserAuthorities().get(i).getUserRight().getUuid())) {
//					prototype.getUserAuthorities().get(i).setEnabled(sourceUserAuthority.getEnabled());
//					addNewAuthority = false;
//				}
//			}
//			
//			if (addNewAuthority) {
//				
//				Map<String, Object> properties = new HashMap<String, Object>();
//				properties.put(UserPermission.UUID, sourceUserAuthority.getUserPermission().getUuid());
//				UserPermission userPermission = modelService.findOneEntityByProperties(properties, UserPermission.class);
//				
//				properties = new HashMap<String, Object>();
//				properties.put(UserPermission.UUID, sourceUserAuthority.getUserRight().getUuid());
//				UserRight userRight = modelService.findOneEntityByProperties(properties, UserRight.class);
//				
//				UserAuthority userAuthority = new UserAuthority();
//				userAuthority.setUserGroup(prototype);
//				userAuthority.setUserPermission(userPermission);
//				userAuthority.setUserRight(userRight);
//				userAuthority.setEnabled(sourceUserAuthority.getEnabled());
//
//				newAuthorities.add(userAuthority);
//			}
//		}
//		prototype.getUserAuthorities().addAll(newAuthorities);

		return prototype;
	}
}
