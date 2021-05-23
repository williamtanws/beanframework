package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.MenuDto;

public interface MenuFacade {

  MenuDto findOneByUuid(UUID uuid) throws BusinessException;

  MenuDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  MenuDto save(MenuDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  void changePosition(UUID fromUuid, UUID toUuid, int toIndex) throws BusinessException;

  List<MenuDto> findMenuTree() throws BusinessException;

  Page<MenuDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  MenuDto createDto() throws BusinessException;

  List<MenuDto> findMenuTreeByCurrentUser() throws BusinessException;

  List<MenuDto> findMenuBreadcrumbsByPath(String path) throws BusinessException;

}
