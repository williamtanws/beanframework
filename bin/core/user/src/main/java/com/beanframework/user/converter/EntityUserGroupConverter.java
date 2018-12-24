package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;

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
				} else {
					prototype = modelService.create(UserGroup.class);
				}
			} else {
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

	private UserGroup convert(UserGroup source, UserGroup prototype) throws ConverterException {

		try {
			if (source.getId() != null) {
				prototype.setId(source.getId());
			}
			prototype.setLastModifiedDate(new Date());

			if (source.getUserGroups() == null) {
				prototype.setUserGroups(new ArrayList<UserGroup>());
			} else {
				List<UserGroup> childs = new ArrayList<UserGroup>();
				for (UserGroup userGroup : source.getUserGroups()) {
					UserGroup entityUserGroup = modelService.findOneEntityByUuid(userGroup.getUuid(), UserGroup.class);
					if (entityUserGroup != null)
						childs.add(entityUserGroup);
				}
				prototype.setUserGroups(childs);
			}
			if (source.getUserAuthorities() == null || source.getUserAuthorities().isEmpty()) {
				prototype.setUserAuthorities(new ArrayList<UserAuthority>());
			} else {
				prototype.setUserAuthorities(source.getUserAuthorities());
			}
			if (source.getUserGroupFields() == null || source.getUserGroupFields().isEmpty()) {
				prototype.setUserGroupFields(new ArrayList<UserGroupField>());
			} else {
				List<UserGroupField> userGroupFields = new ArrayList<UserGroupField>();
				for (UserGroupField userGroupField : source.getUserGroupFields()) {
					UserGroupField entityUserGroupField = modelService.findOneEntityByUuid(userGroupField.getUuid(),
							UserGroupField.class);
					entityUserGroupField.setValue(userGroupField.getValue());
					userGroupFields.add(entityUserGroupField);
				}
				prototype.setUserGroupFields(userGroupFields);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);

		}

		return prototype;
	}
}
