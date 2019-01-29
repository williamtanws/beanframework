package com.beanframework.menu.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.menu.domain.Menu;
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
		return modelService.findOneEntityByUuid(uuid, true, Menu.class);
	}

	@Cacheable(value = "MenuOneProperties", key = "#properties")
	@Override
	public Menu findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, true,Menu.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "MenuOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "MenuOneProperties", allEntries = true), //
			@CacheEvict(value = "MenusHistory", allEntries = true), //
			@CacheEvict(value = "MenuTree", allEntries = true) }) //
	@Override
	public Menu saveEntity(Menu model) throws BusinessException {
		return (Menu) modelService.saveEntity(model, Menu.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "MenuOne", key = "#uuid"), //
			@CacheEvict(value = "MenuOneProperties", allEntries = true), //
			@CacheEvict(value = "MenusHistory", allEntries = true), //
			@CacheEvict(value = "MenuTree", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Menu model = modelService.findOneEntityByUuid(uuid, true, Menu.class);
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

		List<Menu> menuTree = modelService.findEntityByPropertiesAndSorts(properties, sorts, null, null, true, Menu.class);

		return menuTree;
	}

	@Override
	public List<Menu> filterEntityMenuTreeByCurrentUser(List<Menu> cachedMenuTree) throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = (User) auth.getPrincipal();

		filterAuthorizedMenu(cachedMenuTree, collectUserGroupUuid(user.getUserGroups()));
		filterEmptyChildMenu(cachedMenuTree);
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

	private void filterAuthorizedMenu(List<Menu> menu, Set<UUID> userGroupUuids) {
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
				filterAuthorizedMenu(menuNext.getChilds(), userGroupUuids);
			}
		}
	}
	
	private void filterEmptyChildMenu(List<Menu> parent) {
		Iterator<Menu> menu = parent.iterator();
		while (menu.hasNext()) {
			Menu menuNext = menu.next();
			if(StringUtils.isBlank(menuNext.getPath()) && menuNext.getChilds().isEmpty()) {
				menu.remove();
			}
			
			if(menuNext.getChilds().isEmpty() == false) {
				filterEmptyChildMenu(menuNext.getChilds());
			}
		}
	}
	
	@Cacheable(value = "MenusPage", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public <T> Page<Menu> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), false, Menu.class);
	}

	@Cacheable(value = "MenusPage", key = "'count'")
	@Override
	public int count() throws Exception {
		return modelService.count(Menu.class);
	}
	
	@Cacheable(value = "MenusHistory", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());
		
		return modelService.findHistory(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Menu.class);

	}

	@Cacheable(value = "MenusHistory", key = "'count, dataTableRequest:'+#dataTableRequest")
	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Menu.class);
	}
}
