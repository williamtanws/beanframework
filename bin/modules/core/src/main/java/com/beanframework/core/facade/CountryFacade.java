package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CountryDto;

public interface CountryFacade {

  CountryDto findOneByUuid(UUID uuid) throws BusinessException;

  CountryDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  CountryDto save(CountryDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<CountryDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  CountryDto createDto() throws BusinessException;
}
