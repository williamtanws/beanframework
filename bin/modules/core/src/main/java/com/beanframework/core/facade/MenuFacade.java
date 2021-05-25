package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.MenuDto;
import com.beanframework.menu.MenuConstants;
import com.beanframework.user.domain.User;

public interface MenuFacade {

  MenuDto findOneByUuid(UUID uuid) throws BusinessException;

  MenuDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  @CacheEvict(value = {MenuConstants.CACHE_MENU_TREE, MenuConstants.CACHE_MENU_BREADCRUMBS},
      allEntries = true)
  MenuDto save(MenuDto model) throws BusinessException;

  @CacheEvict(value = {MenuConstants.CACHE_MENU_TREE, MenuConstants.CACHE_MENU_BREADCRUMBS},
      allEntries = true)
  void delete(UUID uuid) throws BusinessException;

  @CacheEvict(value = {MenuConstants.CACHE_MENU_TREE, MenuConstants.CACHE_MENU_BREADCRUMBS},
      allEntries = true)
  void changePosition(UUID fromUuid, UUID toUuid, int toIndex) throws BusinessException;

  @Cacheable(value = MenuConstants.CACHE_MENU_TREE)
  List<MenuDto> findMenuTree() throws BusinessException;

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  MenuDto createDto() throws BusinessException;

  @Cacheable(value = MenuConstants.CACHE_MENU_TREE, key = "#user.uuid")
  List<MenuDto> findMenuTreeByUser(User user) throws BusinessException;

  @Cacheable(value = MenuConstants.CACHE_MENU_BREADCRUMBS, key = "#path")
  List<MenuDto> findMenuBreadcrumbsByPath(String path) throws BusinessException;

}
