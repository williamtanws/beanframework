package com.beanframework.menu.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.menu.MenuConstants;
import com.beanframework.menu.converter.DtoMenuConverter;
import com.beanframework.menu.converter.EntityMenuConverter;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.repository.MenuRepository;
import com.beanframework.user.domain.UserGroup;

@Service
public class MenuServiceImpl implements MenuService {

	Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class.getName());

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private EntityMenuConverter entityMenuConverter;

	@Autowired
	private DtoMenuConverter dtoMenuConverter;

	@Override
	public Menu create() {
		return initDefaults(new Menu());
	}

	@Override
	public Menu initDefaults(Menu menu) {
		menu.setSort(-1);
		return menu;
	}

	@CacheEvict(value = { MenuConstants.Cache.MENU, MenuConstants.Cache.NAVIGATION_TREE,
			MenuConstants.Cache.NAVIGATION_TREE_BY_USERGROUP }, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public Menu save(Menu menu) {
		menu = entityMenuConverter.convert(menu);
		menu = menuRepository.save(menu);
		menu = dtoMenuConverter.convert(menu);

		return menu;
	}
	
	@CacheEvict(value = { MenuConstants.Cache.MENU, MenuConstants.Cache.NAVIGATION_TREE,
			MenuConstants.Cache.NAVIGATION_TREE_BY_USERGROUP }, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public List<Menu> save(List<Menu> menus) {
		menus = entityMenuConverter.convert(menus);
		menus = menuRepository.saveAll(menus);
		menus = dtoMenuConverter.convert(menus);

		return menus;
	}
	
	@CacheEvict(value = { MenuConstants.Cache.MENU, MenuConstants.Cache.NAVIGATION_TREE,
			MenuConstants.Cache.NAVIGATION_TREE_BY_USERGROUP }, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public void savePosition(UUID fromUuid, UUID toUuid, int toIndex) {
		
		if(toUuid == null) {
			menuRepository.setParentNullByUuid(fromUuid);
			
			menuRepository.updateSortByUuid(fromUuid, toIndex);
			
			List<Menu> toMenuChilds = menuRepository.findByParentNullOrderBySort();
			
			List<Menu> menus = changePosition(toMenuChilds, fromUuid, toIndex);
			menuRepository.saveAll(menus);
		}
		else {
			menuRepository.updateParentByUuid(fromUuid, toUuid);
			
			menuRepository.updateSortByUuid(fromUuid, toIndex);
			
			List<Menu> toMenuChilds = menuRepository.findByParentUuidOrderBySort(toUuid);
			
			List<Menu> menus = changePosition(toMenuChilds, fromUuid, toIndex);
			menuRepository.saveAll(menus);
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

	@CacheEvict(value = { MenuConstants.Cache.MENU, MenuConstants.Cache.NAVIGATION_TREE,
			MenuConstants.Cache.NAVIGATION_TREE_BY_USERGROUP }, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public void delete(UUID uuid) {
		menuRepository.deleteById(uuid);
	}

	@CacheEvict(value = { MenuConstants.Cache.MENU, MenuConstants.Cache.NAVIGATION_TREE,
			MenuConstants.Cache.NAVIGATION_TREE_BY_USERGROUP }, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public void deleteAll() {
		menuRepository.deleteAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Menu> findEntityByUuid(UUID uuid) {
		return menuRepository.findByUuid(uuid);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Menu> findEntityById(String id) {
		return menuRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Menu findByUuid(UUID uuid) {
		Optional<Menu> menu = menuRepository.findByUuid(uuid);

		if (menu.isPresent()) {
			return dtoMenuConverter.convert(menu.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Menu findById(String id) {
		Optional<Menu> menu = menuRepository.findById(id);

		if (menu.isPresent()) {
			return dtoMenuConverter.convert(menu.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Menu findByPath(String path) {
		Optional<Menu> menu = menuRepository.findByPath(path);

		if (menu.isPresent()) {
			return dtoMenuConverter.convert(menu.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<Menu> findMenuTree() {

		// Find all root parents
		Specification<Menu> spec = new Specification<Menu>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				predicates.add(cb.and(root.get(Menu.PARENT).isNull()));

				query.orderBy(cb.asc(root.get(Menu.SORT)));

				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		List<Menu> rootParents = menuRepository.findAll(spec);

		initializeChilds(rootParents);

		return dtoMenuConverter.convert(rootParents);
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

		return dtoMenuConverter.convert(rootParents);
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
