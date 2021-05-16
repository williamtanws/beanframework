package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.VendorDto;

public interface VendorFacade {

  VendorDto findOneByUuid(UUID uuid) throws Exception;

  VendorDto findOneProperties(Map<String, Object> properties) throws Exception;

  VendorDto create(VendorDto model) throws BusinessException;

  VendorDto update(VendorDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<VendorDto> findPage(DataTableRequest dataTableRequest) throws Exception;

  int count() throws Exception;

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

  int countHistory(DataTableRequest dataTableRequest) throws Exception;

  VendorDto createDto() throws Exception;
}
