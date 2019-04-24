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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.specification.MenuSpecification;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private ModelService modelService;

	@Override
	public Menu create() throws Exception {
		return modelService.create(Menu.class);
	}

	@Override
	public Menu findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, Menu.class);
	}

	@Override
	public Menu findOneEntityByProperties(Map<String, Object> properties) throws Exception {
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
		Menu menu = modelService.findOneEntityByUuid(fromUuid, Menu.class);
		menu.setParent(null);
		menu.setSort(toIndex);
		modelService.saveEntity(menu, Menu.class);
	}

	private List<Menu> findByParentNullOrderBySort() throws Exception {

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Menu.PARENT, null);

		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Menu.SORT, Sort.Direction.ASC);

		return modelService.findEntityByPropertiesAndSorts(properties, sorts, null, null, Menu.class);
	}

	private void updateParentByUuid(UUID fromUuid, UUID toUuid, int toIndex) throws Exception {
		Menu menu = modelService.findOneEntityByUuid(fromUuid, Menu.class);
		Menu parent = modelService.findOneEntityByUuid(toUuid, Menu.class);
		parent.getChilds().add(menu);
		menu.setParent(parent);
		menu.setSort(toIndex);
		modelService.saveEntity(menu, Menu.class);
	}

	private List<Menu> findByParentUuidOrderBySort(UUID toUuid) throws Exception {

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Menu.PARENT+"."+Menu.UUID, toUuid);

		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Menu.SORT, Sort.Direction.ASC);

		return modelService.findEntityByPropertiesAndSorts(properties, sorts, null, null, Menu.class);
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
	public Page<Menu> findEntityPage(DataTableRequest dataTableRequest) throws Exception {
		return modelService.findEntityPage(MenuSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Menu.class);
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
