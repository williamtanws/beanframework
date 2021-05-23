package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.UserPermissionDto;

public interface UserPermissionFacade {

  UserPermissionDto findOneByUuid(UUID uuid) throws BusinessException;

  UserPermissionDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  UserPermissionDto save(UserPermissionDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<UserPermissionDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  UserPermissionDto createDto() throws BusinessException;

  List<UserPermissionDto> findAllDtoUserPermissions() throws BusinessException;

}
