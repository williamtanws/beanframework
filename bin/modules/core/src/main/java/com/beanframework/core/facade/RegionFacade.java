package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.RegionDto;

public interface RegionFacade {

  RegionDto findOneByUuid(UUID uuid) throws BusinessException;

  RegionDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  RegionDto save(RegionDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<RegionDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  RegionDto createDto() throws BusinessException;
}
