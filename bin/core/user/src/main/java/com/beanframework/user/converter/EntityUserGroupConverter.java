package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

		try {

			if (source.getUuid() != null) {

				UserGroup prototype = modelService.findOneEntityByUuid(source.getUuid(), UserGroup.class);

				if (prototype != null) {
					return convert(source, prototype);
				}
			}

			return convert(source, modelService.create(UserGroup.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

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
					for (UserGroupField sourceField : source.getFields()) {
						if (StringUtils.equals(StringUtils.stripToNull(sourceField.getValue()), prototype.getFields().get(i).getValue()) == false) {
							prototype.getFields().get(i).setValue(StringUtils.stripToNull(sourceField.getValue()));

							prototype.getFields().get(i).setLastModifiedDate(lastModifiedDate);
							prototype.setLastModifiedDate(lastModifiedDate);
						}
					}
				}
			}

			// User Authority
			if (source.getUserAuthorities() != null && source.getUserAuthorities().isEmpty() == false) {
				for (int i = 0; i < prototype.getUserAuthorities().size(); i++) {
					for (UserAuthority sourceUserAuthority : source.getUserAuthorities()) {
						if (prototype.getUserAuthorities().get(i).getUuid().equals(sourceUserAuthority.getUuid())) {
							if (sourceUserAuthority.getEnabled() != prototype.getUserAuthorities().get(i).getEnabled()) {
								prototype.getUserAuthorities().get(i).setEnabled(sourceUserAuthority.getEnabled());
								prototype.setLastModifiedDate(lastModifiedDate);
							}
						}
					}
				}
			}

			// User Group
			if (source.getUserGroups() == null || source.getUserGroups().isEmpty()) {
				if (prototype.getUserGroups() == null || prototype.getUserGroups().isEmpty()) {
					prototype.setUserGroups(new ArrayList<UserGroup>());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			Iterator<UserGroup> itr = prototype.getUserGroups().iterator();
			while (itr.hasNext()) {
				UserGroup userGroup = itr.next();

				boolean remove = true;
				for (UserGroup sourceUserGroup : source.getUserGroups()) {
					if (userGroup.getUuid().equals(sourceUserGroup.getUuid())) {
						remove = false;
					}
				}

				if (remove) {
					itr.remove();
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			for (UserGroup sourceUserGroup : source.getUserGroups()) {

				boolean add = true;
				for (UserGroup userGroup : prototype.getUserGroups()) {
					if (sourceUserGroup.getUuid().equals(userGroup.getUuid()))
						add = false;
				}

				if (add) {
					UserGroup entityUserGroup = modelService.findOneEntityByUuid(sourceUserGroup.getUuid(), UserGroup.class);
					if (entityUserGroup != null) {
						prototype.getUserGroups().add(entityUserGroup);
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);

		}

		return prototype;
	}
}
