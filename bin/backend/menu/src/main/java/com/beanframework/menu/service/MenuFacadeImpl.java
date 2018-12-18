package com.beanframework.menu.service;

import java.util.List;
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
	public void changePosition(UUID fromUuid, UUID toUuid, int toIndex) throws BusinessException {
		try {
			menuService.savePosition(fromUuid, toUuid, toIndex);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public List<Menu> findMenuTree() throws BusinessException {
		try {
			return modelService.getDto(menuService.findMenuTree());
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public List<Menu> findNavigationTreeByUserGroup(List<UUID> userGroupUuids) throws BusinessException {
		try {
			return modelService.getDto(menuService.findNavigationTreeByUserGroup(userGroupUuids));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);

		}
	}
}
