package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

			if (StringUtils.isNotBlank(source.getId()) && StringUtils.equals(source.getId(), prototype.getId()) == false)
				prototype.setId(StringUtils.strip(source.getId()));

			if (StringUtils.equals(source.getName(), prototype.getName()) == false)
				prototype.setName(StringUtils.strip(source.getName()));

			// Dynamic Field
			if (source.getFields() != null && source.getFields().isEmpty() == false) {
				for (int i = 0; i < prototype.getFields().size(); i++) {
					for (UserGroupField sourceUserGroupField : source.getFields()) {
						if (prototype.getFields().get(i).getUuid().equals(sourceUserGroupField.getUuid())) {
							prototype.getFields().get(i).setValue(StringUtils.strip(sourceUserGroupField.getValue()));
						}
					}
				}
			}

			// User Group
			if (source.getUserGroups() == null || source.getUserGroups().isEmpty()) {
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

			// User Authority
			if (source.getUserAuthorities() != null && source.getUserAuthorities().isEmpty() == false) {
				for (int i = 0; i < prototype.getUserAuthorities().size(); i++) {
					for (UserAuthority sourceUserAuthority : source.getUserAuthorities()) {
						if (prototype.getUserAuthorities().get(i).getUuid().equals(sourceUserAuthority.getUuid())) {
							prototype.getUserAuthorities().get(i).setEnabled(sourceUserAuthority.getEnabled());
						}
					}
				}
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);

		}

		return prototype;
	}
}
