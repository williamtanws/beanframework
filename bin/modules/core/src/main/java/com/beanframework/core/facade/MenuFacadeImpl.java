package com.beanframework.core.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.converter.dto.DtoMenuBreadcrumbsConverter;
import com.beanframework.core.converter.dto.DtoMenuTreeByCurrentUserConverter;
import com.beanframework.core.converter.dto.DtoMenuTreeConverter;
import com.beanframework.core.data.MenuDto;
import com.beanframework.core.specification.MenuSpecification;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.service.MenuService;
import com.beanframework.user.service.UserService;

@Service
public class MenuFacadeImpl extends AbstractFacade<Menu, MenuDto> implements MenuFacade {

	private static final Class<Menu> entityClass = Menu.class;
	private static final Class<MenuDto> dtoClass = MenuDto.class;

	@Autowired
	private DtoMenuTreeConverter dtoMenuTreeConverter;

	@Autowired
	private DtoMenuTreeByCurrentUserConverter dtoMenuTreeByCurrentUserConverter;

	@Autowired
	private DtoMenuBreadcrumbsConverter dtoMenuBreadcrumbsConverter;

	@Autowired
	private MenuService menuService;

	@Autowired
	private UserService userService;

	@Override
	public MenuDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}

	@Override
	public MenuDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
	}

	@Override
	public MenuDto create(MenuDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public MenuDto update(MenuDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		delete(uuid, entityClass);
	}

	@Override
	public Page<MenuDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, MenuSpecification.getSpecification(dataTableRequest), entityClass, dtoClass);
	}

	@Override
	public int count() throws Exception {
		return count(entityClass);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {
		return findHistory(dataTableRequest, entityClass, dtoClass);
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return findCountHistory(dataTableRequest, entityClass);
	}

	@Override
	public MenuDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
	}

	@Override
	public void changePosition(UUID fromUuid, UUID toUuid, int toIndex) throws BusinessException {
		try {
			menuService.savePosition(fromUuid, toUuid, toIndex);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Transactional
	@Override
	public List<MenuDto> findMenuTree() throws BusinessException {
		try {

			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Menu.PARENT, null);
			Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
			sorts.put(Menu.SORT, Sort.Direction.ASC);
			List<Menu> rootEntity = modelService.findByPropertiesBySortByResult(properties, sorts, null, null, Menu.class);

			List<MenuDto> rootDto = new ArrayList<MenuDto>();
			for (Menu menu : rootEntity) {
				rootDto.add(dtoMenuTreeConverter.convert(menu, new MenuDto()));
			}

			return rootDto;
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Transactional
	@Override
	public List<MenuDto> findMenuTreeByCurrentUser() throws Exception {

		Set<UUID> userGroupUuids = userService.getAllUserGroupsByCurrentUser();
		if (userGroupUuids == null || userGroupUuids.isEmpty()) {
			return new ArrayList<MenuDto>();
		}
		List<Menu> rootEntity = modelService.findBySpecificationBySort(MenuSpecification.getMenuByEnabledByUserGroup(null, userGroupUuids), Sort.by(Direction.ASC, Menu.SORT), Menu.class);

		List<MenuDto> rootDto = new ArrayList<MenuDto>();
		for (Menu menu : rootEntity) {
			rootDto.add(dtoMenuTreeByCurrentUserConverter.convert(menu, new MenuDto()));
		}
		return rootDto;

	}

	@Transactional
	@Override
	public List<MenuDto> findMenuBreadcrumbsByPath(String path) throws Exception {
		List<Menu> models = menuService.findMenuBreadcrumbsByPath(path);

		List<MenuDto> dtos = new ArrayList<MenuDto>();
		for (Menu menu : models) {
			dtos.add(dtoMenuBreadcrumbsConverter.convert(menu, new MenuDto()));
		}

		return dtos;
	}
}
