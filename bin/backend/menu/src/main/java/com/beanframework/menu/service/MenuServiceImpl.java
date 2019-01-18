package com.beanframework.menu.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuField;
import com.beanframework.menu.repository.MenuRepository;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private ModelService modelService;

	@Autowired
	private MenuRepository menuRepository;

	@Override
	public Menu create() throws Exception {
		return modelService.create(Menu.class);
	}

	@Cacheable(value = "MenuOne", key = "#uuid")
	@Override
	public Menu findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, Menu.class);
	}

	@Cacheable(value = "MenuOneProperties", key = "#properties")
	@Override
	public Menu findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, Menu.class);
	}

	@Cacheable(value = "MenusHistory", key = "'uuid:'+#uuid+',firstResult:'+#firstResult+',maxResults:'+#maxResults")
	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		return modelService.findHistory(false, criterion, order, firstResult, maxResults, Menu.class);
	}

	@Cacheable(value = "MenusRelatedHistory", key = "'relatedEntity:'+#relatedEntity+',uuid:'+#uuid+',firstResult:'+#firstResult+',maxResults:'+#maxResults")
	@Override
	public List<Object[]> findHistoryByRelatedUuid(String relatedEntity, UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.relatedId(relatedEntity).eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		return modelService.findHistory(false, criterion, order, firstResult, maxResults, MenuField.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "MenuOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "MenuOneProperties", allEntries = true), //
			@CacheEvict(value = "MenusHistory", allEntries = true), //
			@CacheEvict(value = "MenusRelatedHistory", allEntries = true), //
			@CacheEvict(value = "MenuTree", allEntries = true) }) //
	@Override
	public Menu saveEntity(Menu model) throws BusinessException {
		return (Menu) modelService.saveEntity(model, Menu.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "MenuOne", key = "#uuid"), //
			@CacheEvict(value = "MenuOneProperties", allEntries = true), //
			@CacheEvict(value = "MenusHistory", allEntries = true), //
			@CacheEvict(value = "MenusRelatedHistory", allEntries = true), //
			@CacheEvict(value = "MenuTree", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Menu model = modelService.findOneEntityByUuid(uuid, Menu.class);
			modelService.deleteByEntity(model, Menu.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Caching(evict = { //
			@CacheEvict(value = "MenuOne", key = "#fromUuid"), //
			@CacheEvict(value = "MenuOne", key = "#toUuid", condition = "#toUuid != null"), //
			@CacheEvict(value = "MenuOneProperties", allEntries = true), //
			@CacheEvict(value = "MenusHistory", allEntries = true), //
			@CacheEvict(value = "MenusRelatedHistory", allEntries = true), //
			@CacheEvict(value = "MenuTree", allEntries = true) })
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

	@Cacheable(value = "MenuTree")
	@Transactional(readOnly = true)
	@Override
	public List<Menu> findEntityMenuTree() throws Exception {

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Menu.PARENT, null);

		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Menu.SORT, Sort.Direction.ASC);

		List<Menu> menuTree = modelService.findEntityByPropertiesAndSorts(properties, sorts, null, null, Menu.class);

		return menuTree;
	}

	@Override
	public List<Menu> filterEntityMenuTreeByCurrentUser(List<Menu> cachedMenuTree) throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = (User) auth.getPrincipal();

		filterMenuNavigation(cachedMenuTree, collectUserGroupUuid(user.getUserGroups()));

		return cachedMenuTree;
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
			if (menuNext.getChilds().isEmpty()) {
				for (UserGroup userGroup : menuNext.getUserGroups()) {
					if (userGroupUuids.contains(userGroup.getUuid())) {
						for (UserAuthority userAuthority : userGroup.getUserAuthorities()) {
							if (userAuthority.getUserPermission().getId().equals(menuNext.getId())) {
								if (userAuthority.getEnabled().equals(Boolean.TRUE)) {
									remove = false;
								}
							}
						}
					}
				}
			}
			else {
				remove = false;
			}
			
			if (remove) {
				parent.remove();
			}

			if (menuNext.getChilds() != null && menuNext.getChilds().isEmpty() == false) {
				filterMenuNavigation(menuNext.getChilds(), userGroupUuids);
			}
		}
	}
}
