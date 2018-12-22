package com.beanframework.menu.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.menu.converter.DtoMenuNavigationConverter;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuNavigation;

@Component
public class MenuFacadeImpl implements MenuFacade {

	Logger logger = LoggerFactory.getLogger(MenuFacadeImpl.class.getName());
	
	@Autowired
	private ModelService modelService;

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private DtoMenuNavigationConverter dtoMenuNavigationConverter;

	@Override
	public void changePosition(UUID fromUuid, UUID toUuid, int toIndex) throws BusinessException {
		try {
			menuService.savePosition(fromUuid, toUuid, toIndex);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public List<Menu> findDtoMenuTree() throws BusinessException {
		try {
			return modelService.getDto(menuService.findMenuTree(), Menu.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public List<MenuNavigation> findDtoMenuNavigationByUserGroup(List<UUID> userGroupUuids) throws BusinessException {
		try {
			return dtoMenuNavigationConverter.convert(menuService.findMenuTreeByUserGroup(userGroupUuids));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);

		}
	}
}
