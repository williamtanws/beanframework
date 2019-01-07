package com.beanframework.menu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuField;

@Component
public class MenuFacadeImpl implements MenuFacade {

	Logger logger = LoggerFactory.getLogger(MenuFacadeImpl.class.getName());

	@Autowired
	private ModelService modelService;

	@Autowired
	private MenuService menuService;

	@Override
	public Menu create() throws Exception {
		return modelService.create(Menu.class);
	}

	@Override
	public Menu findOneDtoByUuid(UUID uuid) throws Exception {
		return modelService.findOneDtoByUuid(uuid, Menu.class);
	}

	@Override
	public Menu findOneDtoByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneDtoByProperties(properties, Menu.class);
	}

	@Override
	public Menu createDto(Menu model) throws BusinessException {
		return (Menu) modelService.saveDto(model, Menu.class);
	}

	@Override
	public Menu updateDto(Menu model) throws BusinessException {
		return (Menu) modelService.saveDto(model, Menu.class);
	}

	@Override
	public void changePosition(UUID fromUuid, UUID toUuid, int toIndex) throws BusinessException {
		try {
			menuService.savePosition(fromUuid, toUuid, toIndex);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		try {
			menuService.delete(uuid);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public List<Menu> findDtoMenuTree() throws BusinessException {
		try {
			return menuService.findDtoMenuTree();
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public List<Menu> findDtoMenuTreeByCurrentUser() throws BusinessException {
		try {
			return menuService.findDtoMenuTreeByCurrentUser();
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Menu saveEntity(Menu model) throws BusinessException {
		return (Menu) modelService.saveEntity(model, Menu.class);
	}

	@Override
	public void deleteById(String id) throws BusinessException {

		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Menu.ID, id);
			Menu model = modelService.findOneEntityByProperties(properties, Menu.class);
			modelService.deleteByEntity(model, Menu.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, Menu.class);
		
		return revisions;
	}
	
	@Override
	public List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.relatedId(MenuField.MENU).eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, MenuField.class);
		
		return revisions;
	}
}
