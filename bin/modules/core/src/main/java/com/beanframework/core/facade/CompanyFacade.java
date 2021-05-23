package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CompanyDto;

public interface CompanyFacade {

  CompanyDto findOneByUuid(UUID uuid) throws BusinessException;

  CompanyDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  CompanyDto save(CompanyDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<CompanyDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  CompanyDto createDto() throws BusinessException;

}
