package com.beanframework.menu.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.menu.domain.Menu;

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
}
