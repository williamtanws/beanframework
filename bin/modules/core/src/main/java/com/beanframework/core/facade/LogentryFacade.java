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

  LogentryDto findOneByUuid(UUID uuid) throws Exception;

  LogentryDto findOneProperties(Map<String, Object> properties) throws Exception;

  LogentryDto create(LogentryDto model) throws BusinessException;

  LogentryDto update(LogentryDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<LogentryDto> findPage(DataTableRequest dataTableRequest) throws Exception;

  int count() throws Exception;

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

  int countHistory(DataTableRequest dataTableRequest) throws Exception;

  LogentryDto createDto() throws Exception;

  int removeAllLogentry() throws BusinessException;

  int removeOldLogentryByToDate(Date date) throws Exception;
}
