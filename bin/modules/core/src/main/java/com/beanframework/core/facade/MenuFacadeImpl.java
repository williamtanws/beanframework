package com.beanframework.core.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.converter.dto.MenuBreadcrumbsDtoConverter;
import com.beanframework.core.converter.dto.MenuTreeByCurrentUserDtoConverter;
import com.beanframework.core.converter.dto.MenuTreeDtoConverter;
import com.beanframework.core.data.MenuDto;
import com.beanframework.core.specification.MenuSpecification;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.service.MenuService;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.UserService;

@Service
public class MenuFacadeImpl extends AbstractFacade<Menu, MenuDto> implements MenuFacade {

  private static final Class<Menu> entityClass = Menu.class;
  private static final Class<MenuDto> dtoClass = MenuDto.class;

  @Autowired
  private MenuTreeDtoConverter menuTreeDtoConverter;

  @Autowired
  private MenuTreeByCurrentUserDtoConverter menuTreeByCurrentUserDtoConverter;

  @Autowired
  private MenuBreadcrumbsDtoConverter menuBreadcrumbsDtoConverter;

  @Autowired
  private MenuService menuService;

  @Autowired
  private UserService userService;

  @Override
  public MenuDto findOneByUuid(UUID uuid) throws BusinessException {
    return findOneByUuid(uuid, entityClass, dtoClass);
  }

  @Override
  public MenuDto findOneProperties(Map<String, Object> properties) throws BusinessException {
    return findOneProperties(properties, entityClass, dtoClass);
  }

  @Override
  public MenuDto save(MenuDto model) throws BusinessException {
    return save(model, entityClass, dtoClass);
  }

  @Override
  public void delete(UUID uuid) throws BusinessException {
    delete(uuid, entityClass);
  }

  @Override
  public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException {
    return findHistory(dataTableRequest, entityClass, dtoClass);
  }

  @Override
  public int countHistory(DataTableRequest dataTableRequest) {
    return findCountHistory(dataTableRequest, entityClass);
  }

  @Override
  public MenuDto createDto() throws BusinessException {
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
      List<Menu> rootEntity =
          modelService.findByPropertiesBySortByResult(properties, sorts, null, null, Menu.class);

      List<MenuDto> rootDto = new ArrayList<MenuDto>();
      for (Menu menu : rootEntity) {
        rootDto.add(menuTreeDtoConverter.convert(menu, new MenuDto()));
      }

      return rootDto;
    } catch (Exception e) {
      throw new BusinessException(e.getMessage(), e);
    }
  }

  @Transactional
  @Override
  public List<MenuDto> findMenuTreeByUser(User user) throws BusinessException {

    Set<UUID> userGroupUuids = userService.getAllUserGroupsByUser(user);
    if (userGroupUuids == null || userGroupUuids.isEmpty()) {
      return new ArrayList<MenuDto>();
    }
    List<Menu> rootEntity = modelService.findBySpecificationBySort(
        MenuSpecification.getMenuByEnabledByUserGroup(null, userGroupUuids),
        Sort.by(Direction.ASC, Menu.SORT), Menu.class);

    List<MenuDto> rootDto = new ArrayList<MenuDto>();
    for (Menu menu : rootEntity) {
      rootDto.add(menuTreeByCurrentUserDtoConverter.convert(menu, new MenuDto()));
    }
    return rootDto;

  }

  @Transactional
  @Override
  public List<MenuDto> findMenuBreadcrumbsByPath(String path) throws BusinessException {
    List<Menu> models = menuService.findMenuBreadcrumbsByPath(path);

    List<MenuDto> dtos = new ArrayList<MenuDto>();
    for (Menu menu : models) {
      dtos.add(menuBreadcrumbsDtoConverter.convert(menu, new MenuDto()));
    }

    return dtos;
  }
}
