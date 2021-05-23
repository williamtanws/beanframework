package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CustomerDto;

public interface CustomerFacade {

  CustomerDto findOneByUuid(UUID uuid) throws BusinessException;

  CustomerDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  CustomerDto save(CustomerDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<CustomerDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  CustomerDto createDto() throws BusinessException;

  CustomerDto getCurrentUser() throws BusinessException;

}
