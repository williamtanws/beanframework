package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserAuthorityDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.core.data.UserGroupAttributeDto;
import com.beanframework.user.domain.UserGroup;

public class EntityUserGroupConverter implements EntityConverter<UserGroupDto, UserGroup> {

	@Autowired
	private ModelService modelService;

	@Override
	public UserGroup convert(UserGroupDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				UserGroup prototype = modelService.findOneByUuid(source.getUuid(), UserGroup.class);

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

			// Attribute
			if (source.getAttributes() != null && source.getAttributes().isEmpty() == Boolean.FALSE) {
				for (int i = 0; i < prototype.getAttributes().size(); i++) {
					for (UserGroupAttributeDto sourceField : source.getAttributes()) {

						if (prototype.getAttributes().get(i).getDynamicFieldSlot().equals(sourceField.getDynamicFieldSlot().getUuid())) {
							if (StringUtils.equals(StringUtils.stripToNull(sourceField.getValue()), prototype.getAttributes().get(i).getValue()) == Boolean.FALSE) {
								prototype.getAttributes().get(i).setValue(StringUtils.stripToNull(sourceField.getValue()));

								prototype.getAttributes().get(i).setLastModifiedDate(lastModifiedDate);
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
								prototype.getUserAuthorities().get(i).setEnabled(sourceUserAuthority.getEnabled() == null ? Boolean.FALSE : sourceUserAuthority.getEnabled());
								prototype.setLastModifiedDate(lastModifiedDate);
							}
						}
					}
				}
			}

			// User Groups
			if (source.getSelectedUserGroupUuids() != null) {

				Iterator<UUID> itr = prototype.getUserGroups().iterator();
				while (itr.hasNext()) {
					UUID model = itr.next();

					boolean remove = true;
					for (int i = 0; i < source.getSelectedUserGroupUuids().length; i++) {
						if (model.equals(UUID.fromString(source.getSelectedUserGroupUuids()[i]))) {
							remove = false;
						}
					}
					if (remove) {
						itr.remove();
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}

				for (int i = 0; i < source.getSelectedUserGroupUuids().length; i++) {

					boolean add = true;
					itr = prototype.getUserGroups().iterator();
					while (itr.hasNext()) {
						UUID model = itr.next();

						if (model.equals(UUID.fromString(source.getSelectedUserGroupUuids()[i]))) {
							add = false;
						}
					}

					if (add) {
						UserGroup entity = modelService.findOneByUuid(UUID.fromString(source.getSelectedUserGroupUuids()[i]), UserGroup.class);
						
						// Prevent add self into groups
						if (entity != null && StringUtils.equals(prototype.getId(), entity.getId()) == Boolean.FALSE) {
							prototype.getUserGroups().add(entity.getUuid());
							prototype.setLastModifiedDate(lastModifiedDate);
						}
					}
				}
			} else if (prototype.getUserGroups() != null && prototype.getUserGroups().isEmpty() == false) {
				for (final Iterator<UUID> itr = prototype.getUserGroups().iterator(); itr.hasNext();) {
					itr.next();
					itr.remove();
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);

		}

		return prototype;
	}
}
