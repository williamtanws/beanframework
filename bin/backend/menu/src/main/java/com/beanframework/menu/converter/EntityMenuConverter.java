package com.beanframework.menu.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
			if (source.getId() != null) {
				prototype.setId(source.getId());
			}
			prototype.setLastModifiedDate(new Date());

			prototype.setSort(source.getSort());
			prototype.setIcon(source.getIcon());
			prototype.setPath(source.getPath());
			prototype.setTarget(source.getTarget());
			prototype.setEnabled(source.getEnabled());

			Hibernate.initialize(source.getParent());
			Hibernate.initialize(source.getChilds());
			Hibernate.initialize(source.getUserGroups());

			if (source.getParent() == null || source.getParent().getUuid() == null) {
				prototype.setParent(null);
			} else {
				Menu parent = modelService.findOneEntityByUuid(source.getParent().getUuid(), Menu.class);
				prototype.setParent(parent);
			}
			if (source.getChilds() == null || source.getChilds().isEmpty()) {
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
			if (source.getUserGroups() == null || source.getUserGroups().isEmpty()) {
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
			if (source.getMenuFields() == null || source.getMenuFields().isEmpty()) {
				prototype.setMenuFields(new ArrayList<MenuField>());
			} else {
				List<MenuField> menuFields = new ArrayList<MenuField>();
				for (MenuField menuField : source.getMenuFields()) {
					MenuField entityMenuField = modelService.findOneEntityByUuid(menuField.getUuid(), MenuField.class);
					entityMenuField.setValue(menuField.getValue());
					menuFields.add(entityMenuField);
				}
				prototype.setMenuFields(menuFields);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return prototype;
	}

}
