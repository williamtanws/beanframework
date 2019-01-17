package com.beanframework.backoffice.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.backoffice.data.MenuDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuField;
import com.beanframework.menu.service.MenuService;

@Component
public class MenuFacadeImpl implements MenuFacade {

	Logger logger = LoggerFactory.getLogger(MenuFacadeImpl.class.getName());

	@Autowired
	private ModelService modelService;

	@Autowired
	private MenuService menuService;

	@Override
	public MenuDto findOneByUuid(UUID uuid) throws Exception {
		Menu entity = menuService.findOneEntityByUuid(uuid);
		return modelService.getDto(entity, MenuDto.class);
	}

	@Override
	public MenuDto findOneProperties(Map<String, Object> properties) throws Exception {
		Menu entity = menuService.findOneEntityByProperties(properties);
		return modelService.getDto(entity, MenuDto.class);
	}

	@Override
	public MenuDto create(MenuDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public MenuDto update(MenuDto model) throws BusinessException {
		return save(model);
	}

	public MenuDto save(MenuDto dto) throws BusinessException {
		try {
			Menu entity = modelService.getEntity(dto, Menu.class);
			entity = (Menu) menuService.saveEntity(entity);

			return modelService.getDto(entity, MenuDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
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
			menuService.deleteByUuid(uuid);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public List<MenuDto> findMenuTree() throws BusinessException {
		try {
			List<Menu> entities = menuService.findEntityMenuTree();
			return modelService.getDto(entities, MenuDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		List<Object[]> revisions = menuService.findHistoryByUuid(uuid, firstResult, maxResults);
//		for (int i = 0; i < revisions.size(); i++) {
//			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], MenuDto.class);
//		}

		return revisions;
	}

	@Override
	public List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		List<Object[]> revisions = menuService.findHistoryByRelatedUuid(MenuField.MENU, uuid, firstResult, maxResults);
//		for (int i = 0; i < revisions.size(); i++) {
//			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], MenuFieldDto.class);
//		}

		return revisions;
	}

	@Override
	public List<Menu> findMenuTreeByCurrentUser() throws Exception {
		List<Menu> menuTree = new ArrayList<Menu>();
		menuTree.addAll(menuService.findEntityMenuTree());
		
		return menuService.filterEntityMenuTreeByCurrentUser(menuTree);
	}
}
