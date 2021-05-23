package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.ImexDto;

public interface ImexFacade {

  ImexDto findOneByUuid(UUID uuid) throws BusinessException;

  ImexDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  ImexDto save(ImexDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<ImexDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  ImexDto createDto() throws BusinessException;
}
