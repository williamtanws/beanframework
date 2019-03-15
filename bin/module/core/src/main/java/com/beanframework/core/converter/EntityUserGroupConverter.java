package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.common.utils.BooleanUtils;
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

				UserGroup prototype = modelService.findOneEntityByUuid(source.getUuid(), true, UserGroup.class);

				if (prototype != null) {
					return convertDto(source, prototype);
				}
			}

			return convert(source, modelService.create(UserGroup.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

	}

	public List<UserGroup> convert(List<UserGroupDto> sources, EntityConverterContext context) throws ConverterException {
		List<UserGroup> convertedList = new ArrayList<UserGroup>();
		try {
			for (UserGroupDto source : sources) {
				convertedList.add(convert(source, context));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}

	private UserGroup convertDto(UserGroupDto source, UserGroup prototype) throws ConverterException {

		try {
			Date lastModifiedDate = new Date();

			if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == false) {
				prototype.setId(StringUtils.stripToNull(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == false) {
				prototype.setName(StringUtils.stripToNull(source.getName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			// Field
			if (source.getFields() != null && source.getFields().isEmpty() == false) {
				for (int i = 0; i < prototype.getFields().size(); i++) {
					for (UserGroupFieldDto sourceField : source.getFields()) {

						if (prototype.getFields().get(i).getDynamicField().getUuid().equals(sourceField.getDynamicField().getUuid())) {
							if (StringUtils.equals(StringUtils.stripToNull(sourceField.getValue()), prototype.getFields().get(i).getValue()) == false) {
								prototype.getFields().get(i).setValue(StringUtils.stripToNull(sourceField.getValue()));

								prototype.getFields().get(i).setLastModifiedDate(lastModifiedDate);
								prototype.setLastModifiedDate(lastModifiedDate);
							}
							if (sourceField.getSort() == prototype.getFields().get(i).getSort() == false) {
								prototype.getFields().get(i).setSort(sourceField.getSort());

								prototype.getFields().get(i).setLastModifiedDate(lastModifiedDate);
								prototype.setLastModifiedDate(lastModifiedDate);
							}
						}
					}
				}
			}

			// User Authority
			if (source.getUserAuthorities() != null && source.getUserAuthorities().isEmpty() == false) {
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

			// UserGroup
			if (source.getTableUserGroups() != null) {

				for (int i = 0; i < source.getTableUserGroups().length; i++) {

					boolean remove = true;
					if (source.getTableSelectedUserGroups() != null && source.getTableSelectedUserGroups().length > i && BooleanUtils.parseBoolean(source.getTableSelectedUserGroups()[i])) {
						remove = false;
					}

					if (remove) {
						for (Iterator<UserGroup> userGroupsIterator = prototype.getUserGroups().listIterator(); userGroupsIterator.hasNext();) {
							if (userGroupsIterator.next().getUuid().equals(UUID.fromString(source.getTableUserGroups()[i]))) {
								userGroupsIterator.remove();
								prototype.setLastModifiedDate(lastModifiedDate);
							}
						}
					} else {
						boolean add = true;
						for (Iterator<UserGroup> userGroupsIterator = prototype.getUserGroups().listIterator(); userGroupsIterator.hasNext();) {
							if (userGroupsIterator.next().getUuid().equals(UUID.fromString(source.getTableUserGroups()[i]))) {
								add = false;
							}
						}

						if (add) {
							UserGroup entityUserGroups = modelService.findOneEntityByUuid(UUID.fromString(source.getTableUserGroups()[i]), false, UserGroup.class);
							prototype.getUserGroups().add(entityUserGroups);
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
