package com.beanframework.menu.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuField;
import com.beanframework.user.domain.UserGroup;

public class EntityMenuConverter implements EntityConverter<Menu, Menu> {

	@Autowired
	private ModelService modelService;

	@Override
	public Menu convert(Menu source) throws ConverterException {

		Menu prototype;
		try {

			if (source.getUuid() != null) {

				Menu exists = modelService.findOneEntityByUuid(source.getUuid(), Menu.class);

				if (exists != null) {
					prototype = exists;
				} else {
					prototype = modelService.create(Menu.class);
				}
			} else {
				prototype = modelService.create(Menu.class);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

		return convert(source, prototype);
	}

	public List<Menu> convert(List<Menu> sources) throws ConverterException {
		List<Menu> convertedList = new ArrayList<Menu>();
		for (Menu source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private Menu convert(Menu source, Menu prototype) throws ConverterException {

		try {
			

			if (StringUtils.isNotBlank(source.getId()) && StringUtils.equals(source.getId(), prototype.getId()) == false)
				prototype.setId(source.getId());

			if (prototype.getSort() == source.getSort() == false)
				prototype.setSort(source.getSort());

			if (StringUtils.equals(prototype.getIcon(), source.getIcon()) == false)
				prototype.setIcon(source.getIcon());

			if (StringUtils.equals(prototype.getPath(), source.getPath()) == false)
				prototype.setPath(source.getPath());

			if (prototype.getTarget() == source.getTarget() == false)
				prototype.setTarget(source.getTarget());

			if (prototype.getEnabled() == source.getEnabled() == false)
				prototype.setEnabled(source.getEnabled());

			Hibernate.initialize(source.getParent());
			Hibernate.initialize(source.getChilds());
			Hibernate.initialize(source.getUserGroups());

			// Parent
			if (source.getParent() == null || source.getParent().getUuid() == null) {
				if (prototype.getParent() != null)
					prototype.setParent(null);
			} else {
				if (prototype.getParent().getUuid() != source.getParent().getUuid()) {
					Menu parent = modelService.findOneEntityByUuid(source.getParent().getUuid(), Menu.class);
					prototype.setParent(parent);
				}
			}
			// Child
			if (source.getChilds() == null || source.getChilds().isEmpty()) {
				if (prototype.getChilds().isEmpty() == false)
					prototype.setChilds(new ArrayList<Menu>());

			} else {
				List<Menu> childs = new ArrayList<Menu>();
				for (Menu child : source.getChilds()) {
					Menu entityMenu = modelService.findOneEntityByUuid(child.getUuid(), Menu.class);
					if (entityMenu != null)
						childs.add(entityMenu);
				}
				prototype.setChilds(childs);
			}
			// User Group
			if (source.getUserGroups() == null || source.getUserGroups().isEmpty()) {
				if (prototype.getUserGroups().isEmpty() == false)
					prototype.setUserGroups(new ArrayList<UserGroup>());
			} else {
				List<UserGroup> userGroups = new ArrayList<UserGroup>();
				for (UserGroup userGroup : source.getUserGroups()) {
					UserGroup entityUserGroup = modelService.findOneEntityByUuid(userGroup.getUuid(), UserGroup.class);
					if (entityUserGroup != null)
						userGroups.add(entityUserGroup);
				}
				prototype.setUserGroups(userGroups);
			}
			// Field
			if (source.getFields() == null || source.getFields().isEmpty()) {
				if (prototype.getFields().isEmpty() == false)
					prototype.setFields(new ArrayList<MenuField>());
			} else {
				List<MenuField> menuFields = new ArrayList<MenuField>();
				for (MenuField menuField : source.getFields()) {
					MenuField entityMenuField = modelService.findOneEntityByUuid(menuField.getUuid(), MenuField.class);
					entityMenuField.setValue(menuField.getValue());
					menuFields.add(entityMenuField);
				}
				prototype.setFields(menuFields);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return prototype;
	}

}
