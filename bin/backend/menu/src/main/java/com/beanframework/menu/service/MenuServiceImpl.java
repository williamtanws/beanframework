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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.service.ModelService;
import com.beanframework.menu.MenuConstants;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.repository.MenuRepository;
import com.beanframework.user.domain.UserGroup;

@Service
public class MenuServiceImpl implements MenuService {

	Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class.getName());

	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private ModelService modelService;
	
	@CacheEvict(value = { MenuConstants.Cache.MENU, MenuConstants.Cache.NAVIGATION_TREE,
			MenuConstants.Cache.NAVIGATION_TREE_BY_USERGROUP }, allEntries = true)
	@Transactional
	@Override
	public void savePosition(UUID fromUuid, UUID toUuid, int toIndex) {
		
		if(toUuid == null) {
			
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Menu.UUID, fromUuid);
			Menu fromMenu = modelService.findOneEntityByProperties(properties, Menu.class);
			fromMenu.setParent(null);
			fromMenu.setSort(toIndex);
			
			modelService.saveEntity(fromMenu);
			
			properties = new HashMap<String, Object>();
			properties.put(Menu.PARENT, null);
			
			Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
			sorts.put(Menu.SORT, Sort.Direction.ASC);
			List<Menu> toMenuChilds = modelService.findByPropertiesAndSorts(properties, sorts, Menu.class);

			List<Menu> menus = changePosition(toMenuChilds, fromUuid, toIndex);
			for (Menu menu : menus) {
				modelService.saveEntity(menu);
			}
		}
		else {
			
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Menu.UUID, toUuid);
			Menu toMenu = modelService.findOneEntityByProperties(properties, Menu.class);
			
			properties = new HashMap<String, Object>();
			properties.put(Menu.UUID, fromUuid);
			Menu fromMenu = modelService.findOneEntityByProperties(properties, Menu.class);
			fromMenu.setParent(toMenu);
			fromMenu.setSort(toIndex);
			
			modelService.saveEntity(fromMenu);
			
			properties = new HashMap<String, Object>();
			properties.put(Menu.PARENT, toUuid);
			
			Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
			sorts.put(Menu.SORT, Sort.Direction.ASC);
			List<Menu> toMenuChilds = modelService.findByPropertiesAndSorts(properties, sorts, Menu.class);
			
			List<Menu> menus = changePosition(toMenuChilds, fromUuid, toIndex);
			for (Menu menu : menus) {
				modelService.saveEntity(menu);
			}
		}
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

	@Transactional(readOnly = true)
	@Override
	public List<Menu> findMenuTree() {
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Menu.PARENT, null);
		
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Menu.SORT, Sort.Direction.ASC);
		List<Menu> rootParents = modelService.findByPropertiesAndSorts(properties, sorts, Menu.class);

		initializeChilds(rootParents);

		return rootParents;
	}

	private void initializeChilds(List<Menu> parents) {

		for (Menu parent : parents) {

			// Find all childs
			Specification<Menu> spec = new Specification<Menu>() {
				private static final long serialVersionUID = 1L;

				@Override
				public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

					List<Predicate> predicates = new ArrayList<Predicate>();

					predicates.add(cb.and(root.get(Menu.PARENT).get("uuid").in(parent.getUuid())));

					query.orderBy(cb.asc(root.get(Menu.SORT)));

					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				}
			};
			List<Menu> childs = menuRepository.findAll(spec);
			parent.setChilds(childs);

			if (parent.getChilds().isEmpty() == false) {
				initializeChilds(parent.getChilds());
			}
		}
	}

	@Cacheable(cacheNames = MenuConstants.Cache.NAVIGATION_TREE_BY_USERGROUP, key = "#userGroupUuids")
	@Transactional(readOnly = true)
	@Override
	public List<Menu> findNavigationTreeByUserGroup(List<UUID> userGroupUuids) {

		// Find all root parents
		Specification<Menu> spec = new Specification<Menu>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				predicates.add(cb.and(root.get(Menu.PARENT).isNull()));
				predicates.add(cb.isTrue(root.get(Menu.ENABLED)));
				predicates.add(cb.and(root.join(Menu.USER_GROUPS, JoinType.INNER).get("uuid").in(userGroupUuids)));
				
				query.orderBy(cb.asc(root.get(Menu.SORT)));
				query.distinct(true);

				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		List<Menu> rootParents = menuRepository.findAll(spec);

		initializeChildsByUserGroup(rootParents, userGroupUuids);

		return rootParents;
	}

	private void initializeChildsByUserGroup(List<Menu> parents, List<UUID> userGroupUuids) {

		for (Menu parent : parents) {

			// Find all childs
			Specification<Menu> spec = new Specification<Menu>() {
				private static final long serialVersionUID = 1L;

				@Override
				public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

					List<Predicate> predicates = new ArrayList<Predicate>();

					predicates.add(cb.and(root.get(Menu.PARENT).get(Menu.UUID).in(parent.getUuid())));
					predicates.add(cb.isTrue(root.get(Menu.ENABLED)));
					predicates.add(cb.and(root.join(Menu.USER_GROUPS, JoinType.INNER).get(UserGroup.UUID).in(userGroupUuids)));

					query.orderBy(cb.asc(root.get(Menu.SORT)));
					query.distinct(true);

					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				}
			};
			List<Menu> childs = menuRepository.findAll(spec);
			parent.setChilds(childs);

			if (parent.getChilds().isEmpty() == false) {
				initializeChildsByUserGroup(parent.getChilds(), userGroupUuids);
			}
		}
	}
}
