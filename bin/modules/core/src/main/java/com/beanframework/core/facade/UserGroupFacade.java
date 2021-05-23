package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.UserGroupDto;

public interface UserGroupFacade {

  UserGroupDto findOneByUuid(UUID uuid) throws BusinessException;

  UserGroupDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  UserGroupDto save(UserGroupDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<UserGroupDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  UserGroupDto createDto() throws BusinessException;
}
