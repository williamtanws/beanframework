package com.beanframework.menu.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;
import com.beanframework.menu.MenuConstants;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuAttribute;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private ModelService modelService;

	@Value(MenuConstants.CONFIGURATION_DYNAMIC_FIELD_TEMPLATE)
	private String CONFIGURATION_DYNAMIC_FIELD_TEMPLATE;

	@Transactional
	@Override
	public void savePosition(UUID fromUuid, UUID toUuid, int toIndex) throws Exception {

		if (toUuid == null) {
			// Set to root Menu
			setParentNullAndSortByUuid(fromUuid, toIndex);

			// Reset position
			List<Menu> toMenuChilds = findByParentNullOrderBySort();
			List<Menu> menus = changePosition(toMenuChilds, fromUuid, toIndex);
			for (Menu menu : menus) {
				modelService.saveEntity(menu);
			}
		} else {
			updateParentByUuid(fromUuid, toUuid, toIndex);

			List<Menu> toMenuChilds = findByParentUuidOrderBySort(toUuid);

			List<Menu> menus = changePosition(toMenuChilds, fromUuid, toIndex);
			for (Menu menu : menus) {
				modelService.saveEntity(menu);
			}
		}
	}

	// Set to root menu
	private void setParentNullAndSortByUuid(UUID fromUuid, int toIndex) throws Exception {

		// Remove child menu from parent menu
		Menu menu = modelService.findOneByUuid(fromUuid, Menu.class);
		if (menu.getParent() != null) {
			Menu parent = menu.getParent();
			Hibernate.initialize(parent.getChilds());
			if (parent.getChilds() != null) {
				for (int i = 0; i < parent.getChilds().size(); i++) {
					if (parent.getChilds().get(i).getUuid() == menu.getUuid()) {
						parent.getChilds().remove(i);
						modelService.saveEntity(parent);
						break;
					}
				}
			}
		}

		// Set to root menu
		menu.setParent(null);
		menu.setSort(toIndex);
		modelService.saveEntity(menu);
	}

	private List<Menu> findByParentNullOrderBySort() throws Exception {

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Menu.PARENT, null);

		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Menu.SORT, Sort.Direction.ASC);

		return modelService.findByPropertiesBySortByResult(properties, sorts, null, null, Menu.class);
	}

	private void updateParentByUuid(UUID fromUuid, UUID toUuid, int toIndex) throws Exception {
		Menu menu = modelService.findOneByUuid(fromUuid, Menu.class);
		Menu parent = modelService.findOneByUuid(toUuid, Menu.class);
		Hibernate.initialize(parent.getChilds());
		menu.setSort(toIndex);
		menu.setParent(parent);
		parent.getChilds().add(menu);
		modelService.saveEntity(parent);
	}

	private List<Menu> findByParentUuidOrderBySort(UUID toUuid) throws Exception {

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Menu.PARENT + "." + Menu.UUID, toUuid);

		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Menu.SORT, Sort.Direction.ASC);

		return modelService.findByPropertiesBySortByResult(properties, sorts, null, null, Menu.class);
	}

	private List<Menu> changePosition(List<Menu> menuList, UUID fromId, int toIndex) {

		int topIndex;
		int bottomIndex;

		int fromIndex = 0;
		for (int i = 0; i < menuList.size(); i++) {
			if (menuList.get(i).getUuid().equals(fromId)) {
				fromIndex = i;
			}
		}

		// Move left to right
		if (fromIndex < toIndex) {
			topIndex = fromIndex;
			bottomIndex = toIndex;

			int currentIndex = topIndex;

			while (currentIndex != bottomIndex) {
				Collections.swap(menuList, currentIndex, currentIndex + 1);
				currentIndex++;
			}
		} else {
			// Move right to left
			topIndex = toIndex;
			bottomIndex = fromIndex;

			int currentIndex = bottomIndex;

			while (currentIndex != topIndex) {
				Collections.swap(menuList, currentIndex, currentIndex - 1);
				currentIndex--;
			}
		}

		for (int i = 0; i < menuList.size(); i++) {
			menuList.get(i).setSort(i);
		}

		return menuList;
	}

	@Override
	public void generateMenuAttribute(Menu model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Configuration.ID, CONFIGURATION_DYNAMIC_FIELD_TEMPLATE);
		Configuration configuration = modelService.findOneByProperties(properties, Configuration.class);

		if (configuration != null && StringUtils.isNotBlank(configuration.getValue())) {
			properties = new HashMap<String, Object>();
			properties.put(DynamicFieldTemplate.ID, configuration.getValue());

			DynamicFieldTemplate dynamicFieldTemplate = modelService.findOneByProperties(properties, DynamicFieldTemplate.class);

			if (dynamicFieldTemplate != null) {

				for (UUID dynamicFieldSlot : dynamicFieldTemplate.getDynamicFieldSlots()) {

					boolean addField = true;
					for (MenuAttribute field : model.getAttributes()) {
						if (field.getDynamicFieldSlot().equals(dynamicFieldSlot)) {
							addField = false;
						}
					}

					if (addField) {
						MenuAttribute field = new MenuAttribute();
						field.setDynamicFieldSlot(dynamicFieldSlot);
						field.setMenu(model);
						model.getAttributes().add(field);
					}
				}
			}
		}
	}

	@Override
	public List<Menu> findMenuBreadcrumbsByPath(String path) throws Exception {
		List<Menu> breadcrumbs = new ArrayList<Menu>();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Menu.PATH, path);
		Menu menu = modelService.findOneByProperties(properties, Menu.class);

		if (menu == null) {
			return null;
		} else {
			breadcrumbs.add(menu);

			if (menu.getParent() != null) {
				breadcrumbs.addAll(getParent(menu.getParent()));
			}

			Collections.reverse(breadcrumbs);

			return breadcrumbs;
		}
	}

	public List<Menu> getParent(Menu menu) {
		List<Menu> breadcrumbs = new ArrayList<Menu>();
		breadcrumbs.add(menu);
		if (menu.getParent() != null) {
			breadcrumbs.addAll(getParent(menu.getParent()));
		}

		return breadcrumbs;
	}
}
