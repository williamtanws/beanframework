package com.beanframework.menu.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.service.ModelService;
import com.beanframework.menu.domain.Menu;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserGroup;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

	@Autowired
	private ModelService modelService;
	
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
	public void removeUserGroupsRel(UserGroup model) throws Exception {
		Specification<Menu> specification = new Specification<Menu>() {
			private static final long serialVersionUID = 1L;

			public String toString() {
				return model.getUuid().toString();
			}

			@Override
			public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				predicates.add(cb.or(root.join(Menu.USER_GROUPS, JoinType.LEFT).get(UserGroup.UUID).in(model.getUuid())));

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}

			}
		};

		List<Menu> entities = modelService.findBySpecificationBySort(specification, Menu.class);

		for (int i = 0; i < entities.size(); i++) {

			for (int j = 0; j < entities.get(i).getUserGroups().size(); j++) {
				entities.get(i).getUserGroups().remove(model.getUuid());
			}
			modelService.saveEntityByLegacyMode(entities.get(i), User.class);
		}
	}
}
