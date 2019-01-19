package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.backoffice.data.MenuDto;
import com.beanframework.backoffice.data.MenuFieldDto;
import com.beanframework.backoffice.data.UserGroupDto;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.menu.domain.Menu;
import com.beanframework.user.domain.UserGroup;

public class EntityMenuConverter implements EntityConverter<MenuDto, Menu> {

	@Autowired
	private ModelService modelService;

	@Override
	public Menu convert(MenuDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {

				Menu prototype = modelService.findOneEntityByUuid(source.getUuid(), true, Menu.class);

				if (prototype != null) {
					return convert(source, prototype);
				}
			}

			return convert(source, modelService.create(Menu.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

	}

	public List<Menu> convert(List<MenuDto> sources) throws ConverterException {
		List<Menu> convertedList = new ArrayList<Menu>();
		for (MenuDto source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private Menu convert(MenuDto source, Menu prototype) throws ConverterException {

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

			if (source.getSort() == null) {
				if (prototype.getSort() != null) {
					prototype.setSort(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				if (prototype.getSort() == null || prototype.getSort().equals(source.getSort()) == false) {
					prototype.setSort(source.getSort());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getIcon()), prototype.getIcon()) == false) {
				prototype.setIcon(StringUtils.stripToNull(source.getIcon()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getPath()), prototype.getPath()) == false) {
				prototype.setPath(StringUtils.stripToNull(source.getPath()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (prototype.getTarget() == source.getTarget() == false) {
				prototype.setTarget(source.getTarget());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getEnabled() == null) {
				if (prototype.getEnabled() != null) {
					prototype.setEnabled(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				if (prototype.getEnabled() == null || prototype.getEnabled().equals(source.getEnabled()) == false) {
					prototype.setEnabled(source.getEnabled());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			// Field
			if (source.getFields() != null && source.getFields().isEmpty() == false) {
				for (int i = 0; i < prototype.getFields().size(); i++) {
					for (MenuFieldDto sourceField : source.getFields()) {
						if (StringUtils.equals(StringUtils.stripToNull(sourceField.getValue()), prototype.getFields().get(i).getValue()) == false) {
							prototype.getFields().get(i).setValue(StringUtils.stripToNull(sourceField.getValue()));

							prototype.getFields().get(i).setLastModifiedDate(lastModifiedDate);
							prototype.setLastModifiedDate(lastModifiedDate);
						}
					}
				}
			}

			// User Group
			if (source.getUserGroups() == null || source.getUserGroups().isEmpty()) {
				if (prototype.getUserGroups().isEmpty()) {
					prototype.setUserGroups(new ArrayList<UserGroup>());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			Iterator<UserGroup> itr = prototype.getUserGroups().iterator();
			while (itr.hasNext()) {
				UserGroup userGroup = itr.next();

				boolean remove = true;
				for (UserGroupDto sourceUserGroup : source.getUserGroups()) {
					if (userGroup.getUuid().equals(sourceUserGroup.getUuid())) {
						remove = false;
					}
				}

				if (remove) {
					itr.remove();
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			for (UserGroupDto sourceUserGroup : source.getUserGroups()) {

				boolean add = true;
				for (UserGroup userGroup : prototype.getUserGroups()) {
					if (sourceUserGroup.getUuid().equals(userGroup.getUuid()))
						add = false;
				}

				if (add) {
					UserGroup entityUserGroup = modelService.findOneEntityByUuid(sourceUserGroup.getUuid(), true, UserGroup.class);
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
