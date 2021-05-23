package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CurrencyDto;

public interface CurrencyFacade {

  CurrencyDto findOneByUuid(UUID uuid) throws BusinessException;

  CurrencyDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  CurrencyDto save(CurrencyDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<CurrencyDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  CurrencyDto createDto() throws BusinessException;
}
