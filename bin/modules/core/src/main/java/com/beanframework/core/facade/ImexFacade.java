package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.ImexDto;

public interface ImexFacade {

  ImexDto findOneByUuid(UUID uuid) throws Exception;

  ImexDto findOneProperties(Map<String, Object> properties) throws Exception;

  ImexDto create(ImexDto model) throws BusinessException;

  ImexDto update(ImexDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<ImexDto> findPage(DataTableRequest dataTableRequest) throws Exception;

  int count() throws Exception;

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

  int countHistory(DataTableRequest dataTableRequest) throws Exception;

  ImexDto createDto() throws Exception;
}
