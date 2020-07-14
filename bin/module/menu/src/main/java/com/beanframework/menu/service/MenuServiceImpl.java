package com.beanframework.menu.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.service.ModelService;
import com.beanframework.menu.domain.Menu;
import com.beanframework.user.domain.UserGroup;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

	@Autowired
	private ModelService modelService;

	@Override
	public void savePosition(UUID fromUuid, UUID toUuid, int toIndex) throws Exception {

		if (toUuid == null) {
			setParentNullAndSortByUuid(fromUuid, toIndex);

			List<Menu> toMenuChilds = findByParentNullOrderBySort();

			List<Menu> menus = changePosition(toMenuChilds, fromUuid, toIndex);
			for (Menu menu : menus) {
				modelService.saveEntity(menu, Menu.class);
			}
		} else {
			updateParentByUuid(fromUuid, toUuid, toIndex);

			List<Menu> toMenuChilds = findByParentUuidOrderBySort(toUuid);

			List<Menu> menus = changePosition(toMenuChilds, fromUuid, toIndex);
			for (Menu menu : menus) {
				modelService.saveEntity(menu, Menu.class);
			}
		}
	}

	private void setParentNullAndSortByUuid(UUID fromUuid, int toIndex) throws Exception {
		Menu menu = modelService.findOneByUuid(fromUuid, Menu.class);
		Menu parent = menu.getParent();
		if (parent != null) {
			Hibernate.initialize(parent.getChilds());
			if (parent.getChilds() != null) {
				for (int i = 0; i < parent.getChilds().size(); i++) {
					if (parent.getChilds().get(i).getUuid() == menu.getUuid()) {
						parent.getChilds().remove(i);
						modelService.saveEntity(parent, Menu.class);
						break;
					}
				}
			}
		}

		menu.setParent(null);
		menu.setSort(toIndex);
		modelService.saveEntity(menu, Menu.class);
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
		parent.getChilds().add(menu);
		menu.setParent(parent);
		menu.setSort(toIndex);
		modelService.saveEntity(menu, Menu.class);
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
	public List<Menu> findMenuTree(boolean enabled) throws Exception {

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Menu.PARENT, null);

		if (enabled) {
			properties.put(Menu.ENABLED, enabled);
		}

		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Menu.SORT, Sort.Direction.ASC);

		List<Menu> menuTree = modelService.findByPropertiesBySortByResult(properties, sorts, null, null, Menu.class);

		for (Menu model : menuTree) {
			Hibernate.initialize(model.getChilds());
			for (Menu menu : model.getChilds()) {
				initializeChilds(menu);
			}

			Hibernate.initialize(model.getUserGroups());
			for (UserGroup userGroup : model.getUserGroups()) {
				Hibernate.initialize(userGroup.getUserAuthorities());
			}
		}

		return menuTree;
	}

	private void initializeChilds(Menu model) {

		Hibernate.initialize(model.getChilds());
		for (Menu menu : model.getChilds()) {
			initializeChilds(menu);
		}

		Hibernate.initialize(model.getUserGroups());
		for (UserGroup userGroup : model.getUserGroups()) {
			Hibernate.initialize(userGroup.getUserAuthorities());
		}
	}

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

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.countHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Menu.class);
	}

	@Override
	public List<Menu> filterMenuByUserGroups(List<Menu> entities, List<UserGroup> userGroups) throws Exception {

		filterAuthorizedMenu(entities, userGroups);

		return entities;
	}

	private void filterAuthorizedMenu(List<Menu> menuRootList, List<UserGroup> userGroups) {
		Iterator<Menu> parent = menuRootList.iterator();
		while (parent.hasNext()) {
			Menu menu = parent.next();

			boolean removed = false;

			if (removed == Boolean.FALSE && menu.getEnabled() == Boolean.FALSE) {
				parent.remove();
				removed = true;
			}

			// If menu groups or authorities is not authorized
			if (removed == Boolean.FALSE && isUserGroupAuthorized(menu, userGroups) == Boolean.FALSE) {
				parent.remove();
				removed = true;
			}

			// If menu has childs
			if (menu.getChilds().isEmpty() == Boolean.FALSE) {
				filterAuthorizedMenu(menu.getChilds(), userGroups);
			}
		}
	}

	private boolean isUserGroupAuthorized(Menu menu, List<UserGroup> userGroups) {

		Set<String> collectionUserGroupUuid = collectUserGroupUuid(userGroups);

		Set<String> menuUserGroupUuidList = collectUserGroupUuid(menu.getUserGroups());
		for (String menuUserGroupUuid : menuUserGroupUuidList) {
			for (String authorizedUserGroupUuid : collectionUserGroupUuid) {
				if (authorizedUserGroupUuid.equals(menuUserGroupUuid)) {
					return true;
				}
			}
		}

		return false;
	}

	private Set<String> collectUserGroupUuid(List<UserGroup> userGroups) {
		return collectUserGroupUuid(userGroups, new HashSet<String>());
	}

	private Set<String> collectUserGroupUuid(List<UserGroup> userGroups, Set<String> userGroupUuids) {
		for (UserGroup userGroup : userGroups) {
			if (userGroupUuids.contains(userGroup.getUuid().toString()) == Boolean.FALSE) {
				userGroupUuids.add(userGroup.getUuid().toString());
				userGroupUuids.addAll(collectUserGroupUuid(userGroup.getUserGroups(), userGroupUuids));
			}
		}
		return userGroupUuids;
	}
}
