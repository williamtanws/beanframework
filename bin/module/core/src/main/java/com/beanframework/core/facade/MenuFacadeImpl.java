package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.MenuDto;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.service.MenuService;

@Component
public class MenuFacadeImpl implements MenuFacade {

	protected static final Logger LOGGER = LoggerFactory.getLogger(MenuFacadeImpl.class.getName());

	@Autowired
	private ModelService modelService;

	@Autowired
	private MenuService menuService;

	@Override
	public MenuDto findOneByUuid(UUID uuid) throws Exception {
		Menu entity = menuService.findOneEntityByUuid(uuid);
		MenuDto dto = modelService.getDto(entity, MenuDto.class, new DtoConverterContext(ConvertRelationType.ALL));

		return dto;
	}

	@Override
	public MenuDto findOneProperties(Map<String, Object> properties) throws Exception {
		Menu entity = menuService.findOneEntityByProperties(properties);
		MenuDto dto = modelService.getDto(entity, MenuDto.class);

		return dto;
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

			List<Menu> entities = menuService.findEntityMenuTree(false);
			List<MenuDto> dtos = modelService.getDto(entities, MenuDto.class);

			return dtos;
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Page<MenuDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Menu> page = menuService.findEntityPage(dataTableRequest);

		List<MenuDto> dtos = modelService.getDto(page.getContent(), MenuDto.class, new DtoConverterContext(ConvertRelationType.RELATION));
		return new PageImpl<MenuDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return menuService.count();
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = menuService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Menu) {

				entityObject[0] = modelService.getDto(entityObject[0], MenuDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return menuService.findCountHistory(dataTableRequest);
	}

	@Override
	public MenuDto createDto() throws Exception {
		return modelService.getDto(modelService.create(Menu.class), MenuDto.class);
	}
}
