package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.DynamicFieldSlotDto;

public interface DynamicFieldSlotFacade {

  DynamicFieldSlotDto findOneByUuid(UUID uuid) throws BusinessException;

  DynamicFieldSlotDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  DynamicFieldSlotDto save(DynamicFieldSlotDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<DynamicFieldSlotDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  DynamicFieldSlotDto createDto() throws BusinessException;
}
