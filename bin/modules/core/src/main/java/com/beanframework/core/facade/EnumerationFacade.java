package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.EnumerationDto;

public interface EnumerationFacade {

  EnumerationDto findOneByUuid(UUID uuid) throws BusinessException;

  EnumerationDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  EnumerationDto save(EnumerationDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<EnumerationDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  EnumerationDto createDto() throws BusinessException;
}
