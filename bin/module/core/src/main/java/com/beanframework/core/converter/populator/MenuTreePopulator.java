package com.beanframework.core.converter.populator;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.MenuDto;
import com.beanframework.core.data.MenuFieldDto;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuField;

public class MenuTreePopulator extends AbstractPopulator<Menu, MenuDto> implements Populator<Menu, MenuDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(MenuTreePopulator.class);

	@Override
	public void populate(Menu source, MenuDto target) throws PopulatorException {
		try {
			populateGeneric(source, target);
			target.setName(source.getName());
			target.setParent(populateParent(source.getParent()));
			target.setIcon(source.getIcon());
			target.setPath(source.getPath());
			target.setSort(source.getSort());
			target.setTarget(source.getTarget());
			target.setEnabled(source.getEnabled());

			Hibernate.initialize(source.getChilds());
			for (Menu child : source.getChilds()) {
				MenuDto populateChild = populateChild(child, new HashSet<UUID>());
				if (populateChild != null) {
					target.getChilds().add(populateChild);
				}
			}
			for (UUID userGroup : source.getUserGroups()) {
				target.getUserGroups().add(populateUserGroup(userGroup));
			}
			for (MenuField field : source.getFields()) {
				target.getFields().add(populateMenuField(field));
			}

			Collections.sort(target.getFields(), new Comparator<MenuFieldDto>() {
				@Override
				public int compare(MenuFieldDto o1, MenuFieldDto o2) {
					if (o1.getDynamicFieldSlot().getSort() == null)
						return o2.getDynamicFieldSlot().getSort() == null ? 0 : 1;

					if (o2.getDynamicFieldSlot().getSort() == null)
						return -1;

					return o1.getDynamicFieldSlot().getSort() - o2.getDynamicFieldSlot().getSort();
				}
			});
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	public MenuDto populateParent(Menu source) throws PopulatorException {
		if (source == null)
			return null;

		MenuDto target = new MenuDto();
		populateGeneric(source, target);
		target.setName(source.getName());
		target.setIcon(source.getIcon());
		target.setPath(source.getPath());
		target.setSort(source.getSort());
		target.setTarget(source.getTarget());
		target.setEnabled(source.getEnabled());
		for (MenuField field : source.getFields()) {
			target.getFields().add(populateMenuField(field));
		}
		return target;
	}

	public MenuDto populateChild(Menu source, Set<UUID> populated) throws PopulatorException {
		if (source == null)
			return null;

		try {
			if (populated.contains(source.getUuid()) == Boolean.FALSE) {
				populated.add(source.getUuid());
				MenuDto target = new MenuDto();
				populateGeneric(source, target);
				target.setName(source.getName());
				target.setIcon(source.getIcon());
				target.setPath(source.getPath());
				target.setSort(source.getSort());
				target.setTarget(source.getTarget());
				target.setEnabled(source.getEnabled());

				for (Menu child : source.getChilds()) {
					MenuDto populateChild = populateChild(child, populated);
					if (populateChild != null) {
						target.getChilds().add(populateChild);
					}
				}
				for (UUID userGroup : source.getUserGroups()) {
					target.getUserGroups().add(populateUserGroup(userGroup));
				}
				for (MenuField field : source.getFields()) {
					target.getFields().add(populateMenuField(field));
				}
				return target;
			} else {
				return null;
			}

		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	public MenuFieldDto populateMenuField(MenuField source) throws PopulatorException {
		if (source == null)
			return null;

		try {
			MenuFieldDto target = new MenuFieldDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(populateAuditor(source.getCreatedBy()));
			target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));

			target.setValue(source.getValue());
			target.setDynamicFieldSlot(populateDynamicFieldSlot(source.getDynamicFieldSlot()));

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}
}
