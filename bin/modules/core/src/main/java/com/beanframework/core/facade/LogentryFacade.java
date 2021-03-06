package com.beanframework.core.facade;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.LogentryDto;

public interface LogentryFacade {

  LogentryDto findOneByUuid(UUID uuid) throws BusinessException;

  LogentryDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  LogentryDto save(LogentryDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<LogentryDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  LogentryDto createDto() throws BusinessException;

  int removeAllLogentry() throws BusinessException;

  int removeOldLogentryByToDate(Date date) throws BusinessException;
}
