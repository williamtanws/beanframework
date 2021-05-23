package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.UserRightDto;

public interface UserRightFacade {

  UserRightDto findOneByUuid(UUID uuid) throws BusinessException;

  UserRightDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  UserRightDto save(UserRightDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<UserRightDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  UserRightDto createDto() throws BusinessException;

  List<UserRightDto> findAllDtoUserRights() throws BusinessException;
}
