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

  UserDto findOneByUuid(UUID uuid) throws Exception;

  UserDto findOneProperties(Map<String, Object> properties) throws Exception;

  UserDto create(UserDto model) throws BusinessException;

  UserDto update(UserDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<UserDto> findPage(DataTableRequest dataTableRequest) throws Exception;

  int count() throws Exception;

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

  int countHistory(DataTableRequest dataTableRequest) throws Exception;

  UserDto createDto() throws Exception;

  UserDto getCurrentUser() throws Exception;

  Set<UserSession> findAllSessions();

  void expireAllSessionsByUuid(UUID uuid);

  void expireAllSessions();
}
