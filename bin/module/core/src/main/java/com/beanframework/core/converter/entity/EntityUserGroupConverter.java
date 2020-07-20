package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserAuthorityDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.core.data.UserGroupFieldDto;
import com.beanframework.user.domain.UserGroup;

public class EntityUserGroupConverter implements EntityConverter<UserGroupDto, UserGroup> {

	@Autowired
	private ModelService modelService;

	@Override
	public UserGroup convert(UserGroupDto source, EntityConverterContext context) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserGroup.UUID, source.getUuid());
				UserGroup prototype = modelService.findOneByProperties(properties, UserGroup.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}

			return convertToEntity(source, modelService.create(UserGroup.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

	}

	private UserGroup convertToEntity(UserGroupDto source, UserGroup prototype) throws ConverterException {

		try {
			Date lastModifiedDate = new Date();

			if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
				prototype.setId(StringUtils.stripToNull(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == Boolean.FALSE) {
				prototype.setName(StringUtils.stripToNull(source.getName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			// Field
			if (source.getFields() != null && source.getFields().isEmpty() == Boolean.FALSE) {
				for (int i = 0; i < prototype.getFields().size(); i++) {
					for (UserGroupFieldDto sourceField : source.getFields()) {

						if (prototype.getFields().get(i).getDynamicFieldSlot().getUuid().equals(sourceField.getDynamicFieldSlot().getUuid())) {
							if (StringUtils.equals(StringUtils.stripToNull(sourceField.getValue()), prototype.getFields().get(i).getValue()) == Boolean.FALSE) {
								prototype.getFields().get(i).setValue(StringUtils.stripToNull(sourceField.getValue()));

								prototype.getFields().get(i).setLastModifiedDate(lastModifiedDate);
								prototype.setLastModifiedDate(lastModifiedDate);
							}
						}
					}
				}
			}

			// User Authority
			if (source.getUserAuthorities() != null && source.getUserAuthorities().isEmpty() == Boolean.FALSE) {
				for (int i = 0; i < prototype.getUserAuthorities().size(); i++) {
					for (UserAuthorityDto sourceUserAuthority : source.getUserAuthorities()) {
						if (prototype.getUserAuthorities().get(i).getUuid().equals(sourceUserAuthority.getUuid())) {
							if (sourceUserAuthority.getEnabled() != prototype.getUserAuthorities().get(i).getEnabled()) {
								prototype.getUserAuthorities().get(i).setEnabled(sourceUserAuthority.getEnabled());
								prototype.setLastModifiedDate(lastModifiedDate);
							}
						}
					}
				}
			}

			// Childs
			if (source.getSelectedUserGroups() != null) {

				Iterator<UserGroup> userGroupsIterator = prototype.getUserGroups().iterator();
				while (userGroupsIterator.hasNext()) {
					UserGroup userGroup = userGroupsIterator.next();

					boolean remove = true;
					for (int i = 0; i < source.getSelectedUserGroups().length; i++) {
						if (userGroup.getUuid().equals(UUID.fromString(source.getSelectedUserGroups()[i]))) {
							remove = false;
						}
					}
					if (remove) {
						userGroupsIterator.remove();
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}

				for (int i = 0; i < source.getSelectedUserGroups().length; i++) {

					boolean add = true;
					userGroupsIterator = prototype.getUserGroups().iterator();
					while (userGroupsIterator.hasNext()) {
						UserGroup userGroup = userGroupsIterator.next();
						
						if (userGroup.getUuid().equals(UUID.fromString(source.getSelectedUserGroups()[i]))) {
							add = false;
						}
					}

					if (add) {
						UserGroup userGroup = modelService.findOneByUuid(UUID.fromString(source.getSelectedUserGroups()[i]), UserGroup.class);
						if (userGroup != null) {
							prototype.getUserGroups().add(userGroup);
							prototype.setLastModifiedDate(lastModifiedDate);
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