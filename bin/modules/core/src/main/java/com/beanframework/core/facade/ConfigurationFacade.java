package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.ConfigurationDto;

public interface ConfigurationFacade {

  ConfigurationDto findOneByUuid(UUID uuid) throws BusinessException;

  ConfigurationDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  ConfigurationDto save(ConfigurationDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<ConfigurationDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  ConfigurationDto createDto() throws BusinessException;

  String get(String id, String defaultValue) throws BusinessException;

  boolean is(String id, boolean defaultValue) throws BusinessException;
}
