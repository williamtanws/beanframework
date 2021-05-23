package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CronjobDto;

public interface CronjobFacade {

  CronjobDto findOneByUuid(UUID uuid) throws BusinessException;

  CronjobDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  CronjobDto save(CronjobDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<CronjobDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  CronjobDto createDto() throws BusinessException;

}
