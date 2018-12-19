package com.beanframework.menu.converter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuNavigation;
import com.beanframework.user.converter.DtoUserGroupConverter;

@Component
public class DtoMenuNavigationConverter implements DtoConverter<Menu, MenuNavigation> {

	@Autowired
	private DtoUserGroupConverter dtoUserGroupConverter;

	@Autowired
	private DtoMenuFieldConverter dtoMenuNavigationFieldConverter;

	@Override
	public MenuNavigation convert(Menu source) {
		return convert(source, new MenuNavigation());
	}

	public List<MenuNavigation> convert(List<Menu> sources) {
		List<MenuNavigation> convertedList = new ArrayList<MenuNavigation>();
		for (Menu source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private MenuNavigation convert(Menu source, MenuNavigation prototype) {
		return convert(source, prototype, true);
	}

	private MenuNavigation convert(Menu source, MenuNavigation prototype, boolean initializeParent) {
		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setIcon(source.getIcon());
		prototype.setPath(source.getPath());
		prototype.setSort(source.getSort());
		prototype.setTarget(source.getTarget());
		prototype.setEnabled(source.getEnabled());
		if (source.getChilds() != null && source.getChilds().isEmpty() == false) {
			prototype.setNavigationChilds(this.convert(source.getChilds()));
		}
		Hibernate.initialize(source.getUserGroups());
		prototype.setUserGroups(dtoUserGroupConverter.convert(source.getUserGroups()));
		Hibernate.initialize(source.getMenuFields());
		prototype.setMenuFields(dtoMenuNavigationFieldConverter.convert(source.getMenuFields()));

		return prototype;
	}
}
