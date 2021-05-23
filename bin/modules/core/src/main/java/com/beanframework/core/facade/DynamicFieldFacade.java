package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.DynamicFieldDto;

public interface DynamicFieldFacade {

  DynamicFieldDto findOneByUuid(UUID uuid) throws BusinessException;

  DynamicFieldDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  DynamicFieldDto save(DynamicFieldDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<DynamicFieldDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  DynamicFieldDto createDto() throws BusinessException;

}
