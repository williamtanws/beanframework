package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.EmployeeDto;

public interface EmployeeFacade {

  EmployeeDto findOneByUuid(UUID uuid) throws BusinessException;

  EmployeeDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  EmployeeDto save(EmployeeDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<EmployeeDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  EmployeeDto createDto() throws BusinessException;

  EmployeeDto getCurrentUser() throws BusinessException;
}
