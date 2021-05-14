package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CurrencyDto;

public interface CurrencyFacade {

  CurrencyDto findOneByUuid(UUID uuid) throws Exception;

  CurrencyDto findOneProperties(Map<String, Object> properties) throws Exception;

  CurrencyDto create(CurrencyDto model) throws BusinessException;

  CurrencyDto update(CurrencyDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<CurrencyDto> findPage(DataTableRequest dataTableRequest) throws Exception;

  int count() throws Exception;

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

  int countHistory(DataTableRequest dataTableRequest) throws Exception;

  CurrencyDto createDto() throws Exception;
}
