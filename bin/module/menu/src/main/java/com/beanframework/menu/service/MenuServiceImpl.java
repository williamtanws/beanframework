package com.beanframework.menu.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.context.FetchContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.repository.MenuRepository;
import com.beanframework.user.domain.UserGroup;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private ModelService modelService;

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private FetchContext fetchContext;

	@Override
	public Menu create() throws Exception {
		return modelService.create(Menu.class);
	}

	@Override
	public Menu findOneEntityByUuid(UUID uuid) throws Exception {
		fetchContext.clearFetchProperties(Menu.class);

		fetchContext.addFetchProperty(Menu.class, Menu.CHILDS);
		fetchContext.addFetchProperty(Menu.class, Menu.USER_GROUPS);
		fetchContext.addFetchProperty(Menu.class, Menu.FIELDS);

		return modelService.findOneEntityByUuid(uuid, Menu.class);
	}

	@Override
	public Menu findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		fetchContext.clearFetchProperties(Menu.class);

		fetchContext.addFetchProperty(Menu.class, Menu.CHILDS);
		fetchContext.addFetchProperty(Menu.class, Menu.USER_GROUPS);
		fetchContext.addFetchProperty(Menu.class, Menu.FIELDS);

		return modelService.findOneEntityByProperties(properties, Menu.class);
	}

	@Override
	public Menu saveEntity(Menu model) throws BusinessException {
		return (Menu) modelService.saveEntity(model, Menu.class);
	}

	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Menu model = modelService.findOneEntityByUuid(uuid, Menu.class);
			modelService.deleteByEntity(model, Menu.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

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

	@Transactional(readOnly = true)
	@Override
	public List<Menu> findEntityMenuTree(boolean enabled) throws Exception {
		fetchContext.clearFetchProperties(Menu.class);

		fetchContext.addFetchProperty(Menu.class, Menu.CHILDS);
		fetchContext.addFetchProperty(Menu.class, Menu.USER_GROUPS);
		fetchContext.addFetchProperty(Menu.class, Menu.FIELDS);
		fetchContext.addFetchProperty(Menu.class, UserGroup.USER_AUTHORITIES);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Menu.PARENT, null);

		if (enabled) {
			properties.put(Menu.ENABLED, enabled);
		}

		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Menu.SORT, Sort.Direction.ASC);

		List<Menu> menuTree = modelService.findEntityByPropertiesAndSorts(properties, sorts, null, null, Menu.class);

		return menuTree;
	}

	@Override
	public <T> Page<Menu> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), Menu.class);
	}

	@Override
	public int count() throws Exception {
		return modelService.count(Menu.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Menu.class);

	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Menu.class);
	}
}
