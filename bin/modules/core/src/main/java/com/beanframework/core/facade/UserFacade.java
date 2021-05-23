package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.UserDto;
import com.beanframework.user.data.UserSession;

public interface UserFacade {

  UserDto findOneByUuid(UUID uuid) throws BusinessException;

  UserDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  UserDto save(UserDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<UserDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  UserDto createDto() throws BusinessException;

  UserDto getCurrentUser() throws BusinessException;

  Set<UserSession> findAllSessions();

  void expireAllSessionsByUuid(UUID uuid);

  void expireAllSessions();
}
