package com.beanframework.menu.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.service.ModelService;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.repository.MenuRepository;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserGroup;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private ModelService modelService;

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	protected CacheManager cacheManager;

	@Transactional
	@Override
	public void savePosition(UUID fromUuid, UUID toUuid, int toIndex) {

		if (toUuid == null) {
			menuRepository.setParentNullByUuid(fromUuid);

			menuRepository.updateSortByUuid(fromUuid, toIndex);

			List<Menu> toMenuChilds = menuRepository.findByParentNullOrderBySort();

			List<Menu> menus = changePosition(toMenuChilds, fromUuid, toIndex);
			menuRepository.saveAll(menus);
		} else {
			menuRepository.updateParentByUuid(fromUuid, toUuid);

			menuRepository.updateSortByUuid(fromUuid, toIndex);

			List<Menu> toMenuChilds = menuRepository.findByParentUuidOrderBySort(toUuid);

			List<Menu> menus = changePosition(toMenuChilds, fromUuid, toIndex);
			menuRepository.saveAll(menus);
		}

		modelService.clearCache(Menu.class);
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
	public List<Menu> findDtoMenuTree() throws Exception {

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
		List<Menu> menuTree = menuRepository.findAll(spec);

		initializeChilds(menuTree);

		menuTree = modelService.getDto(menuTree, Menu.class);

		return menuTree;
	}

	private void initializeChilds(List<Menu> parents) throws Exception {

		for (Menu parent : parents) {
			Hibernate.initialize(parent.getUserGroups());
			Hibernate.initialize(parent.getFields());

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
			if (childs != null && childs.isEmpty()) {
				parent.setChilds(childs);

				initializeChilds(parent.getChilds());
			}
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<Menu> findDtoMenuTreeByCurrentUser() throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = (User) auth.getPrincipal();

		List<Menu> menuTree = findDtoMenuTreeCached();
		filterMenuNavigation(menuTree, collectUserGroupUuid(user.getUserGroups()));

		return menuTree;
	}

	@SuppressWarnings("unchecked")
	public List<Menu> findDtoMenuTreeCached() throws Exception {

		ValueWrapper valueWrapper = cacheManager.getCache(Menu.class.getName()).get("MenuTree");
		if (valueWrapper == null) {

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
			List<Menu> menuTree = menuRepository.findAll(spec);

			initializeChilds(menuTree);

			menuTree = modelService.getDto(menuTree, Menu.class);

			cacheManager.getCache(Menu.class.getName()).put("MenuTree", menuTree);

			return menuTree;
		} else {
			return (List<Menu>) valueWrapper.get();
		}
	}

	private Set<UUID> collectUserGroupUuid(List<UserGroup> userGroups) {
		Set<UUID> userGroupUuids = new LinkedHashSet<UUID>();
		for (UserGroup userGroup : userGroups) {
			userGroupUuids.add(userGroup.getUuid());
			if (userGroup.getUserGroups() != null && userGroup.getUserGroups().isEmpty() == false) {
				userGroupUuids.addAll(collectUserGroupUuid(userGroup.getUserGroups()));
			}
		}
		return userGroupUuids;
	}

	private void filterMenuNavigation(List<Menu> menu, Set<UUID> userGroupUuids) {
		Iterator<Menu> parent = menu.iterator();
		while (parent.hasNext()) {
			Menu menuNext = parent.next();
			if (menuNext.getEnabled() == false) {
				parent.remove();
			}

			boolean remove = true;
			for (UserGroup userGroup : menuNext.getUserGroups()) {
				if (userGroupUuids.contains(userGroup.getUuid())) {
					remove = false;
				}
			}
			if (remove) {
				parent.remove();
			}

			if (menuNext.getChilds() != null && menuNext.getChilds().isEmpty() == false) {
				filterMenuNavigation(menuNext.getChilds(), userGroupUuids);
			}
		}
	}

	@Override
	public void delete(UUID uuid) throws Exception {
		modelService.delete(uuid, Menu.class);
	}
}
